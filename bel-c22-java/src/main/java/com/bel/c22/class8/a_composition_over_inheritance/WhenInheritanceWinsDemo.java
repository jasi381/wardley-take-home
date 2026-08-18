package com.bel.c22.class8.a_composition_over_inheritance;

/**
 * WHEN INHERITANCE IS THE RIGHT CHOICE
 *
 * Composition isn't ALWAYS better. Inheritance wins when:
 *
 * 1. There is a TRUE "is-a" relationship (a Dog IS an Animal)
 * 2. You want to share common behavior across a type hierarchy
 * 3. The hierarchy is SHALLOW (2-3 levels max) and STABLE (won't keep growing)
 * 4. You need polymorphism based on TYPE, not behavior combination
 *
 * RULE OF THUMB:
 * - "Is-a" relationship → consider inheritance
 * - "Has-a" or "can-do" relationship → use composition
 * - Cross-cutting features (logging, encryption, caching) → always composition
 */
public class WhenInheritanceWinsDemo {

    // --- EXAMPLE 1: Shape hierarchy — classic "is-a" ---
    // A Circle IS-A Shape. This relationship is fundamental and won't change.

    static abstract class Shape {
        String color;

        Shape(String color) { this.color = color; }

        // Shared behavior — all shapes have area and perimeter
        abstract double area();
        abstract double perimeter();

        // Common method — same for all shapes
        void describe() {
            System.out.printf("  %s %s: area=%.2f, perimeter=%.2f%n",
                    color, getClass().getSimpleName(), area(), perimeter());
        }
    }

    static class Circle extends Shape {
        double radius;
        Circle(String color, double radius) { super(color); this.radius = radius; }
        double area() { return Math.PI * radius * radius; }
        double perimeter() { return 2 * Math.PI * radius; }
    }

    static class Rectangle extends Shape {
        double width, height;
        Rectangle(String color, double w, double h) { super(color); width = w; height = h; }
        double area() { return width * height; }
        double perimeter() { return 2 * (width + height); }
    }

    // Why inheritance works here:
    // - True is-a: a Circle IS a Shape
    // - Stable hierarchy: geometric shapes don't randomly gain new categories
    // - Shared contract: all shapes must have area() and perimeter()
    // - Polymorphism: we can treat all shapes uniformly

    // --- EXAMPLE 2: Payment processing — shared template with specialization ---

    static abstract class Payment {
        double amount;
        String currency;

        Payment(double amount, String currency) {
            this.amount = amount;
            this.currency = currency;
        }

        // Template Method pattern — shared flow, customized steps
        final void processPayment() {
            validate();
            System.out.printf("  Processing %s %.2f via %s%n",
                    currency, amount, getClass().getSimpleName());
            executePayment();
            sendReceipt();
        }

        private void validate() {
            if (amount <= 0) throw new IllegalArgumentException("Invalid amount");
        }

        abstract void executePayment();  // each type implements differently

        private void sendReceipt() {
            System.out.println("  Receipt sent.");
        }
    }

    static class CreditCardPayment extends Payment {
        String cardNumber;
        CreditCardPayment(double amount, String card) {
            super(amount, "INR");
            this.cardNumber = card;
        }
        void executePayment() {
            System.out.println("  Charging card: ****" + cardNumber.substring(cardNumber.length() - 4));
        }
    }

    static class UPIPayment extends Payment {
        String upiId;
        UPIPayment(double amount, String upiId) {
            super(amount, "INR");
            this.upiId = upiId;
        }
        void executePayment() {
            System.out.println("  UPI request sent to: " + upiId);
        }
    }

    // --- SUMMARY: When to use WHAT ---

    public static void main(String[] args) {
        System.out.println("=== WHEN INHERITANCE IS THE RIGHT CHOICE ===\n");

        // Example 1: Shape hierarchy
        System.out.println("1. Shape hierarchy (true is-a):");
        Shape[] shapes = { new Circle("Red", 5), new Rectangle("Blue", 4, 6) };
        for (Shape s : shapes) s.describe();

        // Example 2: Payment processing
        System.out.println("\n2. Payment processing (shared template):");
        Payment card = new CreditCardPayment(999.99, "4111111111111234");
        card.processPayment();
        Payment upi = new UPIPayment(499.50, "user@paytm");
        upi.processPayment();

        System.out.println("\n=== DECISION GUIDE ===");
        System.out.println("USE INHERITANCE when:");
        System.out.println("  - True 'is-a' relationship (Circle is-a Shape)");
        System.out.println("  - Hierarchy is shallow and stable");
        System.out.println("  - You need shared template with specialization");
        System.out.println("  - Polymorphism is based on TYPE\n");

        System.out.println("USE COMPOSITION when:");
        System.out.println("  - 'Has-a' or 'can-do' relationship");
        System.out.println("  - Features can be mixed and matched");
        System.out.println("  - Behavior needs to change at runtime");
        System.out.println("  - You see class explosion happening");
        System.out.println("  - Cross-cutting concerns (logging, caching, security)");
    }
}
