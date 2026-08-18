package com.bel.c22.class6.b_dry_kiss_yagni;

/**
 * DRY - Don't Repeat Yourself
 *
 * Principle: Every piece of knowledge or logic should have a SINGLE,
 * unambiguous representation within a system.
 *
 * If you copy-paste code, you're violating DRY!
 */
public class DryDemo {

    // =====================================================
    // BEFORE DRY (Bad) - Tax calculation is DUPLICATED
    // =====================================================
    static class InvoiceBad {
        private double amount;

        InvoiceBad(double amount) { this.amount = amount; }

        public void printInvoice() {
            double tax = amount * 0.18;
            double total = amount + tax;
            System.out.println("Invoice Total: Rs." + total);
        }

        public void printReceipt() {
            double tax = amount * 0.18;
            double total = amount + tax;
            System.out.println("Receipt Total: Rs." + total);
        }

    }













    // =====================================================
    // AFTER DRY (Good) - Tax calculation in ONE place
    // =====================================================
    static class InvoiceGood {
        private double amount;
        private static final double GST_RATE = 0.18; // Single source of truth

        InvoiceGood(double amount) { this.amount = amount; }

        // Tax logic in ONE reusable method
        private double calculateTotal() {
            // GST_RATE = 10;
            double tax = calculateTax();
            return amount + tax;
        }

        private double calculateTax() {
            return amount * GST_RATE;
        }

        public void printInvoice() {
            System.out.println("Invoice Total: Rs." + calculateTotal());
        }

        public void printReceipt() {
            System.out.println("Receipt Total: Rs." + calculateTotal());
        }

        // Now if GST changes, update ONE place - GST_RATE!
    }

    public static void main(String[] args) {
        System.out.println("=== BEFORE DRY (duplicated tax logic) ===");
        InvoiceBad bad = new InvoiceBad(1000);
        InvoiceBad bad1 = new InvoiceBad(1000);

        bad.printInvoice();
        bad.printReceipt();

        System.out.println("\n=== AFTER DRY (single tax method) ===");
        InvoiceGood good = new InvoiceGood(1000);
        good.printInvoice();
        good.printReceipt();

        System.out.println("\n--- Key Takeaway ---");
        System.out.println("If you change something in one place and must");
        System.out.println("remember to change it elsewhere too, you're violating DRY!");
    }
}
