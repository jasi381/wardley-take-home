"use client";

import { PROVIDER_LABELS, type ProviderId } from "@/lib/schema";

const PROVIDERS: ProviderId[] = ["gemini", "cerebras"];

type Props = {
  value: ProviderId;
  onChange: (provider: ProviderId) => void;
  disabled?: boolean;
};

export function ProviderSelector({ value, onChange, disabled }: Props) {
  return (
    <label
      className="flex items-center gap-1.5 text-xs text-zinc-500 select-none"
      title="Language model"
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
        <path d="M12 2a4 4 0 0 0-4 4v1a4 4 0 0 0-2 7v1a4 4 0 0 0 6 3.5A4 4 0 0 0 18 15v-1a4 4 0 0 0-2-7V6a4 4 0 0 0-4-4Z" />
      </svg>
      <span className="sr-only">Language model</span>
      <select
        aria-label="Language model"
        value={value}
        disabled={disabled}
        onChange={(e) => onChange(e.target.value as ProviderId)}
        className="rounded-md border border-zinc-200 dark:border-zinc-700 bg-white dark:bg-zinc-900 px-2 py-1 text-xs text-zinc-700 dark:text-zinc-200 focus:outline-none focus:ring-2 focus:ring-blue-500 disabled:opacity-50 cursor-pointer hover:border-zinc-300 dark:hover:border-zinc-600 transition-colors"
      >
        {PROVIDERS.map((p) => (
          <option key={p} value={p}>
            {PROVIDER_LABELS[p]}
          </option>
        ))}
      </select>
    </label>
  );
}
