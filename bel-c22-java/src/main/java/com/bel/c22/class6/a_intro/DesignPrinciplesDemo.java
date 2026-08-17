package com.bel.c22.class6.a_intro;

import java.util.List;

/**
 * What are Design Principles?
 *
 * Principles = the compass ("what to aim for").
 * Patterns   = a specific route ("how to get there" in one situation).
 *
 * This demo doesn't implement one specific principle - it shows the same
 * requirement solved two ways, so you can see a principle (favor
 * composition, depend on abstractions) guiding the design without
 * naming a single formal pattern.
 */
public class DesignPrinciplesDemo {

    interface Notifier {
        void send(String message);
    }

    static class EmailNotifier implements Notifier {
        public void send(String message) {
            System.out.println("Email: " + message);
        }
    }

    static class SmsNotifier implements Notifier {
        public void send(String message) {
            System.out.println("SMS: " + message);
        }
    }

    // Follows the design PRINCIPLE "depend on an abstraction, not a concrete class"
    // without needing any named design PATTERN to do it.
    static class AlertService {
        private final List<Notifier> notifiers;

        AlertService(List<Notifier> notifiers) {
            this.notifiers = notifiers;
        }

        void raise(String message) {
            for (Notifier notifier : notifiers) {
                notifier.send(message);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Principle in action: code against Notifier, not EmailNotifier/SmsNotifier ===");
        AlertService alerts = new AlertService(List.of(new EmailNotifier(), new SmsNotifier()));
        alerts.raise("Server CPU above 90%");

        System.out.println("\n--- Key Takeaway ---");
        System.out.println("AlertService never mentions Email or SMS by name.");
        System.out.println("That's the PRINCIPLE (depend on abstractions) guiding the design.");
        System.out.println("Later topics (SRP, OCP, LSP, ISP, DIP) each give this idea a precise, checkable rule.");
    }
}
