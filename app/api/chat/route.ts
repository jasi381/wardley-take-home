import { NextRequest } from "next/server";
import { callLlm } from "@/lib/llm";
import { INTAKE_SYSTEM_PROMPT } from "@/lib/prompts";
import type {
  ChatApiRequest,
  ChatApiResponse,
  ClinicalBrief,
  ProviderId,
  ROS,
  ROSSystem,
} from "@/lib/schema";

const VALID_PROVIDERS: ProviderId[] = ["gemini", "cerebras"];

// Hard cap on patient turns before we force the model to finalize. Twelve gives
// room for OLDCARTS + targeted ROS + close-out without becoming an interrogation.
const MAX_USER_TURNS = 12;

export const runtime = "nodejs";

const ROS_KEYS: ROSSystem[] = [
  "constitutional",
  "heent",
  "cardiovascular",
  "respiratory",
  "gastrointestinal",
  "genitourinary",
  "neurological",
  "musculoskeletal",
  "skin",
  "psychiatric",
];

function asStringArray(v: unknown): string[] {
  return Array.isArray(v) ? v.filter((x): x is string => typeof x === "string") : [];
}

function asString(v: unknown, fallback = ""): string {
  return typeof v === "string" ? v : fallback;
}

function normalizeBrief(
  raw: Record<string, unknown>,
  startedAt: string,
  turnCount: number,
  patientName: string,
  patientAge: string,
): ClinicalBrief {
  const hpiRaw = (raw.hpi ?? {}) as Record<string, unknown>;
  const rosRaw = (raw.ros ?? {}) as Record<string, unknown>;

  const ros = ROS_KEYS.reduce<ROS>((acc, key) => {
    acc[key] = asStringArray(rosRaw[key]);
    return acc;
  }, {} as ROS);

  const start = new Date(startedAt).getTime();
  const completedAt = new Date();
  const durationSeconds = Math.max(
    0,
    Math.round((completedAt.getTime() - start) / 1000),
  );

  return {
    patient_name: patientName || asString(raw.patient_name, ""),
    patient_age: patientAge || asString(raw.patient_age, ""),
    chief_complaint: asString(raw.chief_complaint, "Not stated"),
    hpi: {
      onset: asString(hpiRaw.onset),
      location: asString(hpiRaw.location),
      duration: asString(hpiRaw.duration),
      character: asString(hpiRaw.character),
      aggravating_factors: asStringArray(hpiRaw.aggravating_factors),
      alleviating_factors: asStringArray(hpiRaw.alleviating_factors),
      radiation:
        typeof hpiRaw.radiation === "string" ? (hpiRaw.radiation as string) : null,
      timing: asString(hpiRaw.timing),
      severity:
        typeof hpiRaw.severity === "number" ? (hpiRaw.severity as number) : 0,
      associated_symptoms: asStringArray(hpiRaw.associated_symptoms),
      narrative: asString(hpiRaw.narrative),
    },
    ros,
    red_flags: asStringArray(raw.red_flags),
    patient_concerns: asString(raw.patient_concerns),
    intake_metadata: {
      duration_seconds: durationSeconds,
      turn_count: turnCount,
      completed_at: completedAt.toISOString(),
    },
  };
}

export async function POST(request: NextRequest) {
  let body: ChatApiRequest;
  try {
    body = (await request.json()) as ChatApiRequest;
  } catch {
    return Response.json(
      { type: "error", message: "Invalid JSON body" } satisfies ChatApiResponse,
      { status: 400 },
    );
  }

  const startedAt = body.startedAt ?? new Date().toISOString();
  const incoming = Array.isArray(body.messages) ? body.messages : [];
  const cleaned = incoming.filter((m) => m.role !== "system");
  const patientName = (body.patientName ?? "").trim();
  const patientAge = (body.patientAge ?? "").trim();

  const provider: ProviderId =
    body.provider && VALID_PROVIDERS.includes(body.provider)
      ? body.provider
      : "gemini";

  // Most OpenAI-compat providers reject a request that contains only a system
  // message. Inject a kickoff user turn so the model produces the greeting as
  // its first ask_followup tool call.
  //
  // Kickoff phrasing matters more than expected:
  //  - Bracketed instructions like "[Begin intake — greet the patient...]"
  //    cause Llama 3.1 8B to truncate to {"text": "Hello, I"} (it satisfies
  //    the schema with the minimum viable string).
  //  - A bare "Hello" makes Gemini return 0 tokens (it has no context to
  //    decide what to do given the dual ask_followup/finalize_brief tools).
  //  - The narrated, scene-setting form below is a Goldilocks fit: long
  //    enough for Gemini to parse intent, natural enough for Llama 8B to
  //    produce a complete greeting.
  // Kickoff phrasing matters a lot for small models (Llama 3.1 8B):
  //  - Brackets/parens cause it to truncate to a minimum-viable string.
  //  - Scene-setting language ("walked into the exam room") gets parroted
  //    verbatim into the greeting, which sounds clinical and cold.
  // Keep it neutral: state who the patient is, ask for a warm greeting.
  const ageClause = patientAge ? `, age ${patientAge}` : "";
  const kickoff = patientName
    ? `Your next patient is ${patientName}${ageClause}. Greet them warmly by name and ask what brings them in today, all in one friendly sentence ending with a question mark.`
    : "Begin the intake. Greet the patient warmly and ask what brings them in today, all in one friendly sentence ending with a question mark.";

  const seeded =
    cleaned.length === 0
      ? [{ role: "user" as const, content: kickoff }]
      : cleaned;

  const userTurns = cleaned.filter((m) => m.role === "user").length;
  const atCap = userTurns >= MAX_USER_TURNS;

  const messages = [
    { role: "system" as const, content: INTAKE_SYSTEM_PROMPT },
    ...seeded,
    ...(atCap
      ? [
          {
            role: "system" as const,
            content:
              "TURN LIMIT REACHED. You have gathered sufficient information. Your next response MUST call the finalize_brief tool with the structured brief based only on what the patient has stated. Do not ask any further questions.",
          },
        ]
      : []),
  ];

  try {
    const call = await callLlm(
      messages,
      provider,
      atCap ? "finalize_brief" : undefined,
    );

    if (call.name === "finalize_brief") {
      const brief = normalizeBrief(
        call.args,
        startedAt,
        userTurns,
        patientName,
        patientAge,
      );
      return Response.json({ type: "brief", brief } satisfies ChatApiResponse);
    }

    return Response.json({
      type: "question",
      text: call.args.text,
    } satisfies ChatApiResponse);
  } catch (err) {
    const message = err instanceof Error ? err.message : "Unknown error";
    return Response.json(
      { type: "error", message } satisfies ChatApiResponse,
      { status: 500 },
    );
  }
}
