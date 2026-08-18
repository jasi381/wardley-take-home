package com.bel.c22.class8.c_practice_problem.solution;

/** Credit card payment — validates card number has 16 digits */
public class CreditCardPayment extends PaymentMethod {
    private String cardNumber;

    public CreditCardPayment(double amount, String cardNumber) {
        super(amount);
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean processPayment() {
        // Validate card number
        if (cardNumber == null || cardNumber.length() != 16) {
            System.out.println("  Payment FAILED: Invalid card number (must be 16 digits)");
            return false;
        }

        String masked = "****-****-****-" + cardNumber.substring(12);
        System.out.printf("  Processing Rs %.2f on card %s... SUCCESS%n", amount, masked);
        return true;
    }
}
