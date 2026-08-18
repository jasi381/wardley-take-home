package com.bel.c22.class6.d_ocp;

/**
 * O - Open/Closed Principle (OCP)
 *
 * "Classes should be OPEN for extension but CLOSED for modification."
 *
 * You should be able to add new behavior WITHOUT changing existing code.
 * Use interfaces/abstract classes to achieve this.
 */
public class OcpDemo {

    // =====================================================
    // BEFORE OCP (Bad) - Adding new payment = modifying class
    // =====================================================
    static class PaymentProcessorBad {
        public void processPayment(String type, double amount) {
            // Every new payment method = modify this method!
            if (type.equals("UPI")) {
                // upiPayment.pay();
                System.out.println("Processing UPI payment of Rs." + amount);
            } else if (type.equals("CreditCard")) {
                // creditcard.pay();
                System.out.println("Processing Credit Card payment of Rs." + amount);
            } else if (type.equals("DebitCard")) {
                System.out.println("Processing Debit Card payment of Rs." + amount);
            }
















            // Want to add NetBanking? Must MODIFY this class!
            // Risk breaking existing UPI/Card logic!
        }
    }

    // =====================================================
    // AFTER OCP (Good) - Adding new payment = new class only
    // =====================================================

    /*
    UpiPayment {

       payViaUpi(int amount) {
       }
    }

    CreditCardPayment {

       payViaCC(int amount) {
       }
    }

    public void processPayment(String type, double amount) {
            // Every new payment method = modify this method!
            if (type.equals("UPI")) {
                UpiPayment.payViaUpi(500)
            } else if (type.equals("CreditCard")) {
                CreditCardPayment.payViaCC(amount);
            }


     abs MoveXYZ {

       abstract validate()
       move {
        ---
        =--
        --

       }
     }

     class ABC extends MoveXYZ {

      validate() {
      ---
     }

     move() {
       super.move1();
       myExecution();
       super.move2();
     }
     }
     */
    // Step 1: Define an interface (the contract)
    interface PaymentMethod {
        void pay(double amount);
    }

    // Step 2: Each payment type is its own class
    static class UpiPayment implements PaymentMethod {
        @Override
        public void pay(double amount) {
            System.out.println("Processing UPI payment of Rs." + amount);
        }
    }

    static class CreditCardPayment implements PaymentMethod {
        @Override
        public void pay(double amount) {
            System.out.println("Processing Credit Card payment of Rs." + amount);
        }
    }

    // Step 3: Want to add NetBanking? Just create a NEW class!
    // No existing code is modified!
    static class NetBankingPayment implements PaymentMethod {
        @Override
        public void pay(double amount) {
            System.out.println("Processing Net Banking payment of Rs." + amount);
        }
    }

    // Step 4: The processor works with ANY PaymentMethod
    static class PaymentProcessor {
        public void processPayment(PaymentMethod method, double amount) {
            method.pay(amount); // Works for UPI, Card, NetBanking, or ANYTHING new!
        }
    }

    public static void main(String[] args) {
        System.out.println("=== BEFORE OCP (if-else chain) ===");
        PaymentProcessorBad bad = new PaymentProcessorBad();
        bad.processPayment("UPI", 500);
        bad.processPayment("CreditCard", 1500);

        System.out.println("\n=== AFTER OCP (interface-based) ===");
        PaymentProcessor processor = new PaymentProcessor();
        processor.processPayment(new UpiPayment(), 500);
        processor.processPayment(new CreditCardPayment(), 1500);
        processor.processPayment(new NetBankingPayment(), 2000);
        // Added NetBanking WITHOUT touching UpiPayment or CreditCardPayment!

        System.out.println("\n--- Key Takeaway ---");
        System.out.println("Open for EXTENSION: Add new classes anytime");
        System.out.println("Closed for MODIFICATION: Never touch existing working code");
    }
}
