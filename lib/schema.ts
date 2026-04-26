export type ROSSystem =
  | "constitutional"
  | "heent"
  | "cardiovascular"
  | "respiratory"
  | "gastrointestinal"
  | "genitourinary"
  | "neurological"
  | "musculoskeletal"
  | "skin"
  | "psychiatric";

export type HPI = {
  onset: string;
  location: string;
  duration: string;
  character: string;
  aggravating_factors: string[];
  alleviating_factors: string[];
  radiation: string | null;
  timing: string;
  severity: number;
  associated_symptoms: string[];
  narrative: string;
};

export type ROS = Record<ROSSystem, string[]>;

export type ClinicalBrief = {
  patient_name: string;
  patient_age: string;
  chief_complaint: string;
  hpi: HPI;
  ros: ROS;
  red_flags: string[];
  patient_concerns: string;
  intake_metadata: {
    duration_seconds: number;
    turn_count: number;
    completed_at: string;
  };
};

export type ChatMessage =
  | { role: "system"; content: string }
  | { role: "user"; content: string }
  | { role: "assistant"; content: string };

export type ProviderId = "gemini" | "cerebras";

export const PROVIDER_LABELS: Record<ProviderId, string> = {
  gemini: "Gemini 2.5 Flash",
  cerebras: "Cerebras Llama 3.1 8B",
};

export type ChatApiRequest = {
  messages: ChatMessage[];
  startedAt: string;
  provider?: ProviderId;
  patientName?: string;
  patientAge?: string;
};

export type ChatApiResponse =
  | { type: "question"; text: string }
  | { type: "brief"; brief: ClinicalBrief }
  | { type: "error"; message: string };
