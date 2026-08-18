package com.bel.c22.class6.e_lsp;

/**
 * L - Liskov Substitution Principle (LSP)
 *
 * "Child classes must be SUBSTITUTABLE for their parent classes
 *  without breaking the program."
 *
 * If you replace a parent object with a child object, everything
 * should still work correctly. No surprises!
 */
/*
Vehicle
Car extends Vehicle
Bike extends Vehicle

Vehicle car  = new Car();
 */
public class LspDemo {

    // =====================================================
    // BEFORE LSP (Bad) - Ostrich CAN'T fly but extends Bird
    // =====================================================
    static class BirdBad {
        public void fly() throws Exception {
            System.out.println("Flying high!");
        }
    }

    static class SparrowBad extends BirdBad {
        @Override
        public void fly() {
            System.out.println("Sparrow is flying!");
        }
    }

    static class OstrichBad extends BirdBad {
        @Override
        public void fly() throws Exception {
            //throw new Exception("Ostriches can't fly!");
        }
    }

    // This method expects ALL birds can fly — but Ostrich can't!
    static void makeBirdFlyBad(BirdBad bird) throws Exception {
            bird.fly();

    }






    /*
    Bird {
    }
    FlyingBird extends Bird {
       fly();
    }
    Sparraw extends FlyingBird {
    }
    NonFlyingBird extends Bird {
       run();
    }
    Ostrich extends NonFlyingBird {
       run();
    }
     */

    // =====================================================
    // AFTER LSP (Good) - Redesign: all birds can move()
    // =====================================================
    static abstract class Bird {
        public abstract void move(); // ALL birds can move — safe contract!
    }

    static class Sparrow extends Bird {
        @Override
        public void move() {
            System.out.println("Sparrow flies through the sky!");
        }
    }

    static class Ostrich extends Bird {
        @Override
        public void move() {
            System.out.println("Ostrich runs on the ground!"); // Valid! No violation!
        }
    }

    static class Penguin extends Bird {
        @Override
        public void move() {
            System.out.println("Penguin swims in the water!"); // Also valid!
        }
    }

    // This method works perfectly with ANY bird!
    static void makeBirdMove(Bird bird) {
        bird.move(); // Safe — every bird can move in its own way
    }

    public static void main(String[] args) throws Exception{
        System.out.println("=== BEFORE LSP (broken substitution) ===");
        makeBirdFlyBad(new SparrowBad());   // Works fine
//        try {
//            makeBirdFlyBad(new OstrichBad()); // CRASH! LSP violated!
//        } catch (UnsupportedOperationException e) {
//            System.out.println("ERROR: " + e.getMessage());
//        }

        System.out.println("\n=== AFTER LSP (proper substitution) ===");
        /*makeBirdMove(new Sparrow());   // Flies
        makeBirdMove(new Ostrich());   // Runs
        makeBirdMove(new Penguin());   // Swims
        // Every bird can be substituted without breaking anything!

        System.out.println("\n--- Key Takeaway ---");
        System.out.println("If a child class throws exceptions or does nothing");
        System.out.println("for a parent method, your hierarchy is WRONG.");
        System.out.println("Redesign so ALL children can fulfill the contract!");*/
    }
}
