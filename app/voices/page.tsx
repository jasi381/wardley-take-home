"use client";

import { useCallback, useEffect, useMemo, useRef, useState } from "react";
import Link from "next/link";

const SAMPLE_LINES = [
  "Hi, I'm here to gather some information before your visit with the doctor. What brings you in today?",
  "Got it — that sounds rough. When did it start, was it sudden or did it come on gradually?",
  "Thanks for telling me. On a scale of zero to ten, how would you rate the pain right now?",
  "Is there anything else you'd like the doctor to know before your visit?",
];

// ──────────────────────────────────────────────────────────────────────────────
// Cloud voices via Puter.js (free, unlimited, no API key).
// https://docs.puter.com/AI/txt2speech/
// ──────────────────────────────────────────────────────────────────────────────

type Provider = "polly" | "openai" | "elevenlabs";

type CloudVoice = {
  id: string;
  displayName: string;
  provider: Provider;
  language: string;
  flavour: string; // short human-readable description
  options: Record<string, unknown>;
};

const CLOUD_VOICES: CloudVoice[] = [
  // ─── AWS Polly (Puter default) — best clinical-friendly picks ───
  {
    id: "polly-joanna-neural",
    displayName: "Joanna (neural)",
    provider: "polly",
    language: "en-US",
    flavour: "US female · warm, default Polly voice",
    options: { voice: "Joanna", engine: "neural", language: "en-US" },
  },
  {
    id: "polly-ruth-generative",
    displayName: "Ruth (generative)",
    provider: "polly",
    language: "en-US",
    flavour: "US female · most natural Polly voice (generative engine)",
    options: { voice: "Ruth", engine: "generative", language: "en-US" },
  },
  {
    id: "polly-matthew-neural",
    displayName: "Matthew (neural)",
    provider: "polly",
    language: "en-US",
    flavour: "US male · professional, calm",
    options: { voice: "Matthew", engine: "neural", language: "en-US" },
  },
  {
    id: "polly-stephen-generative",
    displayName: "Stephen (generative)",
    provider: "polly",
    language: "en-US",
    flavour: "US male · very natural (generative engine)",
    options: { voice: "Stephen", engine: "generative", language: "en-US" },
  },
  {
    id: "polly-kendra-neural",
    displayName: "Kendra (neural)",
    provider: "polly",
    language: "en-US",
    flavour: "US female · calm, soft",
    options: { voice: "Kendra", engine: "neural", language: "en-US" },
  },
  {
    id: "polly-salli-neural",
    displayName: "Salli (neural)",
    provider: "polly",
    language: "en-US",
    flavour: "US female · friendly, slightly young",
    options: { voice: "Salli", engine: "neural", language: "en-US" },
  },
  {
    id: "polly-amy-neural",
    displayName: "Amy (neural)",
    provider: "polly",
    language: "en-GB",
    flavour: "UK female · warm British accent",
    options: { voice: "Amy", engine: "neural", language: "en-GB" },
  },
  {
    id: "polly-brian-neural",
    displayName: "Brian (neural)",
    provider: "polly",
    language: "en-GB",
    flavour: "UK male · authoritative, bedside-manner feel",
    options: { voice: "Brian", engine: "neural", language: "en-GB" },
  },

  // ─── OpenAI TTS via Puter ───
  {
    id: "openai-nova",
    displayName: "Nova (OpenAI)",
    provider: "openai",
    language: "en-US",
    flavour: "Warm female, very conversational — great for intake",
    options: { voice: "nova", model: "gpt-4o-mini-tts" },
  },
  {
    id: "openai-shimmer",
    displayName: "Shimmer (OpenAI)",
    provider: "openai",
    language: "en-US",
    flavour: "Soft, gentle female",
    options: { voice: "shimmer", model: "gpt-4o-mini-tts" },
  },
  {
    id: "openai-coral",
    displayName: "Coral (OpenAI)",
    provider: "openai",
    language: "en-US",
    flavour: "Friendly female, expressive",
    options: { voice: "coral", model: "gpt-4o-mini-tts" },
  },
  {
    id: "openai-sage",
    displayName: "Sage (OpenAI)",
    provider: "openai",
    language: "en-US",
    flavour: "Calm, warm female",
    options: { voice: "sage", model: "gpt-4o-mini-tts" },
  },
  {
    id: "openai-echo",
    displayName: "Echo (OpenAI)",
    provider: "openai",
    language: "en-US",
    flavour: "Calm male",
    options: { voice: "echo", model: "gpt-4o-mini-tts" },
  },
  {
    id: "openai-onyx",
    displayName: "Onyx (OpenAI)",
    provider: "openai",
    language: "en-US",
    flavour: "Deep, authoritative male",
    options: { voice: "onyx", model: "gpt-4o-mini-tts" },
  },

  // ─── ElevenLabs via Puter ───
  {
    id: "11l-rachel",
    displayName: "Rachel (ElevenLabs)",
    provider: "elevenlabs",
    language: "en-US",
    flavour: "Default ElevenLabs voice — very natural",
    options: {
      voice: "21m00Tcm4TlvDq8ikWAM",
      model: "eleven_multilingual_v2",
    },
  },
];

// ──────────────────────────────────────────────────────────────────────────────
// Types
// ──────────────────────────────────────────────────────────────────────────────

type ChosenVoice =
  | { kind: "browser"; voice: SpeechSynthesisVoice }
  | { kind: "cloud"; voice: CloudVoice };

type PuterSdk = {
  ai: {
    txt2speech: (
      text: string,
      options?: Record<string, unknown>,
    ) => Promise<HTMLAudioElement>;
  };
};

declare global {
  interface Window {
    puter?: PuterSdk;
  }
}

// ──────────────────────────────────────────────────────────────────────────────
// Component
// ──────────────────────────────────────────────────────────────────────────────

export default function VoicesPage() {
  const [voices, setVoices] = useState<SpeechSynthesisVoice[]>([]);
  const [filter, setFilter] = useState<"en" | "all">("en");
  const [text, setText] = useState(SAMPLE_LINES[0]);
  const [rate, setRate] = useState(1.02);
  const [pitch, setPitch] = useState(1.0);
  const [speakingId, setSpeakingId] = useState<string | null>(null);
  const [loadingId, setLoadingId] = useState<string | null>(null);
  const [chosen, setChosen] = useState<ChosenVoice | null>(null);
  const [supported, setSupported] = useState(true);
  const [puterReady, setPuterReady] = useState(false);
  const [puterError, setPuterError] = useState<string | null>(null);
  const cloudAudioRef = useRef<HTMLAudioElement | null>(null);

  // Browser TTS voices.
  useEffect(() => {
    if (typeof window === "undefined" || !("speechSynthesis" in window)) {
      setSupported(false);
      return;
    }
    const sync = () => setVoices(window.speechSynthesis.getVoices());
    sync();
    window.speechSynthesis.onvoiceschanged = sync;
    return () => {
      window.speechSynthesis.onvoiceschanged = null;
      window.speechSynthesis.cancel();
    };
  }, []);

  // Lazy-load Puter SDK (only on this page).
  useEffect(() => {
    if (typeof window === "undefined") return;
    if (window.puter) {
      setPuterReady(true);
      return;
    }
    const existing = document.querySelector(
      'script[src="https://js.puter.com/v2/"]',
    );
    if (existing) {
      existing.addEventListener("load", () => setPuterReady(true));
      return;
    }
    const s = document.createElement("script");
    s.src = "https://js.puter.com/v2/";
    s.async = true;
    s.onload = () => setPuterReady(true);
    s.onerror = () =>
      setPuterError("Failed to load Puter SDK (check network / ad-blocker).");
    document.body.appendChild(s);
  }, []);

  const stopAll = useCallback(() => {
    if (typeof window !== "undefined" && "speechSynthesis" in window) {
      window.speechSynthesis.cancel();
    }
    if (cloudAudioRef.current) {
      cloudAudioRef.current.pause();
      cloudAudioRef.current.src = "";
      cloudAudioRef.current = null;
    }
    setSpeakingId(null);
    setLoadingId(null);
  }, []);

  const speakBrowser = (voice: SpeechSynthesisVoice) => {
    if (!supported) return;
    stopAll();
    const id = `browser:${voice.voiceURI}`;
    const u = new SpeechSynthesisUtterance(text);
    u.voice = voice;
    u.lang = voice.lang;
    u.rate = rate;
    u.pitch = pitch;
    u.onstart = () => setSpeakingId(id);
    u.onend = () => setSpeakingId(null);
    u.onerror = () => setSpeakingId(null);
    window.speechSynthesis.speak(u);
  };

  const speakCloud = async (voice: CloudVoice) => {
    if (!puterReady || !window.puter) {
      setPuterError("Puter SDK not loaded yet. Wait a second and retry.");
      return;
    }
    stopAll();
    const id = `cloud:${voice.id}`;
    setLoadingId(id);
    setPuterError(null);
    try {
      const audio = await window.puter.ai.txt2speech(text, voice.options);
      cloudAudioRef.current = audio;
      audio.onplay = () => {
        setLoadingId(null);
        setSpeakingId(id);
      };
      audio.onended = () => {
        setSpeakingId(null);
        cloudAudioRef.current = null;
      };
      audio.onerror = () => {
        setSpeakingId(null);
        setLoadingId(null);
        setPuterError("Audio playback failed.");
        cloudAudioRef.current = null;
      };
      await audio.play();
    } catch (err) {
      setLoadingId(null);
      setSpeakingId(null);
      setPuterError(err instanceof Error ? err.message : "Puter call failed.");
    }
  };

  const browserRows = useMemo(() => {
    const filtered =
      filter === "en" ? voices.filter((v) => /^en/i.test(v.lang)) : voices;
    return filtered.slice().sort((a, b) => {
      if (a.localService !== b.localService) return a.localService ? -1 : 1;
      if (a.lang !== b.lang) return a.lang.localeCompare(b.lang);
      return a.name.localeCompare(b.name);
    });
  }, [voices, filter]);

  const isChosen = (id: string) =>
    chosen?.kind === "cloud"
      ? `cloud:${chosen.voice.id}` === id
      : chosen?.kind === "browser"
        ? `browser:${chosen.voice.voiceURI}` === id
        : false;

  return (
    <div className="flex-1 flex flex-col items-center px-4 py-8 sm:py-12 bg-zinc-50 dark:bg-zinc-950">
      <div className="w-full max-w-3xl flex flex-col gap-6">
        <header className="flex items-start justify-between gap-4">
          <div>
            <h1 className="text-2xl font-semibold tracking-tight text-zinc-900 dark:text-zinc-50">
              Voice picker
            </h1>
            <p className="text-sm text-zinc-500 mt-0.5">
              Audition cloud voices (via Puter — free, unlimited) and browser
              voices side by side. Click any to hear it read the sample line.
            </p>
          </div>
          <Link
            href="/"
            className="text-sm text-blue-600 hover:underline shrink-0"
          >
            ← back to intake
          </Link>
        </header>

        {!supported && (
          <div className="rounded-lg border border-amber-300 bg-amber-50 dark:bg-amber-950/30 dark:border-amber-800 p-3 text-sm text-amber-900 dark:text-amber-200">
            Browser speechSynthesis isn&apos;t available — cloud voices still work.
          </div>
        )}
        {puterError && (
          <div className="rounded-lg border border-red-300 bg-red-50 dark:bg-red-950/30 dark:border-red-800 p-3 text-sm text-red-900 dark:text-red-200">
            {puterError}
          </div>
        )}

        {/* Controls */}
        <section className="rounded-2xl border border-zinc-200 dark:border-zinc-800 bg-white dark:bg-zinc-900 p-4 space-y-4">
          <div>
            <label className="block text-xs font-medium text-zinc-500 uppercase tracking-wider mb-1">
              Sample line
            </label>
            <textarea
              value={text}
              onChange={(e) => setText(e.target.value)}
              rows={2}
              className="w-full rounded-lg border border-zinc-200 dark:border-zinc-700 bg-white dark:bg-zinc-950 p-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-600"
            />
            <div className="flex flex-wrap gap-2 mt-2">
              {SAMPLE_LINES.map((line, i) => (
                <button
                  key={i}
                  onClick={() => setText(line)}
                  className="text-xs px-2 py-1 rounded-full border border-zinc-200 dark:border-zinc-700 hover:bg-zinc-50 dark:hover:bg-zinc-800"
                >
                  Line {i + 1}
                </button>
              ))}
            </div>
          </div>

          <div className="grid grid-cols-2 gap-4">
            <div>
              <label className="block text-xs font-medium text-zinc-500 uppercase tracking-wider mb-1">
                Rate ({rate.toFixed(2)}) <span className="text-zinc-400">— browser only</span>
              </label>
              <input
                type="range"
                min="0.5"
                max="1.5"
                step="0.01"
                value={rate}
                onChange={(e) => setRate(parseFloat(e.target.value))}
                className="w-full accent-blue-600"
              />
            </div>
            <div>
              <label className="block text-xs font-medium text-zinc-500 uppercase tracking-wider mb-1">
                Pitch ({pitch.toFixed(2)}) <span className="text-zinc-400">— browser only</span>
              </label>
              <input
                type="range"
                min="0.5"
                max="1.5"
                step="0.01"
                value={pitch}
                onChange={(e) => setPitch(parseFloat(e.target.value))}
                className="w-full accent-blue-600"
              />
            </div>
          </div>

          <div className="flex items-center justify-between flex-wrap gap-2">
            <div className="flex gap-1 text-xs">
              <button
                onClick={() => setFilter("en")}
                className={`px-3 py-1 rounded-full border ${
                  filter === "en"
                    ? "bg-blue-600 text-white border-blue-600"
                    : "border-zinc-200 dark:border-zinc-700"
                }`}
              >
                English browser ({voices.filter((v) => /^en/i.test(v.lang)).length})
              </button>
              <button
                onClick={() => setFilter("all")}
                className={`px-3 py-1 rounded-full border ${
                  filter === "all"
                    ? "bg-blue-600 text-white border-blue-600"
                    : "border-zinc-200 dark:border-zinc-700"
                }`}
              >
                All browser ({voices.length})
              </button>
            </div>
            <button
              onClick={stopAll}
              disabled={!speakingId && !loadingId}
              className="text-xs px-3 py-1 rounded-full border border-zinc-300 dark:border-zinc-700 disabled:opacity-40"
            >
              Stop
            </button>
          </div>
        </section>

        {/* Chosen voice + snippet */}
        {chosen && (
          <SelectedSnippet chosen={chosen} rate={rate} pitch={pitch} />
        )}

        {/* Cloud voices section */}
        <section>
          <div className="flex items-baseline justify-between mb-2 px-1">
            <h2 className="text-sm font-semibold uppercase tracking-wider text-zinc-700 dark:text-zinc-300">
              Cloud voices · Puter
              <span className="ml-2 text-[10px] font-normal text-emerald-600 dark:text-emerald-400 normal-case tracking-normal">
                free · unlimited · no API key
              </span>
            </h2>
            <span className="text-[11px] text-zinc-400">
              {puterReady ? "SDK ready" : "loading SDK…"}
            </span>
          </div>
          <ul className="space-y-2">
            {CLOUD_VOICES.map((v) => {
              const id = `cloud:${v.id}`;
              const isSpeaking = speakingId === id;
              const isLoading = loadingId === id;
              const selected = isChosen(id);
              return (
                <li
                  key={v.id}
                  className={`flex items-center gap-3 rounded-xl border bg-white dark:bg-zinc-900 px-4 py-3 ${
                    selected
                      ? "border-emerald-400 dark:border-emerald-700"
                      : "border-zinc-200 dark:border-zinc-800"
                  }`}
                >
                  <div className="flex-1 min-w-0">
                    <p className="text-sm font-medium text-zinc-900 dark:text-zinc-100">
                      {v.displayName}
                    </p>
                    <p className="text-xs text-zinc-500">
                      <ProviderBadge provider={v.provider} />
                      <span className="ml-2">{v.language}</span>
                      <span className="ml-2 text-zinc-400">{v.flavour}</span>
                    </p>
                  </div>
                  <button
                    onClick={() => speakCloud(v)}
                    disabled={!puterReady || isLoading}
                    className={`px-3 py-1.5 rounded-full text-sm font-medium ${
                      isSpeaking
                        ? "bg-emerald-600 text-white animate-pulse"
                        : isLoading
                          ? "bg-zinc-400 text-white"
                          : "bg-zinc-900 dark:bg-zinc-100 text-white dark:text-zinc-900 hover:opacity-90 disabled:opacity-40"
                    }`}
                  >
                    {isLoading ? "Loading…" : isSpeaking ? "Speaking…" : "Speak"}
                  </button>
                  <button
                    onClick={() => setChosen({ kind: "cloud", voice: v })}
                    className={`px-3 py-1.5 rounded-full text-sm font-medium border ${
                      selected
                        ? "bg-emerald-600 text-white border-emerald-600"
                        : "border-zinc-300 dark:border-zinc-700 hover:bg-zinc-50 dark:hover:bg-zinc-800"
                    }`}
                  >
                    {selected ? "Selected" : "Use"}
                  </button>
                </li>
              );
            })}
          </ul>
        </section>

        {/* Browser voices section */}
        <section>
          <h2 className="text-sm font-semibold uppercase tracking-wider text-zinc-700 dark:text-zinc-300 mb-2 px-1">
            Browser voices · Web Speech API
            <span className="ml-2 text-[10px] font-normal text-zinc-400 normal-case tracking-normal">
              device-dependent, zero latency
            </span>
          </h2>
          <ul className="space-y-2">
            {browserRows.length === 0 && supported && (
              <li className="text-sm text-zinc-400 italic px-1">
                No voices yet — Chrome loads them async; reload if this persists.
              </li>
            )}
            {browserRows.map((voice) => {
              const id = `browser:${voice.voiceURI}`;
              const isSpeaking = speakingId === id;
              const selected = isChosen(id);
              return (
                <li
                  key={voice.voiceURI}
                  className={`flex items-center gap-3 rounded-xl border bg-white dark:bg-zinc-900 px-4 py-3 ${
                    selected
                      ? "border-emerald-400 dark:border-emerald-700"
                      : "border-zinc-200 dark:border-zinc-800"
                  }`}
                >
                  <div className="flex-1 min-w-0">
                    <p className="text-sm font-medium text-zinc-900 dark:text-zinc-100 truncate">
                      {voice.name}
                    </p>
                    <p className="text-xs text-zinc-500">
                      {voice.lang}
                      {voice.default && (
                        <span className="ml-2 px-1.5 py-0.5 rounded bg-zinc-100 dark:bg-zinc-800 text-[10px] uppercase tracking-wider">
                          default
                        </span>
                      )}
                      <span
                        className={`ml-2 px-1.5 py-0.5 rounded text-[10px] uppercase tracking-wider ${
                          voice.localService
                            ? "bg-zinc-100 dark:bg-zinc-800"
                            : "bg-amber-100 dark:bg-amber-900/40 text-amber-800 dark:text-amber-200"
                        }`}
                      >
                        {voice.localService ? "local" : "network"}
                      </span>
                    </p>
                  </div>
                  <button
                    onClick={() => speakBrowser(voice)}
                    disabled={!supported}
                    className={`px-3 py-1.5 rounded-full text-sm font-medium ${
                      isSpeaking
                        ? "bg-emerald-600 text-white animate-pulse"
                        : "bg-zinc-900 dark:bg-zinc-100 text-white dark:text-zinc-900 hover:opacity-90"
                    }`}
                  >
                    {isSpeaking ? "Speaking…" : "Speak"}
                  </button>
                  <button
                    onClick={() => setChosen({ kind: "browser", voice })}
                    className={`px-3 py-1.5 rounded-full text-sm font-medium border ${
                      selected
                        ? "bg-emerald-600 text-white border-emerald-600"
                        : "border-zinc-300 dark:border-zinc-700 hover:bg-zinc-50 dark:hover:bg-zinc-800"
                    }`}
                  >
                    {selected ? "Selected" : "Use"}
                  </button>
                </li>
              );
            })}
          </ul>
        </section>

        <footer className="pt-4 pb-8 text-xs text-zinc-400 text-center">
          Cloud voices route through Puter (a free wrapper around AWS Polly,
          OpenAI, ElevenLabs). No keys, no card. Trade-off: ~300–800ms latency
          per turn vs zero-latency browser TTS.
        </footer>
      </div>
    </div>
  );
}

function ProviderBadge({ provider }: { provider: Provider }) {
  const map: Record<Provider, { label: string; cls: string }> = {
    polly: {
      label: "AWS Polly",
      cls: "bg-orange-100 dark:bg-orange-900/40 text-orange-800 dark:text-orange-200",
    },
    openai: {
      label: "OpenAI",
      cls: "bg-emerald-100 dark:bg-emerald-900/40 text-emerald-800 dark:text-emerald-200",
    },
    elevenlabs: {
      label: "ElevenLabs",
      cls: "bg-violet-100 dark:bg-violet-900/40 text-violet-800 dark:text-violet-200",
    },
  };
  const { label, cls } = map[provider];
  return (
    <span className={`px-1.5 py-0.5 rounded text-[10px] uppercase tracking-wider ${cls}`}>
      {label}
    </span>
  );
}

function SelectedSnippet({
  chosen,
  rate,
  pitch,
}: {
  chosen: ChosenVoice;
  rate: number;
  pitch: number;
}) {
  if (chosen.kind === "browser") {
    const v = chosen.voice;
    const snippet = `// hooks/useSpeech.ts — replace the voice-selection block in speak():
const voices = window.speechSynthesis.getVoices();
const preferred =
  voices.find((v) => v.voiceURI === ${JSON.stringify(v.voiceURI)}) ??
  voices.find((v) => v.name === ${JSON.stringify(v.name)}) ??
  voices.find((v) => /en-US/i.test(v.lang)) ??
  voices[0];
if (preferred) u.voice = preferred;
u.rate = ${rate};
u.pitch = ${pitch};`;
    return (
      <SnippetCard
        title={`${v.name} · ${v.lang} · ${v.localService ? "local" : "network"}`}
        snippet={snippet}
      />
    );
  }

  const v = chosen.voice;
  const optsLine = JSON.stringify(v.options, null, 2);
  const snippet = `// 1) In app/layout.tsx (or anywhere globally), load the Puter SDK once:
//    <script src="https://js.puter.com/v2/" async />
//    Or dynamically inside hooks/useSpeech.ts on first speak().
//
// 2) Replace the speak() implementation in hooks/useSpeech.ts:
const speak = useCallback((text, onEnd) => {
  if (typeof window === "undefined" || !window.puter) {
    onEnd?.();
    return;
  }
  window.puter.ai.txt2speech(text, ${optsLine.replace(/\n/g, "\n    ")}).then((audio) => {
    cancelSpeech();
    audio.onplay  = () => setIsSpeaking(true);
    audio.onended = () => { setIsSpeaking(false); onEnd?.(); };
    audio.onerror = () => { setIsSpeaking(false); onEnd?.(); };
    audio.play();
  });
}, []);`;
  return (
    <SnippetCard
      title={`${v.displayName} · ${v.language} · ${v.provider}`}
      snippet={snippet}
    />
  );
}

function SnippetCard({ title, snippet }: { title: string; snippet: string }) {
  const [copied, setCopied] = useState(false);
  const copy = async () => {
    try {
      await navigator.clipboard.writeText(snippet);
      setCopied(true);
      setTimeout(() => setCopied(false), 1200);
    } catch {
      /* noop */
    }
  };
  return (
    <section className="rounded-2xl border border-emerald-300 dark:border-emerald-800 bg-emerald-50 dark:bg-emerald-950/40 p-4 space-y-2">
      <div className="flex items-center justify-between">
        <p className="text-xs font-semibold uppercase tracking-wider text-emerald-700 dark:text-emerald-300">
          Selected voice
        </p>
        <button
          onClick={copy}
          className="text-xs px-2 py-1 rounded-full bg-emerald-600 text-white hover:bg-emerald-700"
        >
          {copied ? "Copied" : "Copy snippet"}
        </button>
      </div>
      <p className="text-sm text-emerald-900 dark:text-emerald-100">{title}</p>
      <details className="text-xs">
        <summary className="cursor-pointer text-emerald-800 dark:text-emerald-200">
          How to wire this into the intake agent
        </summary>
        <pre className="mt-2 p-3 rounded bg-emerald-100 dark:bg-emerald-900/50 text-emerald-950 dark:text-emerald-50 overflow-x-auto whitespace-pre text-[11px] leading-relaxed">
{snippet}
        </pre>
      </details>
    </section>
  );
}
