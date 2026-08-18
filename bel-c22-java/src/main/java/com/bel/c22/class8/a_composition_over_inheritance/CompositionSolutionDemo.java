package com.bel.c22.class8.a_composition_over_inheritance;

import java.util.ArrayList;
import java.util.List;

/**
 * SOLUTION: Same notification system using COMPOSITION.
 *
 * Instead of inheriting behaviors, we COMPOSE them.
 * Each behavior (send channel, encryption, logging, retry) is a separate object.
 * We plug them together like LEGO blocks.
 *
 * KEY INSIGHT: "HAS-A" behaviors instead of "IS-A" type hierarchy.
 */
public class CompositionSolutionDemo {

    // --- STEP 1: Define behavior contracts (interfaces) ---

    interface MessageSender {
        void send(String message);
    }

    interface MessageEncryptor {
        String encrypt(String message);
    }

    interface MessageLogger {
        void log(String event);
    }

    // --- STEP 2: Implement each behavior ONCE ---

    static class EmailSender implements MessageSender {
        public void send(String msg) { System.out.println("  EMAIL: " + msg); }
    }

    static class SMSSender implements MessageSender {
        public void send(String msg) { System.out.println("  SMS: " + msg); }
    }

    static class PushSender implements MessageSender {
        public void send(String msg) { System.out.println("  PUSH: " + msg); }
    }

    static class AESEncryptor implements MessageEncryptor {
        public String encrypt(String msg) { return "AES[" + msg + "]"; }
    }

    static class ConsoleLogger implements MessageLogger {
        public void log(String event) { System.out.println("  LOG: " + event); }
    }

    // --- STEP 3: Compose behaviors in one class ---

    static class NotificationService {
        private final MessageSender sender;            // required
        private final MessageEncryptor encryptor;      // optional (null = no encryption)
        private final MessageLogger logger;            // optional (null = no logging)
        private final boolean retryEnabled;            // optional feature

        // Constructor with all options
        NotificationService(MessageSender sender, MessageEncryptor encryptor,
                            MessageLogger logger, boolean retryEnabled) {
            this.sender = sender;
            this.encryptor = encryptor;
            this.logger = logger;
            this.retryEnabled = retryEnabled;
        }

        // Simple constructor — just a sender
        NotificationService(MessageSender sender) {
            this(sender, null, null, false);
        }

        void sendNotification(String message) {
            if (logger != null) logger.log("Sending: " + message);

            String processed = message;
            if (encryptor != null) processed = encryptor.encrypt(message);

            sender.send(processed);

            if (retryEnabled) System.out.println("  (retry enabled - will retry on failure)");
            if (logger != null) logger.log("Sent successfully");
        }
    }

    /**
     * COMPOSITION BENEFITS:
     *
     * 1. NO class explosion — 3 senders + 1 encryptor + 1 logger = 5 classes total
     *    (vs 12-48 with inheritance)
     *
     * 2. Each behavior written ONCE — fix encryption bug in ONE place
     *
     * 3. Features can be toggled at RUNTIME — not locked at compile time
     *
     * 4. Easy to add new features — just create new interface + implementation
     *    (no existing code changes needed → Open/Closed Principle!)
     *
     * 5. Easy to test — mock any individual behavior
     */
    public static void main(String[] args) {
        System.out.println("=== COMPOSITION SOLUTION ===\n");

        // Simple email — no extras
        System.out.println("1. Simple email:");
        NotificationService simple = new NotificationService(new EmailSender());
        simple.sendNotification("Hello World");

        // Encrypted SMS with logging
        System.out.println("\n2. Encrypted SMS with logging:");
        NotificationService encryptedSms = new NotificationService(
                new SMSSender(), new AESEncryptor(), new ConsoleLogger(), false);
        encryptedSms.sendNotification("OTP: 4829");

        // Push with retry and logging (no encryption)
        System.out.println("\n3. Push with retry + logging:");
        NotificationService pushRetry = new NotificationService(
                new PushSender(), null, new ConsoleLogger(), true);
        pushRetry.sendNotification("You have a new message");

        // Runtime flexibility — switch sender dynamically!
        System.out.println("\n4. Runtime flexibility — same config, different senders:");
        List<MessageSender> allChannels = new ArrayList<>();
        allChannels.add(new EmailSender());
        allChannels.add(new SMSSender());
        allChannels.add(new PushSender());

        for (MessageSender sender : allChannels) {
            new NotificationService(sender, new AESEncryptor(), null, false)
                    .sendNotification("Broadcast alert!");
        }

        System.out.println("\nTotal classes needed: 5 (vs 12-48 with inheritance)");
        System.out.println("Adding a new feature? Just 1 new class!");
    }
}
