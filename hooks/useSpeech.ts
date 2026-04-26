"use client";

import { useCallback, useEffect, useRef, useState } from "react";
import type { CloudVoice } from "@/lib/cloudVoices";

type SpeechRecognitionEvent = Event & {
  results: ArrayLike<{
    0: { transcript: string };
    isFinal: boolean;
    length: number;
  }> & { length: number };
  resultIndex: number;
};

type SpeechRecognitionErrorEvent = Event & { error: string };

type SpeechRecognitionInstance = {
  continuous: boolean;
  interimResults: boolean;
  lang: string;
  onresult: ((e: SpeechRecognitionEvent) => void) | null;
  onerror: ((e: SpeechRecognitionErrorEvent) => void) | null;
  onend: (() => void) | null;
  onstart: (() => void) | null;
  start: () => void;
  stop: () => void;
  abort: () => void;
};

type SpeechRecognitionCtor = new () => SpeechRecognitionInstance;

type PuterSdk = {
  ai: {
    txt2speech: (
      text: string,
      options?: Record<string, unknown>,
    ) => Promise<HTMLAudioElement>;
  };
};

declare global {
  interface Window {
    webkitSpeechRecognition?: SpeechRecognitionCtor;
    SpeechRecognition?: SpeechRecognitionCtor;
    puter?: PuterSdk;
  }
}

export type UseSpeechResult = {
  /** null = capability not yet detected (initial render). true/false = confirmed. */
  supported: boolean | null;
  isListening: boolean;
  isSpeaking: boolean;
  interimTranscript: string;
  startListening: () => void;
  stopListening: () => void;
  speak: (text: string, onEnd?: () => void) => void;
  cancelSpeech: () => void;
};

export type UseSpeechOptions = {
  /** Cloud voice (Puter) to use for TTS. If undefined, falls back to browser speechSynthesis. */
  cloudVoice?: CloudVoice | null;
};

function waitForPuter(timeoutMs = 5000): Promise<PuterSdk> {
  return new Promise((resolve, reject) => {
    if (window.puter) return resolve(window.puter);
    const start = Date.now();
    const tick = () => {
      if (window.puter) return resolve(window.puter);
      if (Date.now() - start > timeoutMs) {
        return reject(new Error("Puter SDK did not load in time."));
      }
      setTimeout(tick, 100);
    };
    tick();
  });
}

export function useSpeech(
  onFinalTranscript: (text: string) => void,
  { cloudVoice }: UseSpeechOptions = {},
): UseSpeechResult {
  // null until the post-mount effect confirms — prevents an "unsupported"
  // warning flashing on first paint before we've checked.
  const [supported, setSupported] = useState<boolean | null>(null);
  const [isListening, setIsListening] = useState(false);
  const [isSpeaking, setIsSpeaking] = useState(false);
  const [interimTranscript, setInterimTranscript] = useState("");

  const recognitionRef = useRef<SpeechRecognitionInstance | null>(null);
  const finalBufferRef = useRef("");
  const onFinalRef = useRef(onFinalTranscript);
  const cloudAudioRef = useRef<HTMLAudioElement | null>(null);
  const cloudVoiceRef = useRef<CloudVoice | null | undefined>(cloudVoice);

  useEffect(() => {
    onFinalRef.current = onFinalTranscript;
  }, [onFinalTranscript]);

  useEffect(() => {
    cloudVoiceRef.current = cloudVoice;
  }, [cloudVoice]);

  useEffect(() => {
    if (typeof window === "undefined") return;
    const Ctor = window.SpeechRecognition ?? window.webkitSpeechRecognition;
    if (!Ctor || !("speechSynthesis" in window)) {
      setSupported(false);
      return;
    }
    setSupported(true);

    const r = new Ctor();
    r.continuous = false;
    r.interimResults = true;
    r.lang = "en-US";

    r.onresult = (e) => {
      let interim = "";
      let finalChunk = "";
      for (let i = e.resultIndex; i < e.results.length; i++) {
        const result = e.results[i];
        const transcript = result[0].transcript;
        if (result.isFinal) finalChunk += transcript;
        else interim += transcript;
      }
      if (finalChunk) finalBufferRef.current += finalChunk;
      setInterimTranscript(interim);
    };

    r.onerror = () => {
      setIsListening(false);
    };

    r.onend = () => {
      setIsListening(false);
      const text = finalBufferRef.current.trim();
      finalBufferRef.current = "";
      setInterimTranscript("");
      if (text) onFinalRef.current(text);
    };

    recognitionRef.current = r;

    return () => {
      try {
        r.abort();
      } catch {
        /* noop */
      }
      window.speechSynthesis.cancel();
      if (cloudAudioRef.current) {
        cloudAudioRef.current.pause();
        cloudAudioRef.current = null;
      }
    };
  }, []);

  const startListening = useCallback(() => {
    const r = recognitionRef.current;
    if (!r) return;
    finalBufferRef.current = "";
    setInterimTranscript("");
    try {
      r.start();
      setIsListening(true);
    } catch {
      // already started — ignore
    }
  }, []);

  const stopListening = useCallback(() => {
    const r = recognitionRef.current;
    if (!r) return;
    try {
      r.stop();
    } catch {
      /* noop */
    }
  }, []);

  const cancelSpeech = useCallback(() => {
    if (typeof window === "undefined") return;
    window.speechSynthesis.cancel();
    if (cloudAudioRef.current) {
      cloudAudioRef.current.pause();
      cloudAudioRef.current = null;
    }
    setIsSpeaking(false);
  }, []);

  const speakBrowser = useCallback((text: string, onEnd?: () => void) => {
    if (typeof window === "undefined" || !("speechSynthesis" in window)) {
      onEnd?.();
      return;
    }
    window.speechSynthesis.cancel();
    const u = new SpeechSynthesisUtterance(text);
    u.lang = "en-US";
    u.rate = 1.02;
    u.pitch = 1.0;
    const voices = window.speechSynthesis.getVoices();
    const preferred =
      voices.find((v) => /en-US/i.test(v.lang) && /female|samantha|google us/i.test(v.name)) ??
      voices.find((v) => /en-US/i.test(v.lang)) ??
      voices[0];
    if (preferred) u.voice = preferred;
    u.onstart = () => setIsSpeaking(true);
    u.onend = () => {
      setIsSpeaking(false);
      onEnd?.();
    };
    u.onerror = () => {
      setIsSpeaking(false);
      onEnd?.();
    };
    window.speechSynthesis.speak(u);
  }, []);

  const speakCloud = useCallback(
    async (text: string, voice: CloudVoice, onEnd?: () => void) => {
      try {
        const puter = await waitForPuter();
        // If the user changed voice or cancelled before this resolved, abort.
        if (cloudVoiceRef.current?.id !== voice.id) return;
        cancelSpeech();
        const audio = await puter.ai.txt2speech(text, voice.options);
        if (cloudVoiceRef.current?.id !== voice.id) return;
        cloudAudioRef.current = audio;
        audio.onplay = () => setIsSpeaking(true);
        audio.onended = () => {
          setIsSpeaking(false);
          if (cloudAudioRef.current === audio) cloudAudioRef.current = null;
          onEnd?.();
        };
        audio.onerror = () => {
          setIsSpeaking(false);
          if (cloudAudioRef.current === audio) cloudAudioRef.current = null;
          onEnd?.();
        };
        await audio.play();
      } catch {
        // Fall back to browser TTS so the conversation never silently stalls.
        speakBrowser(text, onEnd);
      }
    },
    [cancelSpeech, speakBrowser],
  );

  const speak = useCallback(
    (text: string, onEnd?: () => void) => {
      const voice = cloudVoiceRef.current;
      if (voice) {
        void speakCloud(text, voice, onEnd);
      } else {
        speakBrowser(text, onEnd);
      }
    },
    [speakCloud, speakBrowser],
  );

  return {
    supported,
    isListening,
    isSpeaking,
    interimTranscript,
    startListening,
    stopListening,
    speak,
    cancelSpeech,
  };
}
