package com.bel.c22.class9.c_factory;

/**
 *new Email("", true, 123, 4th params) -- login / payment / order
 * class Email {
 *     -
 *     -
 *     -
 *     Email(_, _, _, _) {
 *
 *     }
 *
 *     send(body, to, cc, bcc) {
 *
 *     }
 * }
 * FACTORY METHOD PATTERN
 *
 * Intent: Define an interface for creating an object, but let subclasses decide which
 *         class to instantiate. Defer object creation to subclasses.
 *
 * Problem it solves — "new everywhere" anti-pattern:
 *   Callers that do `new EmailNotifier()` or `new SMSNotifier()` are TIGHTLY COUPLED.
 *   Swap or add a type → touch every call site.
 *
 * When to use:
 *   - The exact type to create isn't known until runtime
 *   - You want to decouple creation logic from usage
 *   - Adding new products should NOT change existing code (Open/Closed Principle)
 *
 * Two common implementations:
 *   A) Static Factory Method — a single class with a static create() switch
 *   B) Factory Method (GoF)  — abstract creator + concrete subclasses override create()
 */
public class FactoryDemo {

    // ── PRODUCT interface ────────────────────────────────────────────────────
    interface Notifier {
        void send(String message);
        String getType();
    }

    // ── CONCRETE PRODUCTS ────────────────────────────────────────────────────
    static class EmailNotifier implements Notifier {
        /*
        5 variables
         */
        public void send(String msg)  { System.out.println("  [EMAIL]  → " + msg); }
        public String getType()       { return "Email"; }
    }

    static class SMSNotifier implements Notifier {
        public void send(String msg)  { System.out.println("  [SMS]    → " + msg); }
        public String getType()       { return "SMS"; }
    }

    static class PushNotifier implements Notifier {
        public void send(String msg)  { System.out.println("  [PUSH]   → " + msg); }
        public String getType()       { return "Push"; }
    }

    // ── VARIANT A: Static Factory Method ────────────────────────────────────
    // One class, one static method, string/enum decides the type.
    // Simple. Good when the list of types is finite and stable.
    static class NotifierFactory {
        public static Notifier create(String channel) {
            return switch (channel.toLowerCase()) {
                case "email" -> new EmailNotifier();
                case "sms"   -> new SMSNotifier();
                case "push"  -> new PushNotifier();
                default      -> throw new IllegalArgumentException("Unknown channel: " + channel);
            };
        }
    }

    // ── VARIANT B: Factory Method ─────────────────────────────────────
    // Abstract creator declares the factory method; each subclass overrides it.
    // Better when creation logic is complex or varies per subclass.

    // Abstract Creator
    static abstract class NotificationService {
        // Factory Method — subclass decides which product to make
        protected abstract Notifier createNotifier();

        // Template: uses the factory method without knowing the concrete type
        public void notifyUser(String event) {
            Notifier n = createNotifier();          // polymorphic creation
            System.out.println("  [" + n.getType() + " Service] Sending event: " + event);
            n.send(event);
        }
    }

    // Concrete Creators
    static class EmailNotificationService extends NotificationService {

        protected Notifier createNotifier() { return new EmailNotifier(); }
    }

    static class SMSNotificationService extends NotificationService {
        protected Notifier createNotifier() { return new SMSNotifier(); }
    }

    /*
    class XYZ {

        XYZ create() {
            return new XYZ();
        }
    }

    PXYZ obj1 = new PXYZ();
    obj1.create();
     */

    public static void main(String[] args) {
        System.out.println("=== FACTORY METHOD PATTERN ===\n");

        // ── Variant A: Static Factory ────────────────────────────────────────
        System.out.println("A. Static Factory Method:");
        String[] channels = {"email", "sms", "push"};
        for (String ch : channels) {
            Notifier n = NotifierFactory.create(ch);   // caller never writes `new`
            n.send("Welcome to the platform!");
        }

        // KEY: caller code didn't change when we added PushNotifier — OCP ✓

        // ── Variant B: Factory Method ───────────────────────────────────
        System.out.println("\nB. GoF Factory Method:");
        NotificationService[] services = {
                new EmailNotificationService(),
                new SMSNotificationService()
        };
        for (NotificationService svc : services) {
            svc.notifyUser("Password reset requested");
        }

        System.out.println("\n── Key Takeaways ──");
        System.out.println("  • Callers depend on Notifier (interface), not EmailNotifier (class)");
        System.out.println("  • Add SlackNotifier → one new class, zero changes to callers");
        System.out.println("  • Static factory: simple; GoF factory: flexible per subclass");
    }
}
