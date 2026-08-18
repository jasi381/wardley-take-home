package com.bel.c22.class8.a_composition_over_inheritance;

/**
 * PROBLEM: When Inheritance Goes Wrong — The Notification System
 *
 * Imagine you're building a notification service for a production system.
 * You start with simple types: Email, SMS, Push.
 * Then requirements grow: some need encryption, some need logging, some need both.
 *
 * With inheritance, you get CLASS EXPLOSION — the number of classes grows
 * exponentially with every new feature.
 */
public class InheritanceProblemDemo {

    // --- BASE CLASS ---
    static abstract class Notification {
        String message;
        Notification(String message) { this.message = message; }
        abstract void send();
    }

    // --- Level 1: Channel types (3 classes) ---
    static class EmailNotification extends Notification {
        EmailNotification(String msg) { super(msg); }
        void send() { System.out.println("EMAIL: " + message); }
    }

    static class SMSNotification extends Notification {
        SMSNotification(String msg) { super(msg); }
        void send() { System.out.println("SMS: " + message); }
    }

    static class PushNotification extends Notification {
        PushNotification(String msg) { super(msg); }
        void send() { System.out.println("PUSH: " + message); }
    }

    // --- Level 2: Need encryption? Now 3 MORE classes ---
    static class EncryptedEmailNotification extends EmailNotification {
        EncryptedEmailNotification(String msg) { super(msg); }
        void send() {
            message = "ENCRYPTED[" + message + "]";  // encrypt first
            super.send();
        }
    }

    static class EncryptedSMSNotification extends SMSNotification {
        EncryptedSMSNotification(String msg) { super(msg); }
        void send() {
            message = "ENCRYPTED[" + message + "]";
            super.send();
        }
    }
    // EncryptedPushNotification... (you get the idea)

    // --- Level 3: Need logging too? 3 MORE classes PER combination ---
    static class LoggedEncryptedEmailNotification extends EncryptedEmailNotification {
        LoggedEncryptedEmailNotification(String msg) { super(msg); }
        void send() {
            System.out.println("LOG: Sending notification at " + System.currentTimeMillis());
            super.send();
        }
    }
    // LoggedEncryptedSMSNotification...
    // LoggedEncryptedPushNotification...
    // LoggedEmailNotification (without encryption)...
    // LoggedSMSNotification...
    // LoggedPushNotification...

    /**
     * PROBLEM SUMMARY:
     *
     * 3 channels × 2 encryption options × 2 logging options = 12 classes!
     * Add "retry" feature? → 24 classes!
     * Add "rate limiting"? → 48 classes!
     *
     * This is called CLASS EXPLOSION or COMBINATORIAL EXPLOSION.
     * Each new cross-cutting feature DOUBLES the class count.
     *
     * In production, this leads to:
     * - Massive code duplication (encryption logic copied in every Encrypted* class)
     * - Hard to maintain — fix a bug in encryption? Fix it in ALL Encrypted* classes
     * - Impossible to add features at runtime (can't switch encryption on/off)
     */
    public static void main(String[] args) {
        System.out.println("=== INHERITANCE PROBLEM: CLASS EXPLOSION ===\n");

        Notification simple = new EmailNotification("Hello");
        simple.send();

        Notification encrypted = new EncryptedEmailNotification("Secret");
        encrypted.send();

        Notification loggedEncrypted = new LoggedEncryptedEmailNotification("Top Secret");
        loggedEncrypted.send();

        System.out.println("\nImagine adding 2 more features...");
        System.out.println("3 channels × 2 encryption × 2 logging = 12 classes already!");
        System.out.println("With retry + rate-limit: 3 × 2 × 2 × 2 × 2 = 48 classes!");
        System.out.println("\nThis is why composition is better here. See CompositionSolutionDemo.");
    }
}
