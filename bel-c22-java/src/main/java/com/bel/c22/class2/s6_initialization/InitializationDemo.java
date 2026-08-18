package com.bel.c22.class2.s6_initialization;

/**
 * InitializationDemo — Static variables and static blocks execution order.
 *
 * Key points:
 *  - Static vars get default values during Preparation
 *  - Static vars get user values during Initialization
 *  - Static blocks run top-to-bottom, only ONCE
 *  - Superclass initializes BEFORE subclass
 */
public class InitializationDemo {

    // Static variable — assigned during initialization
    static int count = 10;

    // Static block #1 — runs during class initialization
    static {
        System.out.println("[Static Block 1] count = " + count);
        count = 20;  // overwrite the value
        System.out.println("[Static Block 1] count updated to " + count);
    }

    // Another static variable
    static int doubled = count * 2;  // uses count = 20, so doubled = 40

    // Static block #2
    static {
        System.out.println("[Static Block 2] doubled = " + doubled);
    }

    public static void main(String[] args) {
        // -------
        /*System.out.println("\n=== Initialization Phase Demo ===");
        System.out.println("Final count   = " + count);     // 20
        System.out.println("Final doubled = " + doubled);   // 40

        // Demonstrate superclass initializes before subclass
        System.out.println("\n--- Superclass before Subclass ---");
        new Dog();  // triggers Animal init first, then Dog init*/
    }
}

// Superclass
class Animal {
    static {
        System.out.println("[Animal] Static block — superclass initialized FIRST");
    }
}

// Subclass
class Dog extends Animal {
    static {
        System.out.println("[Dog] Static block — subclass initialized SECOND");
    }
}
