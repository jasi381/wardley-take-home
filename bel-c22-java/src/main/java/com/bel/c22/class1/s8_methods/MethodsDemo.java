package com.bel.c22.class1.s8_methods;

/**
 * MethodsDemo — Demonstrates how to define and call methods in Java.
 *
 * Methods help us:
 *  - Avoid repeating code (DRY principle)
 *  - Break a big problem into smaller pieces
 *  - Make code easier to read and test
 */
public class MethodsDemo {

    int a;

    public static void main(String[] args) {

        // Calling methods defined below
        greet("Alice");
        greet("Bob");

        int sum = add(10, 25);
        System.out.println("Sum: " + sum);

        boolean eligible = isEligible(20);
        System.out.println("Eligible: " + eligible);

        printTable(5);
    }

    // Method with a parameter, no return value (void)
    static void greet(String name) {
        System.out.println("Hello, " + name + "! Welcome to Java class.");
    }

    // Method with parameters that RETURNS a value
    static int add(int a, int b) {
        return a + b;  // sends the result back to the caller
    }

    // Method that returns boolean
    static boolean isEligible(int age) {
        return age >= 18;  // returns true if age is 18 or more
    }

    // Method that uses a loop internally
    static void printTable(int number) {
        System.out.println("\nMultiplication table of " + number + ":");
        for (int i = 1; i <= 5; i++) {
            System.out.println(number + " x " + i + " = " + (number * i));
        }
    }
}
