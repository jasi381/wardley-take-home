"use client";

import { useCallback, useEffect, useRef, useState } from "react";
import { MicButton, micStatusLabel } from "@/components/MicButton";
import { ChatBubble, ConversationLog } from "@/components/ConversationLog";
import { BriefCard } from "@/components/BriefCard";
import { BriefActions } from "@/components/BriefActions";
import { VoiceSelector } from "@/components/VoiceSelector";
import { ProviderSelector } from "@/components/ProviderSelector";
import { useSpeech } from "@/hooks/useSpeech";
import {
  CLOUD_VOICES,
  DEFAULT_CLOUD_VOICE_ID,
  findCloudVoice,
  type CloudVoice,
} from "@/lib/cloudVoices";
import type {
  ChatApiResponse,
  ChatMessage,
  ClinicalBrief,
  ProviderId,
} from "@/lib/schema";

const VOICE_STORAGE_KEY = "wardly.voiceId";
const PROVIDER_STORAGE_KEY = "wardly.providerId";
const VALID_PROVIDER_IDS: ProviderId[] = ["gemini", "cerebras"];
const INTRO_LINE =
  "Hi, welcome to your pre-visit intake. I'm the nurse who'll gather a few details before you see the doctor — could I start with your name and age?";

type Phase = "idle" | "speaking" | "listening" | "thinking" | "done";

export default function Home() {
  const [messages, setMessages] = useState<ChatMessage[]>([]);
  const [brief, setBrief] = useState<ClinicalBrief | null>(null);
  const [phase, setPhase] = useState<Phase>("idle");
  const [error, setError] = useState<string | null>(null);
  const [chatMode, setChatMode] = useState(false);
  const [textInput, setTextInput] = useState("");
  const [cloudVoice, setCloudVoice] = useState<CloudVoice | null>(() =>
    findCloudVoice(DEFAULT_CLOUD_VOICE_ID),
  );
  const [provider, setProvider] = useState<ProviderId>("cerebras");
  const [patientName, setPatientName] = useState<string>("");
  const [patientAge, setPatientAge] = useState<string>("");
  const [nameInput, setNameInput] = useState<string>("");
  const [ageInput, setAgeInput] = useState<string>("");
  const providerRef = useRef<ProviderId>("cerebras");
  const patientNameRef = useRef<string>("");
  const patientAgeRef = useRef<string>("");
  const startedAtRef = useRef<string | null>(null);
  const phaseRef = useRef<Phase>("idle");
  const introSpokenRef = useRef(false);

  useEffect(() => {
    phaseRef.current = phase;
  }, [phase]);

  useEffect(() => {
    providerRef.current = provider;
  }, [provider]);

  useEffect(() => {
    patientNameRef.current = patientName;
  }, [patientName]);

  useEffect(() => {
    patientAgeRef.current = patientAge;
  }, [patientAge]);

  // Persist voice + provider choice across reloads.
  useEffect(() => {
    if (typeof window === "undefined") return;
    const savedVoice = window.localStorage.getItem(VOICE_STORAGE_KEY);
    if (savedVoice === "browser") {
      setCloudVoice(null);
    } else if (savedVoice) {
      const found = CLOUD_VOICES.find((v) => v.id === savedVoice);
      if (found) setCloudVoice(found);
    }
    const savedProvider = window.localStorage.getItem(PROVIDER_STORAGE_KEY);
    if (savedProvider && VALID_PROVIDER_IDS.includes(savedProvider as ProviderId)) {
      setProvider(savedProvider as ProviderId);
    }
  }, []);

  const handleVoiceChange = useCallback((next: CloudVoice | null) => {
    setCloudVoice(next);
    if (typeof window !== "undefined") {
      window.localStorage.setItem(VOICE_STORAGE_KEY, next?.id ?? "browser");
    }
  }, []);

  const handleProviderChange = useCallback((next: ProviderId) => {
    setProvider(next);
    if (typeof window !== "undefined") {
      window.localStorage.setItem(PROVIDER_STORAGE_KEY, next);
    }
  }, []);

  const handleResponse = useCallback(
    (data: ChatApiResponse, autoSpeak: boolean, speak: (t: string, cb?: () => void) => void) => {
      if (data.type === "error") {
        setError(data.message);
        setPhase("idle");
        return;
      }
      if (data.type === "brief") {
        setBrief(data.brief);
        setPhase("done");
        return;
      }
      setMessages((prev) => [...prev, { role: "assistant", content: data.text }]);
      if (autoSpeak) {
        setPhase("speaking");
        speak(data.text, () => {
          setPhase("idle");
        });
      } else {
        setPhase("idle");
      }
    },
    [],
  );

  const sendMessages = useCallback(
    async (next: ChatMessage[], autoSpeak: boolean, speak: (t: string, cb?: () => void) => void) => {
      setPhase("thinking");
      setError(null);
      try {
        if (!startedAtRef.current) startedAtRef.current = new Date().toISOString();
        const res = await fetch("/api/chat", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            messages: next,
            startedAt: startedAtRef.current,
            provider: providerRef.current,
            patientName: patientNameRef.current,
            patientAge: patientAgeRef.current,
          }),
        });
        const data = (await res.json()) as ChatApiResponse;
        handleResponse(data, autoSpeak, speak);
      } catch (err) {
        setError(err instanceof Error ? err.message : "Network error");
        setPhase("idle");
      }
    },
    [handleResponse],
  );

  const handleFinalTranscript = useCallback(
    (text: string) => {
      const trimmed = text.trim();
      if (!trimmed) {
        setPhase("idle");
        return;
      }
      const next: ChatMessage[] = [
        ...messages,
        { role: "user", content: trimmed },
      ];
      setMessages(next);
      sendMessages(next, true, speak);
    },
    // speak is stable from useSpeech; messages captured at call time via closure
    // eslint-disable-next-line react-hooks/exhaustive-deps
    [messages, sendMessages],
  );

  const {
    supported,
    isListening,
    isSpeaking,
    interimTranscript,
    startListening,
    stopListening,
    speak,
    cancelSpeech,
  } = useSpeech(handleFinalTranscript, { cloudVoice });

  const startIntake = useCallback(async () => {
    setBrief(null);
    setMessages([]);
    setError(null);
    startedAtRef.current = new Date().toISOString();
    await sendMessages([], !chatMode, speak);
  }, [chatMode, sendMessages, speak]);

  const handleMicPress = useCallback(() => {
    if (phase === "thinking" || phase === "done") return;
    if (isSpeaking) {
      cancelSpeech();
      setPhase("idle");
      return;
    }
    if (isListening) {
      stopListening();
      return;
    }
    if (messages.length === 0) {
      startIntake();
      return;
    }
    setPhase("listening");
    startListening();
  }, [
    phase,
    isSpeaking,
    isListening,
    messages.length,
    cancelSpeech,
    stopListening,
    startListening,
    startIntake,
  ]);

  const sendTyped = useCallback(() => {
    const t = textInput.trim();
    if (!t) return;
    setTextInput("");
    if (messages.length === 0) {
      const opener: ChatMessage[] = [{ role: "user", content: t }];
      setMessages(opener);
      sendMessages(opener, !chatMode, speak);
      return;
    }
    const next: ChatMessage[] = [...messages, { role: "user", content: t }];
    setMessages(next);
    sendMessages(next, !chatMode, speak);
  }, [textInput, messages, chatMode, sendMessages, speak]);

  const reset = useCallback(() => {
    cancelSpeech();
    stopListening();
    setBrief(null);
    setMessages([]);
    setError(null);
    setPhase("idle");
    setPatientName("");
    setPatientAge("");
    setNameInput("");
    setAgeInput("");
    startedAtRef.current = null;
    introSpokenRef.current = false;
  }, [cancelSpeech, stopListening]);

  const submitDetails = useCallback(() => {
    const name = nameInput.trim();
    const age = ageInput.trim();
    if (!name || !age) return;
    setPatientName(name);
    setPatientAge(age);
    patientNameRef.current = name;
    patientAgeRef.current = age;
    startedAtRef.current = new Date().toISOString();
    sendMessages([], !chatMode, speak);
  }, [nameInput, ageInput, chatMode, sendMessages, speak]);

  // Reflect mic state into phase
  useEffect(() => {
    if (isListening && phaseRef.current !== "listening") setPhase("listening");
  }, [isListening]);

  // Voice the intro line as soon as TTS is ready, while the name+age form
  // is still showing. Browsers may suppress autoplay until a user gesture —
  // the focus-handler on the inputs covers that fallback.
  useEffect(() => {
    if (introSpokenRef.current) return;
    if (chatMode || patientName) return;
    if (supported !== true) return;
    introSpokenRef.current = true;
    setPhase("speaking");
    speak(INTRO_LINE, () => setPhase("idle"));
  }, [chatMode, patientName, supported, speak]);

  const handleFormFocus = useCallback(() => {
    if (introSpokenRef.current) return;
    if (chatMode || patientName) return;
    introSpokenRef.current = true;
    setPhase("speaking");
    speak(INTRO_LINE, () => setPhase("idle"));
  }, [chatMode, patientName, speak]);

  const isThinking = phase === "thinking";
  const micDisabled = !supported || phase === "thinking" || phase === "done";

  return (
    <div className="flex-1 min-h-0 flex flex-col items-center px-4 py-8 sm:py-12 bg-zinc-50 dark:bg-zinc-950">
      <div className="w-full max-w-2xl flex flex-col gap-6 flex-1 min-h-0">
        <header className="flex flex-col gap-4">
          <div className="flex items-start justify-between gap-4 flex-wrap">
            <div className="flex items-center gap-3">
              <div
                aria-hidden
                className="w-10 h-10 rounded-xl bg-blue-600 text-white flex items-center justify-center shadow-sm"
              >
                <svg
                  viewBox="0 0 24 24"
                  fill="none"
                  stroke="currentColor"
                  strokeWidth="1.75"
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  className="w-5 h-5"
                >
                  <path d="M6 3v6a6 6 0 0 0 12 0V3" />
                  <path d="M6 3h2" />
                  <path d="M16 3h2" />
                  <path d="M12 15v3a3 3 0 0 0 6 0v-1" />
                  <circle cx="18" cy="17" r="2" />
                </svg>
              </div>
              <div>
                <p className="text-[11px] font-semibold uppercase tracking-wider text-blue-600 dark:text-blue-400">
                  Wardly Clinic
                </p>
                <h1 className="text-xl sm:text-2xl font-semibold tracking-tight text-zinc-900 dark:text-zinc-50 leading-tight">
                  Pre-visit Intake
                </h1>
              </div>
            </div>
            <div className="flex items-center gap-2.5 shrink-0 flex-wrap">
              <ProviderSelector
                value={provider}
                onChange={handleProviderChange}
                disabled={phase === "thinking" || phase === "speaking"}
              />
              <VoiceSelector
                value={cloudVoice}
                onChange={handleVoiceChange}
                disabled={isSpeaking || phase === "thinking"}
              />
              <a
                href="/voices"
                className="text-xs text-zinc-500 hover:text-blue-600 hover:underline"
                title="Audition all available voices"
              >
                audition
              </a>
              <label
                className="flex items-center gap-1.5 text-xs text-zinc-500 select-none cursor-pointer"
                title="Switch between voice and chat input"
              >
                <input
                  type="checkbox"
                  checked={chatMode}
                  onChange={(e) => setChatMode(e.target.checked)}
                  className="accent-blue-600 cursor-pointer"
                />
                Chat
              </label>
            </div>
          </div>
          <p className="text-sm text-zinc-500">
            A few quick questions before you see the doctor — your responses
            will be summarized for your clinician.
          </p>
        </header>

        {supported === false && (
          <div
            role="alert"
            className="rounded-xl border border-amber-200 bg-amber-50 dark:bg-amber-950/30 dark:border-amber-900 p-3 text-sm text-amber-900 dark:text-amber-200 flex gap-2.5"
          >
            <svg
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              strokeWidth="1.75"
              strokeLinecap="round"
              strokeLinejoin="round"
              className="w-4 h-4 mt-0.5 shrink-0"
              aria-hidden
            >
              <circle cx="12" cy="12" r="10" />
              <line x1="12" y1="8" x2="12" y2="12" />
              <line x1="12" y1="16" x2="12.01" y2="16" />
            </svg>
            <span>
              Web Speech API isn&apos;t available in this browser. Use Chrome on
              desktop, or toggle <strong>Chat</strong> to type instead.
            </span>
          </div>
        )}

        {error && (
          <div
            role="alert"
            className="rounded-xl border border-red-200 bg-red-50 dark:bg-red-950/30 dark:border-red-900 p-3 text-sm text-red-900 dark:text-red-200 flex gap-2.5"
          >
            <svg
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              strokeWidth="1.75"
              strokeLinecap="round"
              strokeLinejoin="round"
              className="w-4 h-4 mt-0.5 shrink-0"
              aria-hidden
            >
              <circle cx="12" cy="12" r="10" />
              <line x1="15" y1="9" x2="9" y2="15" />
              <line x1="9" y1="9" x2="15" y2="15" />
            </svg>
            <span>{error}</span>
          </div>
        )}

        {brief ? (
          <>
            <div className="flex-1 min-h-0 w-full overflow-y-auto overscroll-contain">
              <BriefCard brief={brief} />
            </div>
            <BriefActions brief={brief} onReset={reset} />
          </>
        ) : (
          <>
            <ConversationLog
              messages={messages}
              interim={interimTranscript}
              isThinking={isThinking}
              patientName={patientName}
              prelude={
                <>
                  <ChatBubble role="assistant" text={INTRO_LINE} />
                  {patientName && (
                    <ChatBubble
                      role="user"
                      text={`Name: ${patientName}${patientAge ? ` · Age: ${patientAge}` : ""}`}
                    />
                  )}
                </>
              }
            />

            {!patientName ? (
              <div className="rounded-2xl border border-zinc-200 dark:border-zinc-800 bg-white dark:bg-zinc-950 p-4 shadow-sm">
                <div className="grid grid-cols-1 sm:grid-cols-[1fr_120px_auto] gap-3 sm:items-end">
                  <div className="flex flex-col gap-1.5">
                    <label
                      htmlFor="patient-name"
                      className="text-xs font-medium text-zinc-700 dark:text-zinc-200"
                    >
                      Full name
                    </label>
                    <input
                      id="patient-name"
                      type="text"
                      autoComplete="name"
                      value={nameInput}
                      onChange={(e) => setNameInput(e.target.value)}
                      onFocus={handleFormFocus}
                      onKeyDown={(e) => {
                        if (
                          e.key === "Enter" &&
                          nameInput.trim() &&
                          ageInput.trim()
                        )
                          submitDetails();
                      }}
                      placeholder="e.g. Jasmeet Singh"
                      autoFocus
                      className="w-full h-11 rounded-lg border border-zinc-300 dark:border-zinc-700 bg-white dark:bg-zinc-900 px-3 text-sm text-zinc-900 dark:text-zinc-100 placeholder:text-zinc-400 focus:outline-none focus:ring-2 focus:ring-blue-500/40 focus:border-blue-500"
                    />
                  </div>
                  <div className="flex flex-col gap-1.5">
                    <label
                      htmlFor="patient-age"
                      className="text-xs font-medium text-zinc-700 dark:text-zinc-200"
                    >
                      Age
                    </label>
                    <input
                      id="patient-age"
                      type="number"
                      inputMode="numeric"
                      min={0}
                      max={120}
                      value={ageInput}
                      onChange={(e) => setAgeInput(e.target.value)}
                      onFocus={handleFormFocus}
                      onKeyDown={(e) => {
                        if (
                          e.key === "Enter" &&
                          nameInput.trim() &&
                          ageInput.trim()
                        )
                          submitDetails();
                      }}
                      placeholder="—"
                      className="w-full h-11 rounded-lg border border-zinc-300 dark:border-zinc-700 bg-white dark:bg-zinc-900 px-3 text-sm text-zinc-900 dark:text-zinc-100 placeholder:text-zinc-400 focus:outline-none focus:ring-2 focus:ring-blue-500/40 focus:border-blue-500"
                    />
                  </div>
                  <button
                    onClick={submitDetails}
                    disabled={!nameInput.trim() || !ageInput.trim()}
                    className="h-11 px-5 rounded-lg bg-blue-600 text-white text-sm font-medium hover:bg-blue-700 focus:outline-none focus-visible:ring-2 focus-visible:ring-blue-500/40 disabled:opacity-50 disabled:cursor-not-allowed cursor-pointer transition-colors"
                  >
                    Continue
                  </button>
                </div>
                <p className="mt-2.5 text-[11px] text-zinc-400">
                  Used only for this visit · not stored.
                </p>
              </div>
            ) : chatMode ? (
              <div className="flex gap-2 items-center">
                <button
                  type="button"
                  onClick={() => setChatMode(false)}
                  disabled={isThinking}
                  aria-label="Switch to voice"
                  title="Switch to voice"
                  className="shrink-0 h-11 w-11 rounded-lg border border-zinc-200 dark:border-zinc-700 bg-white dark:bg-zinc-900 text-zinc-600 dark:text-zinc-300 hover:text-blue-600 dark:hover:text-blue-400 hover:border-blue-200 dark:hover:border-blue-900 hover:bg-blue-50/50 dark:hover:bg-blue-950/20 focus:outline-none focus-visible:ring-2 focus-visible:ring-blue-500/40 disabled:opacity-50 disabled:cursor-not-allowed cursor-pointer transition-colors flex items-center justify-center"
                >
                  <svg
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    strokeWidth="1.75"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    className="w-4 h-4"
                    aria-hidden
                  >
                    <rect x="9" y="2" width="6" height="12" rx="3" />
                    <path d="M5 10v2a7 7 0 0 0 14 0v-2" />
                    <line x1="12" y1="19" x2="12" y2="22" />
                  </svg>
                </button>
                <input
                  type="text"
                  value={textInput}
                  onChange={(e) => setTextInput(e.target.value)}
                  onKeyDown={(e) => {
                    if (e.key === "Enter" && !isThinking) sendTyped();
                  }}
                  placeholder={
                    messages.length === 0
                      ? "Type to start (e.g. 'I've had a sore throat for 3 days')"
                      : "Type your reply…"
                  }
                  disabled={isThinking}
                  className="flex-1 h-11 rounded-lg border border-zinc-300 dark:border-zinc-700 bg-white dark:bg-zinc-900 px-4 text-sm text-zinc-900 dark:text-zinc-100 placeholder:text-zinc-400 focus:outline-none focus:ring-2 focus:ring-blue-500/40 focus:border-blue-500 disabled:opacity-50"
                />
                <button
                  onClick={sendTyped}
                  disabled={isThinking || !textInput.trim()}
                  className="h-11 px-5 rounded-lg bg-blue-600 text-white text-sm font-medium hover:bg-blue-700 focus:outline-none focus-visible:ring-2 focus-visible:ring-blue-500/40 disabled:opacity-50 disabled:cursor-not-allowed cursor-pointer transition-colors"
                >
                  {isThinking ? "…" : "Send"}
                </button>
              </div>
            ) : (
              <div className="flex flex-col items-center gap-3 py-2">
                <div className="flex items-center gap-4">
                  <MicButton
                    isListening={isListening}
                    isSpeaking={isSpeaking}
                    isThinking={isThinking}
                    disabled={micDisabled}
                    onPress={handleMicPress}
                  />
                  <button
                    type="button"
                    onClick={() => {
                      cancelSpeech();
                      stopListening();
                      setChatMode(true);
                    }}
                    disabled={isThinking}
                    aria-label="Type instead"
                    title="Type instead"
                    className="shrink-0 h-16 w-16 rounded-full border-2 border-zinc-300 dark:border-zinc-700 bg-white dark:bg-zinc-900 text-zinc-600 dark:text-zinc-300 hover:text-blue-600 dark:hover:text-blue-400 hover:border-blue-400 dark:hover:border-blue-600 hover:bg-blue-50/50 dark:hover:bg-blue-950/20 focus:outline-none focus-visible:ring-4 focus-visible:ring-blue-500/30 disabled:opacity-50 disabled:cursor-not-allowed cursor-pointer transition-all duration-200 ease-out flex items-center justify-center"
                  >
                    <svg
                      viewBox="0 0 24 24"
                      fill="none"
                      stroke="currentColor"
                      strokeWidth="1.75"
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      className="w-7 h-7"
                      aria-hidden
                    >
                      <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z" />
                    </svg>
                  </button>
                </div>
                <span className="text-sm font-medium text-zinc-600 dark:text-zinc-300">
                  {micStatusLabel({ isListening, isSpeaking, isThinking })}
                </span>
              </div>
            )}
          </>
        )}

        <footer className="mt-auto pt-4 flex items-center justify-center gap-2 text-[11px] text-zinc-400">
          <svg
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            strokeWidth="1.75"
            strokeLinecap="round"
            strokeLinejoin="round"
            className="w-3 h-3"
            aria-hidden
          >
            <rect x="3" y="11" width="18" height="11" rx="2" />
            <path d="M7 11V7a5 5 0 0 1 10 0v4" />
          </svg>
          <span>
            Demo · not for clinical use. Production swaps to Twilio + Deepgram +
            ElevenLabs.
          </span>
        </footer>
      </div>
    </div>
  );
}
