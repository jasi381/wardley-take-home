"use client";

import { CLOUD_VOICES, type CloudVoice } from "@/lib/cloudVoices";

type Props = {
  value: CloudVoice | null;
  onChange: (voice: CloudVoice | null) => void;
  disabled?: boolean;
};

export function VoiceSelector({ value, onChange, disabled }: Props) {
  return (
    <label
      className="flex items-center gap-1.5 text-xs text-zinc-500 select-none"
      title="Voice"
    >
      <svg
        viewBox="0 0 24 24"
        fill="none"
        stroke="currentColor"
        strokeWidth="1.75"
        strokeLinecap="round"
        strokeLinejoin="round"
        aria-hidden
        className="w-3.5 h-3.5 text-zinc-400"
      >
        <path d="M3 10v4" />
        <path d="M7 7v10" />
        <path d="M11 4v16" />
        <path d="M15 8v8" />
        <path d="M19 11v2" />
      </svg>
      <span className="sr-only">Voice</span>
      <select
        aria-label="Voice"
        value={value?.id ?? "browser"}
        disabled={disabled}
        onChange={(e) => {
          const v = e.target.value;
          if (v === "browser") onChange(null);
          else {
            const found = CLOUD_VOICES.find((c) => c.id === v) ?? null;
            onChange(found);
          }
        }}
        className="rounded-md border border-zinc-200 dark:border-zinc-700 bg-white dark:bg-zinc-900 px-2 py-1 text-xs text-zinc-700 dark:text-zinc-200 focus:outline-none focus:ring-2 focus:ring-blue-500 disabled:opacity-50 cursor-pointer hover:border-zinc-300 dark:hover:border-zinc-600 transition-colors"
      >
        {CLOUD_VOICES.map((v) => (
          <option key={v.id} value={v.id}>
            {v.displayName}
          </option>
        ))}
        <option value="browser">Browser default</option>
      </select>
    </label>
  );
}
