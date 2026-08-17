package com.bel.c22.class6.c_srp;

/**
 * S - Single Responsibility Principle (SRP)
 *
 * "A class should have only one reason to change." - Robert C. Martin
 */
public class SrpDemo {

    // =====================================================
    // BEFORE SRP (Bad) - one class, three reasons to change
    // =====================================================
    static class OrderServiceBad {
        void placeOrder(String item) {
            System.out.println("Order placed for: " + item);
        }

        double calculateTotal(double price, int qty) {
            return price * qty; // calculation rules live here too
        }

        void sendConfirmationEmail(String item) {
            System.out.println("Email: your order for " + item + " is confirmed"); // and email rules too
        }
    }

    // =====================================================
    // AFTER SRP (Good) - each class has exactly one job
    // =====================================================
    static class OrderService {
        void placeOrder(String item) {
            System.out.println("Order placed for: " + item);
        }
    }

    static class OrderCalculator {
        double calculateTotal(double price, int qty) {
            return price * qty;
        }
    }

    static class EmailService {
        void sendConfirmationEmail(String item) {
            System.out.println("Email: your order for " + item + " is confirmed");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== BEFORE SRP (one class doing 3 jobs) ===");
        OrderServiceBad bad = new OrderServiceBad();
        bad.placeOrder("Keyboard");
        System.out.println("Total: " + bad.calculateTotal(1500, 2));
        bad.sendConfirmationEmail("Keyboard");
        System.out.println("(If email logic changes, OrderServiceBad changes too - even though ordering didn't.)");

        System.out.println("\n=== AFTER SRP (one job per class) ===");
        OrderService orderService = new OrderService();
        OrderCalculator calculator = new OrderCalculator();
        EmailService emailService = new EmailService();

        orderService.placeOrder("Mouse");
        System.out.println("Total: " + calculator.calculateTotal(500, 3));
        emailService.sendConfirmationEmail("Mouse");

        System.out.println("\n--- Key Takeaway ---");
        System.out.println("Changing how emails are sent now only touches EmailService.");
        System.out.println("OrderService and OrderCalculator are untouched - each has ONE reason to change.");
    }
}
