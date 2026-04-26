# Wardly — Pre-visit Clinical Intake Agent

A voice-driven (or text-driven) agent that conducts a pre-visit intake with a simulated patient and emits a structured **CC / HPI / ROS** clinical brief a doctor can scan in 30 seconds.

Built as a take-home for the Wardly Founding Engineer role.

---

## What it does

1. The agent greets the patient and asks what brought them in.
2. It walks **OLDCARTS** for the HPI, weaving questions naturally instead of interrogating.
3. It triggers **red-flag follow-ups** when the chief complaint matches a high-risk pattern (chest pain → radiation, dyspnea, diaphoresis; headache → "worst-ever", vision; etc.).
4. It sweeps a **targeted ROS** for the relevant systems plus a general screen.
5. When sufficient history is gathered, it calls a `finalize_brief` tool that emits a typed JSON object the UI renders into a clinician-ready card.

The whole round-trip — speech → LLM → speech — is fast enough to feel conversational. Default LLM is **Gemini 2.5 Flash** via the OpenAI-compatible endpoint (no card, generous free tier); the wrapper in `lib/llm.ts` is provider-agnostic so Groq + Llama 3.3 is also a one-env-var swap.

---

## Architecture

```
┌──────────────────────────────────────────────────────────────┐
│  Browser (Chrome — Web Speech API)                           │
│                                                              │
│  MicButton  ─►  webkitSpeechRecognition  ─►  transcript      │
│                                                  │           │
│                                       POST /api/chat         │
│                                                  ▼           │
└──────────────────────────────────────────────────────────────┘
                                                   │
┌──────────────────────────────────────────────────┼───────────┐
│  Next.js API route (server)                      ▼           │
│   app/api/chat/route.ts                                      │
│    ├─ prepend INTAKE_SYSTEM_PROMPT                           │
│    ├─ call LLM (Gemini 2.5 Flash, OpenAI-compat) w/ tools:   │
│    │     • ask_followup({ text })   → next question          │
│    │     • finalize_brief({ ... })  → typed ClinicalBrief    │
│    └─ return { type: "question" | "brief" | "error" }        │
└──────────────────────────────────────────────────┬───────────┘
                                                   │
┌──────────────────────────────────────────────────▼───────────┐
│  Browser (response handler)                                  │
│   • question → speechSynthesis.speak(text); append to log    │
│   • brief    → render <BriefCard /> + Copy/Download JSON     │
└──────────────────────────────────────────────────────────────┘
```

---

## Design choices

### Provider-agnostic LLM layer (currently Gemini 2.5 Flash)
`lib/llm.ts` is a thin wrapper around an OpenAI-compatible Chat Completions endpoint. The default config uses **Gemini 2.5 Flash** via Google's OpenAI-compat layer because:
- Free tier has 10 RPM / 250 RPD / 1M tokens-a-day — enough headroom for real conversational use without a credit card.
- Native function-calling support → reliable structured output, no JSON-parsing hacks.
- Swapping providers is one env var change. The repo's `.env.example` includes a Groq + Llama 3.3 70B alternative (faster TTFT, tighter free tier).

### Why two tools instead of "parse JSON from text"
The agent has exactly two tools: `ask_followup({ text })` and `finalize_brief({ brief })`. Every turn picks one. This eliminates the most common reliability failure of structured-output LLMs — the model deciding to wrap JSON in prose. The schema lives in `lib/tools.ts` and matches the `ClinicalBrief` TypeScript type in `lib/schema.ts`. There is also a defensive recovery path in `lib/llm.ts` for providers (notably Llama-on-Groq) that emit tool calls in a `<function=name{json}</function>` blob format instead of populating the structured field.

### Why browser STT/TTS (Web Speech API) instead of a real phone call
A real inbound/outbound call requires Twilio, which requires a credit card. The Web Speech API is browser-native, free, and demos as a voice agent in a Loom recording. The voice I/O layer is intentionally isolated in `hooks/useSpeech.ts` so swapping in Twilio + Deepgram + ElevenLabs for production is a clean replacement of one file.

### Why a server route instead of calling the LLM from the client
Keeps the API key off the public bundle. The original env spec used `NEXT_PUBLIC_*`, which would have inlined the key into JS shipped to every browser — fine for a closed demo, dangerous for a public GitHub repo.

### Why the agent never diagnoses or reassures
The system prompt explicitly forbids diagnostic language and reassurance. If a patient describes chest pain with arm radiation, the agent calmly continues gathering info — the brief's `red_flags` array is what surfaces the urgency to the human clinician.

---

## Run locally

Requires Node 20+ and Chrome (Web Speech API is unreliable in Safari/Firefox).

```bash
cp .env.example .env.local
# Edit .env.local and put your Gemini key in LLM_API_KEY
# Get a free key at https://aistudio.google.com/apikey (no card required)

npm install
npm run dev
```

Open `http://localhost:3000` in Chrome. Click the mic and start talking. Toggle **chat mode** in the header if you'd rather type.

---

## Try these scenarios

| Say this                                        | What to verify                                                                                      |
| ----------------------------------------------- | --------------------------------------------------------------------------------------------------- |
| "I've had a really bad sore throat for 3 days"  | Agent walks OLDCARTS, asks about swallowing, fever, fatigue. ROS focuses constitutional + HEENT.    |
| "I've been having chest pain since this morning" | Agent specifically asks about radiation to arm/jaw, shortness of breath, sweating. `red_flags` populated in the brief. |
| "I just don't feel right, kind of off"          | Agent probes constructively (open → narrowing) instead of fabricating symptoms.                     |

---

## File map

```
app/
  page.tsx                    Main UI orchestrator (client)
  layout.tsx
  api/chat/route.ts           Server-side Groq proxy + tool routing
lib/
  prompts.ts                  System prompt — biggest single quality lever
  schema.ts                   ClinicalBrief TS contract
  tools.ts                    JSON-schema tool defs (OpenAI-compat)
  llm.ts                      Provider-agnostic OpenAI-compat fetch wrapper + tool-call parsing
hooks/
  useSpeech.ts                Web Speech API wrapper (the prod-swap boundary)
components/
  MicButton.tsx
  ConversationLog.tsx
  BriefCard.tsx               CC / HPI / ROS render
  BriefActions.tsx            Copy JSON, download, reset
```

---

## Production swap (what would change)

The voice transport is the only layer tied to the browser. Everything else — prompt, tool schemas, brief normalization, UI — is reusable.

| Demo                                | Production                                              |
| ----------------------------------- | ------------------------------------------------------- |
| `useSpeech` (Web Speech API)        | Twilio inbound/outbound webhook                         |
| Browser STT                         | Deepgram streaming STT                                  |
| Browser TTS                         | ElevenLabs Turbo (or Deepgram Aura)                     |
| In-memory message history           | Postgres + Redis (resume on disconnect)                 |
| `.env.local` LLM key                | Vault / Doppler / KMS                                   |
| Render `<BriefCard />`              | POST brief into EHR / FHIR `Encounter` resource         |
| No PHI handling                     | BAA with model provider, encryption at rest + in transit |
| Single user                         | Per-org auth, audit log of every transcript             |

The `app/api/chat/route.ts` handler is unchanged — it still takes a message history and returns a question or a brief. Production-grade is a few new modules at the I/O boundary, not a rewrite.

---

## Known limitations

- Chrome-only (Web Speech API).
- English-only.
- No PHI handling — demo only.
- No persistence — refresh = new intake.
- No automated tests; verified manually against the three scenarios above.
- Gemini 2.5 Flash free tier: 10 RPM / 250 RPD — plenty for one intake but expect a 429 if you do 11 turns in 60 seconds. Surfaces as an inline error and resolves on the next try.
