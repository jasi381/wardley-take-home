package com.bel.c22.class6.a_intro;

/**
 * DESIGN PRINCIPLES - Introduction
 *
 * Design principles are fundamental guidelines that help developers
 * write code that is Maintainable, Scalable, Reusable, and Robust.
 *
 * This demo shows WHY we need design principles using a real-world
 * example of a poorly designed class vs a well-designed one.
 */
public class DesignPrinciplesIntro {

    // =====================================================
    // BAD DESIGN - One class doing EVERYTHING
    // =====================================================
    static class OnlineStore {
        // This class handles products, payments, emails, and reports
        // If ANY one thing changes, we risk breaking everything!

        public void addProduct(String name, double price) {
            System.out.println("Adding product: " + name + " at Rs." + price);
        }

        public void processPayment(String customerName, double amount) {
            // What if we want to switch from UPI to Credit Card?
            // We'd have to modify this class!
            System.out.println("Processing UPI payment of Rs." + amount + " for " + customerName);
        }

        public void sendEmail(String to, String subject) {
            // What if we want to switch from Gmail to SendGrid?
            // Again, modifying this same class!
            System.out.println("Sending email to " + to + ": " + subject);
        }

        public void generateReport() {
            // What if we want PDF instead of CSV?
            // Yet again, touching this massive class!
            System.out.println("Generating CSV sales report...");
        }
    }

    // =====================================================
    // GOOD DESIGN - Each class has ONE clear purpose
    // =====================================================
    static class ProductService {
        public void addProduct(String name, double price) {
            System.out.println("Adding product: " + name + " at Rs." + price);
        }
    }

    static class PaymentService {
        public void processPayment(String customerName, double amount) {
            System.out.println("Processing payment of Rs." + amount + " for " + customerName);
        }
    }

    static class EmailService {
        public void sendEmail(String to, String subject) {
            System.out.println("Sending email to " + to + ": " + subject);
        }
    }

    static class ReportService {
        public void generateReport() {
            System.out.println("Generating sales report...");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== BAD: One class doing everything ===");
        OnlineStore store = new OnlineStore();
        store.addProduct("Laptop", 50000);
        store.processPayment("Rahul", 50000);
        store.sendEmail("rahul@email.com", "Order Confirmed!");
        store.generateReport();

        System.out.println("\n=== GOOD: Separate classes, separate responsibilities ===");
        ProductService productService = new ProductService();
        PaymentService paymentService = new PaymentService();
        EmailService emailService = new EmailService();
        ReportService reportService = new ReportService();

        productService.addProduct("Laptop", 50000);
        paymentService.processPayment("Rahul", 50000);
        emailService.sendEmail("rahul@email.com", "Order Confirmed!");
        reportService.generateReport();

        System.out.println("\n--- Key Takeaway ---");
        System.out.println("Design Principles = GUIDELINES for writing clean, flexible code");
        System.out.println("Design Patterns = SPECIFIC SOLUTIONS to recurring problems");
    }
}
