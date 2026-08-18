package com.bel.c22.class6.b_dry_kiss_yagni;

/**
 * KISS - Keep It Simple, Stupid
 *
 * Principle: Simplicity should be a key goal in design.
 * The simplest solution that works is usually the best one.
 *
 * Don't over-complicate things!
 */
public class KissDemo {

    // =====================================================
    // BEFORE KISS (Bad) - Overly complex, deeply nested
    // =====================================================
    static class OrderProcessorBad {
        public void processOrder(String item, int quantity, boolean isPaid) {
            // Deeply nested if-else = hard to read!
            if (item != null) {
                if (!item.isEmpty()) {
                    if (quantity > 0) {
                        if (isPaid) {
                            System.out.println("Shipping " + quantity + "x " + item);
                        } else {
                            System.out.println("Payment pending for " + item);
                        }
                    } else {
                        System.out.println("Invalid quantity!");
                    }
                } else {
                    System.out.println("Item name is empty!");
                }
            } else {
                System.out.println("Item is null!");
            }
        }
    }









    // =====================================================
    // AFTER KISS (Good) - Early returns, flat structure
    // =====================================================
    static class OrderProcessorGood {
        public void processOrder(String item, int quantity, boolean isPaid) {
            // Guard clauses: handle errors first, then the happy path
            if (item == null) {
                System.out.println("Item is null!");
                return;
            }

            if (item.isEmpty()) {
                System.out.println("Item name is empty!");
                return;
            }

            if (quantity <= 0) {
                System.out.println("Invalid quantity!");
                return;
            }

            if (!isPaid) {
                System.out.println("Payment pending for " + item);
                return;
            }

            // Happy path - clean and readable!
            System.out.println("Shipping " + quantity + "x " + item);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== BEFORE KISS (deeply nested) ===");
        OrderProcessorBad bad = new OrderProcessorBad();
        bad.processOrder("Laptop", 2, true);
        bad.processOrder(null, 1, true);
        bad.processOrder("Phone", -1, true);

        System.out.println("\n=== AFTER KISS (guard clauses, flat) ===");
        OrderProcessorGood good = new OrderProcessorGood();
        good.processOrder("Laptop", 2, true);
        good.processOrder(null, 1, true);
        good.processOrder("Phone", -1, true);

        System.out.println("\n--- Key Takeaway ---");
        System.out.println("Use early returns (guard clauses) to avoid deep nesting.");
        System.out.println("If a junior dev can't understand your code, simplify it!");
    }
}
