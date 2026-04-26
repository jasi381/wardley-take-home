import { intakeTools } from "./tools";
import type { ChatMessage, ProviderId } from "./schema";

type LlmToolCall = {
  id: string;
  type: "function";
  function: { name: string; arguments: string };
};

type LlmResponseMessage = {
  role: "assistant";
  content: string | null;
  tool_calls?: LlmToolCall[];
};

type LlmChatResponse = {
  choices: { message: LlmResponseMessage; finish_reason: string }[];
};

export type ParsedToolCall =
  | { name: "ask_followup"; args: { text: string } }
  | { name: "finalize_brief"; args: Record<string, unknown> };

type ProviderConfig = {
  baseUrl: string;
  apiKey: string;
  model: string;
  // OpenAI-compat providers handle tool_choice inconsistently:
  //  - Gemini works best with "auto" (errors on "required" wrapper).
  //  - Cerebras (Qwen3) reliably emits structured tool_calls with "required".
  //  - Llama-on-Groq malforms tool calls regardless; recovery handles it.
  toolChoice: "auto" | "required";
};

function readProviderConfig(provider: ProviderId): ProviderConfig {
  if (provider === "cerebras") {
    const baseUrl = process.env.CEREBRAS_BASE_URL;
    const apiKey = process.env.CEREBRAS_API_KEY;
    const model = process.env.CEREBRAS_MODEL;
    if (!baseUrl || !apiKey || !model) {
      throw new Error(
        "Missing Cerebras env vars — set CEREBRAS_BASE_URL, CEREBRAS_API_KEY, CEREBRAS_MODEL in .env.local.",
      );
    }
    return { baseUrl, apiKey, model, toolChoice: "required" };
  }
  // default: gemini
  const baseUrl = process.env.LLM_BASE_URL;
  const apiKey = process.env.LLM_API_KEY;
  const model = process.env.LLM_MODEL;
  if (!baseUrl || !apiKey || !model) {
    throw new Error(
      "Missing Gemini env vars — set LLM_BASE_URL, LLM_API_KEY, LLM_MODEL in .env.local.",
    );
  }
  return { baseUrl, apiKey, model, toolChoice: "auto" };
}

const TOOL_NAMES = ["ask_followup", "finalize_brief"] as const;

// Some OpenAI-compat providers (notably Llama-on-Groq) emit tool calls as raw
// text in the form `<function=name{json}</function>` instead of populating the
// structured `tool_calls` field. Salvage those — harmless for providers that
// already return well-formed tool calls (Gemini, GLM, OpenAI, etc.).
function extractFunctionBlob(s: string): { name: string; argsJson: string } | null {
  if (!s) return null;
  for (const name of TOOL_NAMES) {
    const open = `<function=${name}`;
    const idx = s.indexOf(open);
    if (idx === -1) continue;
    const rest = s.slice(idx + open.length);
    const braceStart = rest.indexOf("{");
    if (braceStart === -1) continue;
    let depth = 0;
    let end = -1;
    let inString = false;
    let escape = false;
    for (let i = braceStart; i < rest.length; i++) {
      const ch = rest[i];
      if (escape) {
        escape = false;
        continue;
      }
      if (ch === "\\") {
        escape = true;
        continue;
      }
      if (ch === '"') {
        inString = !inString;
        continue;
      }
      if (inString) continue;
      if (ch === "{") depth++;
      else if (ch === "}") {
        depth--;
        if (depth === 0) {
          end = i + 1;
          break;
        }
      }
    }
    if (end === -1) continue;
    return { name, argsJson: rest.slice(braceStart, end) };
  }
  return null;
}

function normalize(name: string, args: Record<string, unknown>): ParsedToolCall {
  if (name === "finalize_brief") {
    return { name: "finalize_brief", args };
  }
  const text =
    typeof args.text === "string" && args.text.trim()
      ? (args.text as string).trim()
      : "Could you tell me a bit more about that?";
  return { name: "ask_followup", args: { text } };
}

function safeParseJson(s: string): Record<string, unknown> {
  try {
    const parsed = JSON.parse(s);
    return parsed && typeof parsed === "object"
      ? (parsed as Record<string, unknown>)
      : {};
  } catch {
    return {};
  }
}

type ForceTool = "ask_followup" | "finalize_brief";

async function callOnce(
  cfg: ProviderConfig,
  messages: ChatMessage[],
  provider: ProviderId,
  forceTool?: ForceTool,
): Promise<{ tool: ParsedToolCall | null; raw: { call?: unknown; content: string } }> {
  const toolChoice = forceTool
    ? { type: "function" as const, function: { name: forceTool } }
    : cfg.toolChoice;

  const res = await fetch(cfg.baseUrl, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${cfg.apiKey}`,
    },
    body: JSON.stringify({
      model: cfg.model,
      messages,
      tools: intakeTools,
      tool_choice: toolChoice,
      parallel_tool_calls: false,
      temperature: 0.3,
      max_tokens: 1800,
    }),
  });

  if (!res.ok) {
    const bodyText = await res.text();
    if (res.status === 400 && bodyText.includes("tool_use_failed")) {
      try {
        const errBody = JSON.parse(bodyText) as {
          error?: { failed_generation?: string };
        };
        const blob = errBody.error?.failed_generation ?? "";
        const recovered = extractFunctionBlob(blob);
        if (recovered) {
          return {
            tool: normalize(recovered.name, safeParseJson(recovered.argsJson)),
            raw: { content: "" },
          };
        }
      } catch {
        /* fall through */
      }
    }
    throw new Error(`${provider} ${res.status}: ${bodyText}`);
  }

  const data = (await res.json()) as LlmChatResponse;
  const message = data.choices?.[0]?.message;
  const call = message?.tool_calls?.[0];

  if (call) {
    return {
      tool: normalize(call.function.name, safeParseJson(call.function.arguments || "{}")),
      raw: { call, content: "" },
    };
  }

  const content = (message?.content ?? "").trim();
  const recovered = extractFunctionBlob(content);
  if (recovered) {
    return {
      tool: normalize(recovered.name, safeParseJson(recovered.argsJson)),
      raw: { content },
    };
  }

  return { tool: null, raw: { content } };
}

// Llama 3.1 8B occasionally emits a truncated `ask_followup` arg
// (e.g. `{"text": "Hello Jasmeet, I"}`). A short string with no question mark
// is the giveaway — real follow-ups are full sentences ending in `?`.
function looksTruncated(text: string | undefined): boolean {
  if (!text) return true;
  const trimmed = text.trim();
  if (trimmed.length >= 30) return false;
  return !trimmed.includes("?");
}

export async function callLlm(
  messages: ChatMessage[],
  provider: ProviderId = "gemini",
  forceTool?: ForceTool,
): Promise<ParsedToolCall> {
  const cfg = readProviderConfig(provider);
  const result = await callOnce(cfg, messages, provider, forceTool);

  if (result.tool) {
    if (
      result.tool.name === "ask_followup" &&
      !forceTool &&
      looksTruncated(result.tool.args.text)
    ) {
      const retry = await callOnce(cfg, messages, provider, forceTool);
      if (
        retry.tool &&
        retry.tool.name === "ask_followup" &&
        !looksTruncated(retry.tool.args.text)
      ) {
        return retry.tool;
      }
    }
    return result.tool;
  }

  // When forced to finalize, don't retry with an ask_followup nudge — fabricate
  // a minimal brief from whatever the model emitted as content (rare path).
  if (forceTool === "finalize_brief") {
    return { name: "finalize_brief", args: {} };
  }

  // Empty response (Gemini occasionally returns 0 tokens with no tool_call and
  // no content). Retry once with a slightly stronger nudge appended.
  if (!result.raw.content) {
    const nudged: ChatMessage[] = [
      ...messages,
      {
        role: "user",
        content:
          "Continue. Respond by calling the ask_followup tool with your next short question.",
      },
    ];
    const retry = await callOnce(cfg, nudged, provider);
    if (retry.tool) return retry.tool;
    return {
      name: "ask_followup",
      args: { text: retry.raw.content || "Could you tell me a bit more about that?" },
    };
  }

  return {
    name: "ask_followup",
    args: { text: result.raw.content },
  };
}
