package com.bel.c22.class6.b_dry_kiss_yagni;

/**
 * YAGNI - You Aren't Gonna Need It
 *
 * Principle: Only implement functionality when it is NEEDED.
 * Don't build features "just in case" — you'll waste time
 * building things nobody ever uses.
 */
public class YagniDemo {
    /*
    UserId -> User

    public User getUser(int userId) {
    }

    // {"userId": "124", "username": "abc"}
    public List<User> getUsers(Map<String, String> inputVariables) {
    }
     */

    // =====================================================
    // BEFORE YAGNI (Bad) - Building features nobody asked for
    // =====================================================
    static class CalculatorBad {
        // Client asked for: addition only

        public int add(int a, int b) {
            return a + b;
        }

        // "Maybe they'll need these later..." (YAGNI violation!)
        public int subtract(int a, int b) {
            return a - b;
        }

        public int multiply(int a, int b) {
            return a * b;
        }

        public double divide(int a, int b) {
            return (double) a / b;
        }

        public double power(int base, int exp) {
            return Math.pow(base, exp);
        }

        public double squareRoot(int a) {
            return Math.sqrt(a);
        }
        // Problem: 5 extra methods that may NEVER be used!
        // Wasted development time, more code to maintain.
    }

    // =====================================================
    // AFTER YAGNI (Good) - Only what's needed RIGHT NOW
    // =====================================================
    static class CalculatorGood {
        // Client asked for: addition. We build ONLY addition.
        public int add(int a, int b) {
            return a + b;
        }

        // When client asks for subtraction, we add it THEN.
        // Not before. Not "just in case."
    }

    public static void main(String[] args) {
        System.out.println("=== BEFORE YAGNI ===");
        CalculatorBad bad = new CalculatorBad();
        System.out.println("add(5, 3) = " + bad.add(5, 3));
        System.out.println("(+ 5 unused methods nobody asked for!)");

        System.out.println("\n=== AFTER YAGNI ===");
        CalculatorGood good = new CalculatorGood();
        System.out.println("add(5, 3) = " + good.add(5, 3));
        System.out.println("(Only what was needed. Clean and focused.)");

        System.out.println("\n--- Key Takeaway ---");
        System.out.println("Build what you need NOW, not what you THINK you'll need later.");
        System.out.println("Premature features = wasted effort + more bugs + harder maintenance.");
    }
}
