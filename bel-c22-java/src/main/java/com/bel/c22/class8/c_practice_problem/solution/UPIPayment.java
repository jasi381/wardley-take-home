package com.bel.c22.class8.c_practice_problem.solution;

/** UPI payment — validates UPI ID contains '@' */
public class UPIPayment extends PaymentMethod {
    private String upiId;

    public UPIPayment(double amount, String upiId) {
        super(amount);
        this.upiId = upiId;
    }

    @Override
    public boolean processPayment() {
        // Validate UPI ID
        if (upiId == null || !upiId.contains("@")) {
            System.out.println("  Payment FAILED: Invalid UPI ID (must contain '@')");
            return false;
        }

        System.out.printf("  Processing Rs %.2f via UPI (%s)... SUCCESS%n", amount, upiId);
        return true;
    }
}
