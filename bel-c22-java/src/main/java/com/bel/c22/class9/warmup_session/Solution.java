package com.bel.c22.class9.warmup_session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * SOLUTION — "Slice of Heaven" Pizza Ordering App
 * =================================================
 *
 * Demonstrates all 4 creational patterns from Class 9 in a single file:
 *
 *   1. SINGLETON          → Pizzeria          (only one shop config)
 *   2. BUILDER            → Pizza.Builder     (custom pizza with many optional fields)
 *   3. FACTORY (simple)   → PizzaFactory      (preset pizzas by name)
 *   4. ABSTRACT FACTORY   → MealFactory       (matched Pizza + Side per theme)
 *
 * Read top-to-bottom — the patterns appear in the same order as the problem statement.
 * Each pattern is a static nested class to keep this self-contained.
 *
 * Run main() to see the full output.
 * MP extends P {
 *     MP() {
 *         super("thin", "");
 *     }
 * }
 *
 * P {
 *     _
 *     _
 *     _
 *     _
 * }
 * Factory {
 *     P createPizza(
 * }
 */
public class Solution {

    // ════════════════════════════════════════════════════════════════════════
    // 1) SINGLETON — Pizzeria
    // ════════════════════════════════════════════════════════════════════════
    // Only ONE pizzeria config should exist. Anyone who needs the shop name,
    // currency, or delivery radius reads it from the same instance.
    //
    // Variant chosen: Bill Pugh (lazy + thread-safe + no synchronization cost).
    // The Holder class is loaded only when getInstance() is first called.
    // ════════════════════════════════════════════════════════════════════════
    static class Pizzeria {
        private final String shopName;
        private final String currency;
        private final int deliveryRadiusKm;

        private Pizzeria() {
            this.shopName = "Slice of Heaven";
            this.currency = "INR";
            this.deliveryRadiusKm = 5;
        }

        // Bill Pugh: inner static class loaded lazily on first access.
        private static class Holder {
            private static final Pizzeria INSTANCE = new Pizzeria();
        }

        public static Pizzeria getInstance() {
            return Holder.INSTANCE;
        }

        public String describe() {
            return "🏪 " + shopName + " | currency=" + currency
                    + " | delivers up to " + deliveryRadiusKm + " km";
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    // 2) BUILDER — Pizza
    // ════════════════════════════════════════════════════════════════════════
    // Pizza has 2 required fields (size, crust) and 4 optional (extraCheese,
    // toppings, spicyLevel, glutenFree). A 6-arg constructor would be horrible;
    // Builder keeps the call site readable AND makes Pizza immutable.
    // ════════════════════════════════════════════════════════════════════════
    static class Pizza {
        private final String size;
        private final String crust;
        private final boolean extraCheese;
        private final List<String> toppings;   // immutable copy
        private final int spicyLevel;
        private final boolean glutenFree;

        // Private — only the Builder constructs Pizza.
        private Pizza(Builder b) {
            this.size = b.size;
            this.crust = b.crust;
            this.extraCheese = b.extraCheese;
            this.toppings = Collections.unmodifiableList(new ArrayList<>(b.toppings));
            this.spicyLevel = b.spicyLevel;
            this.glutenFree = b.glutenFree;
        }

        @Override
        public String toString() {
            return "🍕 " + size + " " + crust + " crust"
                    + (extraCheese ? " + extra cheese" : "")
                    + (toppings.isEmpty() ? "" : " + toppings=" + toppings)
                    + " | spicy=" + spicyLevel
                    + (glutenFree ? " | gluten-free" : "");
        }

        public static class Builder {
            // required
            private final String size;
            private final String crust;
            // optional — sensible defaults
            private boolean extraCheese = false;
            private List<String> toppings = new ArrayList<>();
            private int spicyLevel = 0;
            private boolean glutenFree = false;

            public Builder(String size, String crust) {
                this.size = size;
                this.crust = crust;
            }

            public Builder extraCheese(boolean v)     { this.extraCheese = v; return this; }
            public Builder addTopping(String t)        { this.toppings.add(t); return this; }
            public Builder spicyLevel(int level)       { this.spicyLevel = level; return this; }
            public Builder glutenFree(boolean v)       { this.glutenFree = v; return this; }

            public Pizza build() {
                // Validate required fields here if needed.
                if (size == null || crust == null) {
                    throw new IllegalStateException("size and crust are required");
                }
                return new Pizza(this);
            }
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    // 3) FACTORY (Simple Factory) — PizzaFactory
    // ════════════════════════════════════════════════════════════════════════
    // Most customers don't customize. They name a preset; the factory hides
    // the assembly. Caller writes one line; the if/else lives in one place.
    // ════════════════════════════════════════════════════════════════════════
    static class PizzaFactory {
        public static Pizza create(String preset) {
            switch (preset.toLowerCase()) {
                case "margherita":
                    return new Pizza.Builder("medium", "thin")
                            .extraCheese(true)
                            .build();
                case "pepperoni":
                    return new Pizza.Builder("medium", "thick")
                            .extraCheese(true)
                            .addTopping("pepperoni")
                            .spicyLevel(2)
                            .build();
                case "veggie":
                    return new Pizza.Builder("medium", "thin")
                            .addTopping("olives")
                            .addTopping("mushrooms")
                            .addTopping("capsicum")
                            .spicyLevel(1)
                            .build();
                default:
                    throw new IllegalArgumentException("Unknown preset: " + preset);
            }
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    // 4) ABSTRACT FACTORY — MealFactory
    // ════════════════════════════════════════════════════════════════════════
    // A meal combo bundles a pizza with a side — and they MUST match the theme.
    // The customer picks the theme once (by choosing a concrete factory);
    // both products come out matched, by construction.
    // ════════════════════════════════════════════════════════════════════════

    // Product family member: Side
    interface Side {
        String name();
    }

    static class GarlicBread implements Side {
        @Override public String name() { return "🥖 Garlic Bread"; }
    }

    static class Nachos implements Side {
        @Override public String name() { return "🌮 Nachos with Salsa"; }
    }

    // The Abstract Factory itself — declares the family of products.
    interface MealFactory {
        Pizza createPizza();
        Side  createSide();
        String themeName();
    }

    // Concrete factory 1: Italian — pairs Margherita with Garlic Bread.
    static class ItalianMealFactory implements MealFactory {
        @Override public Pizza createPizza() { return PizzaFactory.create("margherita"); }
        @Override public Side  createSide()  { return new GarlicBread(); }
        @Override public String themeName()  { return "Italian"; }
    }

    // Concrete factory 2: Mexican — pairs Pepperoni with Nachos.
    static class MexicanMealFactory implements MealFactory {
        @Override public Pizza createPizza() { return PizzaFactory.create("pepperoni"); }
        @Override public Side  createSide()  { return new Nachos(); }
        @Override public String themeName()  { return "Mexican"; }
    }

    // ════════════════════════════════════════════════════════════════════════
    // CLIENT CODE — putting all 4 patterns together
    // ════════════════════════════════════════════════════════════════════════
    public static void main(String[] args) {
        // ---- Singleton ----
        Pizzeria shop = Pizzeria.getInstance();
        System.out.println(shop.describe());
        System.out.println("Same instance check: " + (shop == Pizzeria.getInstance()));
        System.out.println();

        // ---- Builder ----
        System.out.println("== Custom pizza (Builder) ==");
        Pizza custom = new Pizza.Builder("large", "cheeseburst")
                .extraCheese(true)
                .addTopping("paneer")
                .addTopping("jalapeno")
               .spicyLevel(4)
                .glutenFree(false)
                .build();
        System.out.println(custom);
        System.out.println();

        // ---- Simple Factory ----
        System.out.println("== Preset pizzas (Factory) ==");
        System.out.println(PizzaFactory.create("margherita"));
        System.out.println(PizzaFactory.create("pepperoni"));
        System.out.println(PizzaFactory.create("veggie"));
        System.out.println();

        // ---- Abstract Factory ----
        System.out.println("== Themed combos (Abstract Factory) ==");
        printCombo(new ItalianMealFactory());
        printCombo(new MexicanMealFactory());
    }

    // Helper that depends ONLY on the abstract factory — not on any concrete theme.
    // Switching the factory swaps the entire matched family. That's the whole point.
    private static void printCombo(MealFactory factory) {
        Pizza p = factory.createPizza();
        Side  s = factory.createSide();
        Pizzeria shop = Pizzeria.getInstance();
        System.out.println("[" + factory.themeName() + " combo @ " + shop.describe().split("\\|")[0].trim() + "]");
        System.out.println("  " + p);
        System.out.println("  " + s.name());
    }
}
