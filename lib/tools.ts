const ROS_SYSTEM_KEYS = [
  "constitutional",
  "heent",
  "cardiovascular",
  "respiratory",
  "gastrointestinal",
  "genitourinary",
  "neurological",
  "musculoskeletal",
  "skin",
  "psychiatric",
] as const;

const stringArray = { type: "array", items: { type: "string" } } as const;

const rosProperties = Object.fromEntries(
  ROS_SYSTEM_KEYS.map((k) => [k, stringArray]),
);

export const askFollowupTool = {
  type: "function" as const,
  function: {
    name: "ask_followup",
    description:
      "Speak the next intake question to the patient. Use this every turn until the intake is sufficiently complete to finalize the brief.",
    parameters: {
      type: "object",
      properties: {
        text: {
          type: "string",
          description:
            "The exact words to speak to the patient. Conversational, one or two sentences max. No clinical jargon.",
        },
      },
      required: ["text"],
      additionalProperties: false,
    },
  },
};

export const finalizeBriefTool = {
  type: "function" as const,
  function: {
    name: "finalize_brief",
    description:
      "Call this only when CC, all OLDCARTS fields for HPI, relevant ROS, and red-flag screening for the chief complaint are complete. Ends the intake.",
    parameters: {
      type: "object",
      properties: {
        chief_complaint: {
          type: "string",
          description: "One sentence in the patient's own words.",
        },
        hpi: {
          type: "object",
          properties: {
            onset: { type: "string" },
            location: { type: "string" },
            duration: { type: "string" },
            character: { type: "string" },
            aggravating_factors: stringArray,
            alleviating_factors: stringArray,
            radiation: { type: ["string", "null"] },
            timing: { type: "string" },
            severity: {
              type: "number",
              minimum: 0,
              maximum: 10,
              description: "0–10 pain/severity scale; 0 if not applicable.",
            },
            associated_symptoms: stringArray,
            narrative: {
              type: "string",
              description:
                "2–3 sentence prose summary of the HPI for a clinician scanning quickly.",
            },
          },
          required: [
            "onset",
            "location",
            "duration",
            "character",
            "aggravating_factors",
            "alleviating_factors",
            "radiation",
            "timing",
            "severity",
            "associated_symptoms",
            "narrative",
          ],
          additionalProperties: false,
        },
        ros: {
          type: "object",
          description:
            "Per-system findings. Each value is an array of patient-reported positives or pertinent negatives. Empty array = not assessed or no findings.",
          properties: rosProperties,
          required: [...ROS_SYSTEM_KEYS],
          additionalProperties: false,
        },
        red_flags: {
          ...stringArray,
          description:
            "Urgent findings that warrant escalation (e.g. 'chest pain radiating to left arm with diaphoresis').",
        },
        patient_concerns: {
          type: "string",
          description:
            "Anything else the patient wanted the doctor to know, in their own words.",
        },
      },
      required: [
        "chief_complaint",
        "hpi",
        "ros",
        "red_flags",
        "patient_concerns",
      ],
      additionalProperties: false,
    },
  },
};

export const intakeTools = [askFollowupTool, finalizeBriefTool];
