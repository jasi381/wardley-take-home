package com.bel.c22.d_decorator;

/**
 * DECORATOR PATTERN
 *
 * Intent: Attach additional responsibilities to an object dynamically.
 *         Decorators provide a flexible alternative to subclassing for extending functionality.
 *
 * Problem it solves — "Subclass explosion" anti-pattern:
 *   CoffeeWithMilk, CoffeeWithMilkAndSugar, CoffeeWithMilkAndSugarAndWhip...
 *   N toppings → 2^N subclasses. Unmaintainable.
 *
 * When to use:
 *   - You need to add behavior to individual objects, not the whole class
 *   - You want to combine behaviors flexibly (mix and match)
 *   - Subclassing would cause a class explosion
 *
 * Structure:
 *   1. Component (interface) — defines the base operation
 *   2. Concrete Component   — the base object being decorated
 *   3. Decorator (abstract)  — wraps a Component, delegates to it
 *   4. Concrete Decorators   — add behavior before/after delegating
 */
public class DecoratorDemo {

    // ── COMPONENT interface ─────────────────────────────────────────────────
    interface Coffee {
        String getDescription();
        double getCost();
    }

    // ── CONCRETE COMPONENT ──────────────────────────────────────────────────
    static class SimpleCoffee implements Coffee {
        public String getDescription() { return "Simple coffee"; }
        public double getCost()        { return 2.00; }
    }

    // ── ABSTRACT DECORATOR ──────────────────────────────────────────────────
    // Wraps a Coffee and delegates to it. Subclasses add behavior.
    static abstract class CoffeeDecorator implements Coffee {
        protected final Coffee wrapped;   // the component we're decorating

        CoffeeDecorator(Coffee wrapped) { this.wrapped = wrapped; }
    }

    // ── CONCRETE DECORATORS ─────────────────────────────────────────────────

    static class MilkDecorator extends CoffeeDecorator {
        MilkDecorator(Coffee coffee) {
            super(coffee);
        }

        public String getDescription() { return wrapped.getDescription() + " + milk"; }
        public double getCost()        { return wrapped.getCost() + 0.50; }
    }

    static class SugarDecorator extends CoffeeDecorator {
        SugarDecorator(Coffee coffee) { super(coffee); }

        public String getDescription() { return wrapped.getDescription() + " + sugar"; }
        public double getCost()        { return wrapped.getCost() + 0.25; }
    }
    /*
    Method

    Req + Resp

    LoggingDecorator {

       Method

       execute() {
         log -> Request
          Method.execute()
          log -> Response
       }
    }
     */

    static class WhipCreamDecorator extends CoffeeDecorator {
        WhipCreamDecorator(Coffee coffee) { super(coffee); }

        public String getDescription() { return wrapped.getDescription() + " + whip cream"; }
        public double getCost()        { return wrapped.getCost() + 0.75; }
    }

    // Helper to print an order
    static void printOrder(Coffee coffee) {
        System.out.printf("  %-45s $%.2f%n", coffee.getDescription(), coffee.getCost());
    }

    // ── DEMO ────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        System.out.println("=== DECORATOR PATTERN: Coffee Shop ===\n");

        // 1. Plain coffee
        Coffee order1 = new SimpleCoffee();
        printOrder(order1);

        // 2. Coffee + milk  (wrap SimpleCoffee in MilkDecorator)
        Coffee order2 = new MilkDecorator(new SimpleCoffee());
        printOrder(order2);

        // 3. Coffee + milk + sugar  (wrap again!)
        Coffee order3 = new SugarDecorator(new MilkDecorator(new SimpleCoffee()));
        printOrder(order3);

        // 4. Coffee + milk + sugar + whip cream  (stack decorators)
        Coffee order4 = new WhipCreamDecorator(
                            new MilkDecorator(
                                new SugarDecorator(
                                    new SimpleCoffee())));
        printOrder(order4);

        // 5. Double milk!  (same decorator applied twice)
        Coffee order5 = new SugarDecorator(new SugarDecorator(new MilkDecorator(new MilkDecorator(new SimpleCoffee()))));
        printOrder(order5);

        System.out.println("\n=== KEY TAKEAWAY ===");
        System.out.println("  Decorators wrap objects, not classes.");
        System.out.println("  Stack them like layers — each adds behavior.");
        System.out.println("  No subclass explosion: 3 decorators → infinite combos.");
    }
}
