package com.bel.c22.class6.b_dry_kiss_yagni;

/**
 * DRY, KISS, YAGNI
 *
 * DRY  - Don't Repeat Yourself: one source of truth per bit of logic.
 * KISS - Keep It Simple: prefer the plain, readable solution.
 * YAGNI - You Aren't Gonna Need It: only build what's needed now.
 */
public class DryKissYagniDemo {

    static final double GST_RATE = 0.18;

    // ---------- DRY ----------
    // BAD: the GST calculation is duplicated in every method that needs it.
    static double badInvoiceTotal(double price) {
        return price + (price * 0.18);
    }

    static double badReceiptTotal(double price) {
        return price + (price * 0.18); // same 0.18 repeated - change the rate and you must hunt every copy
    }

    // GOOD: one method is the single source of truth for the calculation.
    static double addGst(double price) {
        return price + (price * GST_RATE);
    }

    // ---------- KISS ----------
    // BAD: deeply nested conditions are harder to scan than they need to be.
    static boolean badCanCheckout(Double cart, Boolean paid) {
        if (cart != null) {
            if (cart > 0) {
                if (paid != null && paid) {
                    return true;
                }
            }
        }
        return false;
    }

    // GOOD: guard clauses - each rule is a single line, read top to bottom.
    static boolean canCheckout(Double cart, Boolean paid) {
        if (cart == null) return false;
        if (cart <= 0) return false;
        if (paid == null || !paid) return false;
        return true;
    }

    // ---------- YAGNI ----------
    // A calculator that only implements what's actually asked for: add().
    // No multiply(), divide(), or "future-proof" config nobody requested yet.
    static class Calculator {
        double add(double a, double b) {
            return a + b;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== DRY ===");
        System.out.printf("Invoice total: %.2f | Receipt total: %.2f (both use addGst, one rate)%n",
                addGst(1000), addGst(500));

        System.out.println("\n=== KISS ===");
        System.out.println("canCheckout(1200.0, true)  -> " + canCheckout(1200.0, true));
        System.out.println("canCheckout(0.0, true)     -> " + canCheckout(0.0, true));
        System.out.println("canCheckout(1200.0, null)  -> " + canCheckout(1200.0, null));

        System.out.println("\n=== YAGNI ===");
        Calculator calculator = new Calculator();
        System.out.println("2 + 3 = " + calculator.add(2, 3));
        System.out.println("No multiply()/divide() here - add them only when a real requirement shows up.");
    }
}
