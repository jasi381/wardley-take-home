package com.bel.c22.class1.s7_loops;

/**
 * JumpStatementsDemo — Demonstrates break, continue, and return.
 *
 * break    → exit the loop completely
 * continue → skip the current iteration, go to the next
 * return   → exit the entire method (optionally return a value)
 */
public class JumpStatementsDemo {

    public static void main(String[] args) {

        // -----------------------------------------------
        // BREAK — stop the loop as soon as condition is met
        // -----------------------------------------------
        System.out.println("--- break: find first even number ---");
        int[] numbers = {1, 3, 7, 4, 9, 12};

        for (int n : numbers) {
            if (n % 2 == 0) {
                System.out.println("Found even number: " + n);
                break;  // stop searching after the first match
            }
        }

        // -----------------------------------------------
        // CONTINUE — skip certain values, keep looping
        // -----------------------------------------------
        System.out.println("\n--- continue: skip odd numbers ---");
        for (int i = 1; i <= 8; i++) {
            if (i % 2 != 0) {
                continue;  // skip odd numbers
            }
            System.out.println(i);  // only even numbers print
        }

        // -----------------------------------------------
        // RETURN — exit the method and optionally return a value
        // -----------------------------------------------
        System.out.println("\n--- return: get a discount label ---");
        System.out.println(getDiscountLabel(850));
        System.out.println(getDiscountLabel(450));
    }

    // This method uses return to exit early
    static String getDiscountLabel(int price) {
        if (price > 500) {
            return "Premium — 20% off!";  // exits here if condition is true
        }
        return "Standard — no discount";   // fallback return
    }
}
