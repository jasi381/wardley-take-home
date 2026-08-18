package com.bel.c22.b_strategy;

/**
 * STRATEGY PATTERN
 *
 * Intent: Define a family of algorithms, encapsulate each one, and make them
 *         interchangeable. Strategy lets the algorithm vary independently from
 *         the clients that use it.
 *
 * Problem it solves — "Giant if-else / switch" anti-pattern:
 *   if (type == "credit") { ... } else if (type == "paypal") { ... } else { ... }
 *   Adding a new type → modify the class. Violates Open/Closed Principle.
 *
 * When to use:
 *   - Multiple algorithms for the same task (sorting, payment, compression)
 *   - You want to swap behavior at runtime without changing the context class
 *   - You want to eliminate conditional logic for selecting behavior
 *
 * Structure:
 *   1. Strategy (interface) — declares the algorithm method
 *   2. Concrete Strategies  — implement specific algorithms
 *   3. Context              — holds a Strategy reference, delegates work to it
 */
public class StrategyDemo {

    // ── STRATEGY interface ──────────────────────────────────────────────────
    interface PaymentStrategy {
        void pay(double amount);
    }

    // ── CONCRETE STRATEGIES ─────────────────────────────────────────────────
    static class CreditCardPayment implements PaymentStrategy {
        private final String cardNumber;

        CreditCardPayment(String cardNumber) { this.cardNumber = cardNumber; }

        public void pay(double amount) {
            System.out.println("  [Credit Card] Paid $" + amount
                    + " using card ending in " + cardNumber.substring(cardNumber.length() - 4));
        }
    }

    static class PayPalPayment implements PaymentStrategy {
        private final String email;

        PayPalPayment(String email) { this.email = email; }

        public void pay(double amount) {
            System.out.println("  [PayPal] Paid $" + amount + " via " + email);
        }
    }

    static class CryptoPayment implements PaymentStrategy {
        private final String walletAddress;

        CryptoPayment(String walletAddress) { this.walletAddress = walletAddress; }

        public void pay(double amount) {
            System.out.println("  [Crypto] Paid $" + amount
                    + " to wallet " + walletAddress.substring(0, 6) + "...");
        }
    }

    // ── CONTEXT ─────────────────────────────────────────────────────────────
    // ShoppingCart doesn't know HOW payment works — it just delegates.
    static class ShoppingCart {
        private PaymentStrategy paymentStrategy;

        // Strategy can be set/changed at runtime
        public void setPaymentStrategy(PaymentStrategy strategy) {
            this.paymentStrategy = strategy;
        }

        public void checkout(double total) {
            if (paymentStrategy == null) {
                System.out.println("  [Cart] No payment method set!");
                return;
            }
            System.out.println("  [Cart] Checking out $" + total + "...");
            paymentStrategy.pay(total);   // delegate to strategy
        }
    }

    // ── DEMO ────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        System.out.println("=== STRATEGY PATTERN: Payment System ===\n");

        ShoppingCart cart = new ShoppingCart();

        // 1. Pay with Credit Card
        System.out.println("--- Order 1: Credit Card ---");
        cart.setPaymentStrategy(new CreditCardPayment("4111222233334444"));
        cart.checkout(99.99);

        // 2. Switch to PayPal at runtime — no code change needed!
        System.out.println("\n--- Order 2: PayPal ---");
        cart.setPaymentStrategy(new PayPalPayment("user@example.com"));
        cart.checkout(49.50);

        // 3. Switch to Crypto
        System.out.println("\n--- Order 3: Crypto ---");
        cart.setPaymentStrategy(new CryptoPayment("0xABCDEF123456"));
        cart.checkout(250.00);

        System.out.println("\n=== KEY TAKEAWAY ===");
        System.out.println("  Context (ShoppingCart) is CLOSED for modification.");
        System.out.println("  New payment methods = new class, no if-else changes.");
    }
}
