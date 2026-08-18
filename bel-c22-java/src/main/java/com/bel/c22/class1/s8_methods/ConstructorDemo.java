package com.bel.c22.class1.s8_methods;

/**
 * ConstructorDemo — Demonstrates different types of constructors.
 *
 * A constructor is a special method that:
 *  - Has the SAME NAME as the class
 *  - Has NO return type (not even void)
 *  - Is automatically called when you do: new ClassName()
 *
 *  (4)-(memaddr123)--(true)-----
 *
 *  (4)-(memaddr123)--(true)-----
 *
 *  --memaddr123-> what is written here------
 *
 *
 *
 */
public class ConstructorDemo {

    // ----- The Learner class with multiple constructor types -----
    static class Learner {

        static int a;
        String name;
        int    xp;
        Car car;
        int abc;

        // 1. No-argument constructor — sets default values
        Learner() {
             name = "Unknown";
             xp   = 0;
            // this.car = new Car();
            System.out.println("No-arg constructor called.");
        }

        /*
         static void display() {
            print("Hello");
        }
         */

        // 2. Parameterized constructor — sets specific values
        Learner(String name, int xp) {
            this.name = name;
            this.xp   = xp;
            System.out.println("Parameterized constructor called.");
        }

        Learner(String name, int xp, int abc) {
            this(name, xp);
            this.abc = abc;
            System.out.println("Parameterized constructor called.");
        }

        // 3. Copy constructor — creates a new object from an existing one
        Learner(Learner other) {
            this.name = other.name;
            this.xp   = other.xp;
            this.car = other.car;
            this.abc = other.abc;
            System.out.println("Copy constructor called.");
        }

        void display() {
            System.out.println("  Learner: " + name + " | XP: " + xp);
        }
    }

    // ----- Main method to test them -----
    public static void main(String[] args) {
        //Learner.display();
        Learner l1 = new Learner();              // uses no-arg constructor
        l1.display();
        Car c1 = new Car();
        c1.start();
        Car.test();

        Learner l2 = new Learner("Alice", 120); // uses parameterized constructor
        l2.display();

        Learner l3 = new Learner(l2);            // uses copy constructor
        l3.display();

        // l2 and l3 are DIFFERENT objects (separate copies)
        l3.name = "Bobfwehfiwhfhfhohsbvksdbvk";  // changing l3 does NOT affect l2 new String("Bob");
        /*
        l2 -> Mem1 -> "Alice"
        l3 -> Mem2 -> "Bob"

        Mem2 -> "Bob"

        l2 -> Mem3 -> CarObj (number_of_doors = 4)
        l3 -> Mem3
         */
        System.out.println("\nAfter modifying l3:");
        l2.display();  // still Alice
        l3.display();  // now Bob
    }
}
