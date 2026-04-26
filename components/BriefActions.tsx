"use client";

import { useState } from "react";
import type { ClinicalBrief } from "@/lib/schema";

export function BriefActions({
  brief,
  onReset,
}: {
  brief: ClinicalBrief;
  onReset: () => void;
}) {
  const [copied, setCopied] = useState(false);

  const json = JSON.stringify(brief, null, 2);

  const copy = async () => {
    try {
      await navigator.clipboard.writeText(json);
      setCopied(true);
      setTimeout(() => setCopied(false), 1500);
    } catch {
      /* clipboard blocked */
    }
  };

  const download = () => {
    const blob = new Blob([json], { type: "application/json" });
    const url = URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    const slug =
      brief.chief_complaint
        .toLowerCase()
        .replace(/[^a-z0-9]+/g, "-")
        .replace(/^-|-$/g, "")
        .slice(0, 40) || "intake";
    a.download = `brief-${slug}.json`;
    a.click();
    URL.revokeObjectURL(url);
  };

  return (
    <div className="flex flex-wrap gap-2">
      <button
        type="button"
        onClick={copy}
        className="inline-flex items-center gap-1.5 px-4 h-10 text-sm font-medium rounded-lg bg-blue-600 text-white hover:bg-blue-700 focus:outline-none focus-visible:ring-2 focus-visible:ring-blue-500/40 transition-colors cursor-pointer"
      >
        {copied ? (
          <>
            <Icon path="M5 13l4 4L19 7" />
            Copied
          </>
        ) : (
          <>
            <Icon path="M8 16H6a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v2 M14 8h4a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2h-8a2 2 0 0 1-2-2v-2" />
            Copy JSON
          </>
        )}
      </button>
      <button
        type="button"
        onClick={download}
        className="inline-flex items-center gap-1.5 px-4 h-10 text-sm font-medium rounded-lg border border-zinc-300 dark:border-zinc-700 text-zinc-700 dark:text-zinc-200 hover:bg-zinc-50 dark:hover:bg-zinc-900 focus:outline-none focus-visible:ring-2 focus-visible:ring-blue-500/40 transition-colors cursor-pointer"
      >
        <Icon path="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4 M7 10l5 5 5-5 M12 15V3" />
        Download
      </button>
      <button
        type="button"
        onClick={onReset}
        className="inline-flex items-center gap-1.5 px-4 h-10 text-sm font-medium rounded-lg border border-zinc-300 dark:border-zinc-700 text-zinc-700 dark:text-zinc-200 hover:bg-zinc-50 dark:hover:bg-zinc-900 focus:outline-none focus-visible:ring-2 focus-visible:ring-blue-500/40 transition-colors cursor-pointer ml-auto"
      >
        <Icon path="M3 12a9 9 0 1 0 3-6.7L3 8 M3 3v5h5" />
        New intake
      </button>
    </div>
  );
}

function Icon({ path }: { path: string }) {
  return (
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
      {path.split(" M").map((d, i) => (
        <path key={i} d={i === 0 ? d : `M${d}`} />
      ))}
    </svg>
  );
}
