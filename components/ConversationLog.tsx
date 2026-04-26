"use client";

import { useEffect, useRef, type ReactNode } from "react";
import type { ChatMessage } from "@/lib/schema";

type Props = {
  messages: ChatMessage[];
  interim: string;
  prelude?: ReactNode;
  isThinking?: boolean;
  emptyHint?: string;
  patientName?: string;
};

function initialsOf(name: string): string {
  const parts = name.trim().split(/\s+/).filter(Boolean);
  if (parts.length === 0) return "";
  if (parts.length === 1) return parts[0][0]!.toUpperCase();
  return (parts[0][0]! + parts[parts.length - 1]![0]!).toUpperCase();
}

export function ConversationLog({
  messages,
  interim,
  prelude,
  isThinking,
  emptyHint,
  patientName,
}: Props) {
  const initials = patientName ? initialsOf(patientName) : "";
  const ref = useRef<HTMLDivElement>(null);
  const visible = messages.filter((m) => m.role !== "system");

  useEffect(() => {
    ref.current?.scrollTo({ top: ref.current.scrollHeight, behavior: "smooth" });
  }, [visible.length, interim, isThinking]);

  const showHint = !prelude && visible.length === 0 && !interim;

  return (
    <div
      ref={ref}
      className="flex-1 min-h-0 w-full overflow-y-auto overscroll-contain rounded-2xl border border-zinc-200 dark:border-zinc-800 bg-white dark:bg-zinc-950 px-4 py-5 space-y-4 shadow-sm"
    >
      {prelude}
      {showHint && (
        <p className="text-sm text-zinc-400 italic">
          {emptyHint ??
            "Tap the mic and start the intake. The agent will greet you and ask what brings you in."}
        </p>
      )}
      {visible.map((m, i) => (
        <Bubble key={i} role={m.role} text={m.content} initials={initials} />
      ))}
      {interim && (
        <Bubble role="user" text={interim} pending initials={initials} />
      )}
      {isThinking && <ThinkingBubble />}
    </div>
  );
}

export function ChatBubble({
  role,
  text,
  initials,
}: {
  role: "user" | "assistant";
  text: string;
  initials?: string;
}) {
  return <Bubble role={role} text={text} initials={initials} />;
}

function Bubble({
  role,
  text,
  pending,
  initials,
}: {
  role: "user" | "assistant" | "system";
  text: string;
  pending?: boolean;
  initials?: string;
}) {
  const isUser = role === "user";

  if (isUser) {
    return (
      <div className="flex items-end gap-2 justify-end">
        <div
          className={`max-w-[80%] rounded-2xl rounded-br-md px-4 py-2.5 text-sm leading-relaxed whitespace-pre-wrap bg-blue-600 text-white shadow-sm ${
            pending ? "opacity-60 italic" : ""
          }`}
        >
          {text}
        </div>
        <PatientAvatar initials={initials} />
      </div>
    );
  }

  return (
    <div className="flex items-end gap-2 justify-start">
      <NurseAvatar />
      <div className="max-w-[80%] rounded-2xl rounded-bl-md px-4 py-2.5 text-sm leading-relaxed whitespace-pre-wrap bg-zinc-100 dark:bg-zinc-800 text-zinc-900 dark:text-zinc-100">
        {text}
      </div>
    </div>
  );
}

function ThinkingBubble() {
  return (
    <div
      className="flex items-end gap-2 justify-start"
      aria-live="polite"
      aria-label="Nurse is thinking"
    >
      <NurseAvatar />
      <div className="rounded-2xl rounded-bl-md px-4 py-3 bg-zinc-100 dark:bg-zinc-800 flex items-center gap-1.5">
        <span className="wardly-typing-dot block w-1.5 h-1.5 rounded-full bg-zinc-500" />
        <span className="wardly-typing-dot block w-1.5 h-1.5 rounded-full bg-zinc-500" />
        <span className="wardly-typing-dot block w-1.5 h-1.5 rounded-full bg-zinc-500" />
      </div>
    </div>
  );
}

function NurseAvatar() {
  return (
    <div
      aria-hidden
      className="shrink-0 w-7 h-7 rounded-full bg-blue-50 dark:bg-blue-950/40 border border-blue-100 dark:border-blue-900 flex items-center justify-center text-blue-600 dark:text-blue-400"
    >
      <svg
        viewBox="0 0 24 24"
        fill="none"
        stroke="currentColor"
        strokeWidth="1.75"
        strokeLinecap="round"
        strokeLinejoin="round"
        className="w-3.5 h-3.5"
      >
        <path d="M6 3v6a6 6 0 0 0 12 0V3" />
        <path d="M6 3h2" />
        <path d="M16 3h2" />
        <path d="M12 15v3a3 3 0 0 0 6 0v-1" />
        <circle cx="18" cy="17" r="2" />
      </svg>
    </div>
  );
}

function PatientAvatar({ initials }: { initials?: string }) {
  return (
    <div
      aria-hidden
      className="shrink-0 w-7 h-7 rounded-full bg-zinc-100 dark:bg-zinc-800 border border-zinc-200 dark:border-zinc-700 flex items-center justify-center text-[10px] font-semibold text-zinc-600 dark:text-zinc-300 tracking-wide"
    >
      {initials || (
        <svg
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          strokeWidth="1.75"
          strokeLinecap="round"
          strokeLinejoin="round"
          className="w-3.5 h-3.5"
        >
          <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2" />
          <circle cx="12" cy="7" r="4" />
        </svg>
      )}
    </div>
  );
}
