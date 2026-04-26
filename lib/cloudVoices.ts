// Cloud TTS voices routed through Puter.js (free, unlimited, no API key).
// https://docs.puter.com/AI/txt2speech/

export type CloudVoice = {
  id: string;
  displayName: string;
  flavour: string;
  options: Record<string, unknown>;
};

export const CLOUD_VOICES: CloudVoice[] = [
  {
    id: "polly-joanna-neural",
    displayName: "Joanna",
    flavour: "Warm US female · neural",
    options: { voice: "Joanna", engine: "neural", language: "en-US" },
  },
  {
    id: "polly-ruth-generative",
    displayName: "Ruth",
    flavour: "Most natural US female · generative",
    options: { voice: "Ruth", engine: "generative", language: "en-US" },
  },
  {
    id: "polly-matthew-neural",
    displayName: "Matthew",
    flavour: "Professional US male · neural",
    options: { voice: "Matthew", engine: "neural", language: "en-US" },
  },
];

export const DEFAULT_CLOUD_VOICE_ID = "polly-joanna-neural";

export function findCloudVoice(id: string): CloudVoice {
  return (
    CLOUD_VOICES.find((v) => v.id === id) ??
    CLOUD_VOICES.find((v) => v.id === DEFAULT_CLOUD_VOICE_ID) ??
    CLOUD_VOICES[0]
  );
}
