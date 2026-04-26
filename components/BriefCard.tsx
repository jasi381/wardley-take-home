"use client";

import type { ClinicalBrief, ROSSystem } from "@/lib/schema";

const ROS_LABELS: Record<ROSSystem, string> = {
  constitutional: "Constitutional",
  heent: "HEENT",
  cardiovascular: "Cardiovascular",
  respiratory: "Respiratory",
  gastrointestinal: "Gastrointestinal",
  genitourinary: "Genitourinary",
  neurological: "Neurological",
  musculoskeletal: "Musculoskeletal",
  skin: "Skin",
  psychiatric: "Psychiatric",
};

export function BriefCard({ brief }: { brief: ClinicalBrief }) {
  const rosEntries = (Object.entries(brief.ros) as [ROSSystem, string[]][])
    .filter(([, v]) => v.length > 0);

  const completedAt = new Date(brief.intake_metadata.completed_at);
  const completedDate = completedAt.toLocaleDateString(undefined, {
    month: "short",
    day: "numeric",
    year: "numeric",
  });
  const completedTime = completedAt.toLocaleTimeString(undefined, {
    hour: "numeric",
    minute: "2-digit",
  });

  const minutes = Math.floor(brief.intake_metadata.duration_seconds / 60);
  const seconds = brief.intake_metadata.duration_seconds % 60;
  const durationLabel =
    minutes > 0 ? `${minutes}m ${seconds}s` : `${seconds}s`;

  return (
    <article className="w-full rounded-2xl border border-zinc-200 dark:border-zinc-800 bg-white dark:bg-zinc-950 overflow-hidden shadow-sm">
      <header className="px-6 py-4 border-b border-zinc-200 dark:border-zinc-800 bg-gradient-to-b from-blue-50/40 to-transparent dark:from-blue-950/20">
        <div className="flex items-start justify-between gap-4 flex-wrap">
          <div className="min-w-0">
            <p className="flex items-center gap-1.5 text-[11px] font-semibold uppercase tracking-wider text-blue-600 dark:text-blue-400">
              <span
                aria-hidden
                className="inline-block w-1.5 h-1.5 rounded-full bg-blue-600"
              />
              Pre-visit Clinical Brief
            </p>
            {brief.patient_name && (
              <h2 className="mt-1.5 text-xl font-semibold text-zinc-900 dark:text-zinc-50 leading-tight">
                {brief.patient_name}
                {brief.patient_age && (
                  <span className="ml-2 font-normal text-sm text-zinc-500">
                    age {brief.patient_age}
                  </span>
                )}
              </h2>
            )}
          </div>
          <dl className="text-right text-xs text-zinc-500 space-y-0.5 shrink-0">
            <div>
              <dt className="sr-only">Date</dt>
              <dd className="text-zinc-700 dark:text-zinc-300 font-medium">
                {completedDate}
              </dd>
            </div>
            <div>
              <dt className="sr-only">Time</dt>
              <dd>{completedTime}</dd>
            </div>
            <div>
              <dt className="sr-only">Duration and turns</dt>
              <dd>
                {durationLabel} · {brief.intake_metadata.turn_count} turns
              </dd>
            </div>
          </dl>
        </div>
      </header>

      {brief.red_flags.length > 0 && (
        <div className="px-6 py-3 bg-red-50 dark:bg-red-950/40 border-b border-red-200 dark:border-red-900 flex gap-3">
          <svg
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            strokeWidth="1.75"
            strokeLinecap="round"
            strokeLinejoin="round"
            className="w-4 h-4 mt-0.5 text-red-600 dark:text-red-400 shrink-0"
            aria-hidden
          >
            <path d="M10.29 3.86 1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0Z" />
            <line x1="12" y1="9" x2="12" y2="13" />
            <line x1="12" y1="17" x2="12.01" y2="17" />
          </svg>
          <div className="min-w-0">
            <p className="text-xs font-semibold uppercase tracking-wider text-red-700 dark:text-red-300">
              Red flags — review before visit
            </p>
            <ul className="mt-1 text-sm text-red-900 dark:text-red-100 list-disc list-inside space-y-0.5 marker:text-red-400">
              {brief.red_flags.map((r, i) => (
                <li key={i}>{r}</li>
              ))}
            </ul>
          </div>
        </div>
      )}

      <Section title="Chief Complaint">
        <p className="text-sm text-zinc-900 dark:text-zinc-100 leading-relaxed">
          {brief.chief_complaint || (
            <em className="text-zinc-400">not captured</em>
          )}
        </p>
      </Section>

      <Section title="History of Present Illness">
        {brief.hpi.narrative && (
          <p className="text-sm text-zinc-700 dark:text-zinc-200 mb-4 leading-relaxed">
            {brief.hpi.narrative}
          </p>
        )}
        <dl className="grid grid-cols-1 sm:grid-cols-2 gap-x-8 gap-y-2 text-sm">
          <Field label="Onset" value={brief.hpi.onset} />
          <Field label="Location" value={brief.hpi.location} />
          <Field label="Duration" value={brief.hpi.duration} />
          <Field label="Character" value={brief.hpi.character} />
          <Field label="Timing" value={brief.hpi.timing} />
          <Field label="Severity" value={`${brief.hpi.severity}/10`} />
          <Field label="Radiation" value={brief.hpi.radiation ?? "none"} />
          <Field
            label="Aggravating"
            value={brief.hpi.aggravating_factors.join(", ") || "none"}
          />
          <Field
            label="Alleviating"
            value={brief.hpi.alleviating_factors.join(", ") || "none"}
          />
          <Field
            label="Associated"
            value={brief.hpi.associated_symptoms.join(", ") || "none"}
          />
        </dl>
      </Section>

      <Section title="Review of Systems">
        {rosEntries.length === 0 ? (
          <p className="text-sm text-zinc-400 italic">No systems assessed.</p>
        ) : (
          <dl className="space-y-2 text-sm">
            {rosEntries.map(([sys, items]) => (
              <div
                key={sys}
                className="grid grid-cols-[140px_1fr] gap-3 items-baseline"
              >
                <dt className="text-zinc-500 text-xs uppercase tracking-wider">
                  {ROS_LABELS[sys]}
                </dt>
                <dd className="text-zinc-900 dark:text-zinc-100 leading-relaxed">
                  {items.join(", ")}
                </dd>
              </div>
            ))}
          </dl>
        )}
      </Section>

      {brief.patient_concerns && (
        <Section title="Patient Concerns (verbatim)">
          <p className="text-sm italic text-zinc-700 dark:text-zinc-300 leading-relaxed border-l-2 border-zinc-300 dark:border-zinc-700 pl-3">
            &ldquo;{brief.patient_concerns}&rdquo;
          </p>
        </Section>
      )}
    </article>
  );
}

function Section({ title, children }: { title: string; children: React.ReactNode }) {
  return (
    <section className="px-6 py-4 border-b border-zinc-100 dark:border-zinc-900 last:border-b-0">
      <h3 className="text-[11px] font-semibold uppercase tracking-wider text-zinc-500 mb-2.5">
        {title}
      </h3>
      {children}
    </section>
  );
}

function Field({ label, value }: { label: string; value: string }) {
  return (
    <div className="flex gap-2 items-baseline">
      <dt className="text-zinc-500 shrink-0 w-24 text-xs uppercase tracking-wider">
        {label}
      </dt>
      <dd className="text-zinc-900 dark:text-zinc-100 min-w-0">
        {value || <em className="text-zinc-400">—</em>}
      </dd>
    </div>
  );
}
