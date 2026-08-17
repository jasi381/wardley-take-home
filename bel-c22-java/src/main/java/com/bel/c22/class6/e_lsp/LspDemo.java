package com.bel.c22.class6.e_lsp;

/**
 * L - Liskov Substitution Principle (LSP)
 *
 * "Subtypes must be substitutable for their base types without altering
 * the correctness of the program." - Barbara Liskov
 */
public class LspDemo {

    // =====================================================
    // BEFORE LSP (Bad) - Ostrich breaks the Bird contract
    // =====================================================
    static class BirdBad {
        void fly() {
            System.out.println("Flying high!");
        }
    }

    static class Sparrow extends BirdBad {
        // fine - sparrows really do fly
    }

    static class OstrichBad extends BirdBad {
        @Override
        void fly() {
            throw new UnsupportedOperationException("Ostriches can't fly!"); // VIOLATION
        }
    }

    static void makeItFlyBad(BirdBad bird) {
        bird.fly(); // this method was promised every BirdBad can fly...
    }

    // =====================================================
    // AFTER LSP (Good) - redesign the contract so every child can keep it
    // =====================================================
    abstract static class Bird {
        abstract void move(); // every bird can move - just not the same way
    }

    static class FlyingBird extends Bird {
        @Override
        void move() {
            System.out.println("Flying high!");
        }
    }

    static class Ostrich extends Bird {
        @Override
        void move() {
            System.out.println("Running fast on the ground!");
        }
    }

    static void makeItMove(Bird bird) {
        bird.move(); // works for ANY Bird, no surprises
    }

    public static void main(String[] args) {
        System.out.println("=== BEFORE LSP ===");
        makeItFlyBad(new Sparrow());
        try {
            makeItFlyBad(new OstrichBad());
        } catch (UnsupportedOperationException e) {
            System.out.println("CRASH swapping in Ostrich: " + e.getMessage());
        }

        System.out.println("\n=== AFTER LSP ===");
        Bird[] birds = {new FlyingBird(), new Ostrich()};
        for (Bird bird : birds) {
            makeItMove(bird); // no crash, no exceptions, no surprises for ANY Bird
        }

        System.out.println("\n--- Key Takeaway ---");
        System.out.println("The 'swap test': replace the parent with any child - if it still works, LSP holds.");
        System.out.println("BirdBad promised fly(); Ostrich couldn't keep that promise, so the hierarchy was wrong.");
    }
}
