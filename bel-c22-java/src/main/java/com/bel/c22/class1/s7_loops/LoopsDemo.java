package com.bel.c22.class1.s7_loops;

/**
 * LoopsDemo — Demonstrates for, while, and do-while loops.
 *
 * Use loops when you need to repeat code multiple times.
 */
public class LoopsDemo {

    public static void main(String[] args) {

        // -----------------------------------------------
        // FOR LOOP — best when you know the exact count
        // Structure: for (init; condition; update)
        // -----------------------------------------------
        System.out.println("--- for loop: 1 to 5 ---");
        for (int i = 1; i <= 5; i++) {
           // i++;
            System.out.println("i = " + i);
        }

        // Iterating over an array with for loop
        System.out.println("\n--- for loop over array ---");
        String[] names = {"Alice", "Bob", "Charlie"};
        for (int i = 0; i < names.length; i++) {
            System.out.println("Learner " + (i + 1) + ": " + names[i]);
        }

        // Enhanced for loop (for-each) — cleaner for arrays
        System.out.println("\n--- for-each loop ---");
        for (String name : names) {
            System.out.println("Hello, " + name + "!");
        }

        // -----------------------------------------------
        // WHILE LOOP — best when condition is checked first
        // -----------------------------------------------
        System.out.println("\n--- while loop ---");
        int count = 3;
        while (count <= 3) {
            System.out.println("Attempt #" + count);
            count++; // important: update to avoid infinite loop!
        }

        // -----------------------------------------------
        // DO-WHILE LOOP — always runs at least once
        // -----------------------------------------------
        System.out.println("\n--- do-while loop ---");
        int num = 10;
        do {
            System.out.println("Running with num = " + num);
            num++;
        } while (num < 10); // condition is false from the start, but runs once!
    }
}
