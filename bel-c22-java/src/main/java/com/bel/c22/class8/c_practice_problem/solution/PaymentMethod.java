package com.bel.c22.class8.c_practice_problem.solution;

/**
 * Abstract PaymentMethod — base class for all payment types.
 * Uses inheritance correctly: all payments IS-A PaymentMethod.
 * Subclasses specialize the processPayment() step.
 */
public abstract class PaymentMethod {
    protected double amount;

    public PaymentMethod(double amount) {
        this.amount = amount;
    }

    // Each payment type implements its own processing logic (polymorphism)
    public abstract boolean processPayment();

    // Shared behavior — same for all payment types
    public void generateReceipt() {
        System.out.printf("  RECEIPT: Rs %.2f paid via %s%n",
                amount, getClass().getSimpleName());
        System.out.println("  Thank you for your payment!");
    }
}
