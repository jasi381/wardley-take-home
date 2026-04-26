"use client";

type Props = {
  isListening: boolean;
  isSpeaking: boolean;
  isThinking: boolean;
  disabled: boolean;
  onPress: () => void;
};

export function micStatusLabel({
  isListening,
  isSpeaking,
  isThinking,
}: {
  isListening: boolean;
  isSpeaking: boolean;
  isThinking: boolean;
}): string {
  if (isListening) return "Listening — tap to stop";
  if (isSpeaking) return "Nurse is speaking";
  if (isThinking) return "Thinking…";
  return "Tap to speak";
}

export function MicButton({
  isListening,
  isSpeaking,
  isThinking,
  disabled,
  onPress,
}: Props) {
  const label = micStatusLabel({ isListening, isSpeaking, isThinking });

  const ring = isListening
    ? "bg-red-500 shadow-[0_0_0_10px_rgba(239,68,68,0.16)] animate-pulse"
    : isSpeaking
      ? "bg-emerald-500 shadow-[0_0_0_8px_rgba(16,185,129,0.16)]"
      : isThinking
        ? "bg-zinc-400"
        : "bg-blue-600 hover:bg-blue-700 shadow-[0_0_0_8px_rgba(37,99,235,0.10)] hover:shadow-[0_0_0_10px_rgba(37,99,235,0.14)]";

  return (
    <button
      type="button"
      onClick={onPress}
      disabled={disabled}
      aria-label={label}
      aria-pressed={isListening}
      className={`relative h-16 w-16 rounded-full text-white transition-all duration-200 ease-out disabled:opacity-50 disabled:cursor-not-allowed focus:outline-none focus-visible:ring-4 focus-visible:ring-blue-500/40 ${ring}`}
    >
      <svg
        viewBox="0 0 24 24"
        fill="none"
        stroke="currentColor"
        strokeWidth="1.75"
        strokeLinecap="round"
        strokeLinejoin="round"
        className="mx-auto h-7 w-7"
        aria-hidden
      >
        <rect x="9" y="2" width="6" height="12" rx="3" />
        <path d="M5 10v2a7 7 0 0 0 14 0v-2" />
        <line x1="12" y1="19" x2="12" y2="22" />
        <line x1="8" y1="22" x2="16" y2="22" />
      </svg>
    </button>
  );
}
