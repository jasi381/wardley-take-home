package com.bel.c22.class1.s5_operators;

/**
 * OperatorsDemo — Demonstrates all major Java operators.
 *
 * Covers: Arithmetic, Relational, Logical, Assignment, Increment/Decrement
 */
public class OperatorsDemo {

    public static void main(String[] args) {

        int a = 10, b = 3;

        // -----------------------------------------------
        // ARITHMETIC OPERATORS
        // -----------------------------------------------
        System.out.println("--- Arithmetic ---");
        System.out.println("a + b = " + (a + b));  // 13
        System.out.println("a - b = " + (a - b));  // 7
        System.out.println("a * b = " + (a * b));  // 30
        System.out.println("a / b = " + (a / b));  // 3  (integer division!)
        System.out.println("a % b = " + (a % b));  // 1  (remainder)

        // -----------------------------------------------
        // RELATIONAL (COMPARISON) OPERATORS
        // -----------------------------------------------
        System.out.println("\n--- Relational ---");
        System.out.println("a > b  : " + (a > b));   // true
        System.out.println("a < b  : " + (a < b));   // false
        System.out.println("a == b : " + (a == b));  // false
        System.out.println("a != b : " + (a != b));  // true

        // -----------------------------------------------
        // LOGICAL OPERATORS
        // -----------------------------------------------
        System.out.println("\n--- Logical ---");
        boolean x = true, y = false;
        System.out.println("x && y = " + (x && y));  // false (AND)
        System.out.println("x || y = " + (x || y));  // true  (OR)
        System.out.println("!x     = " + (!x));       // false (NOT)

        // -----------------------------------------------
        // ASSIGNMENT OPERATORS
        // -----------------------------------------------
        System.out.println("\n--- Assignment ---");
        int score = 50;
        score += 10;  // score = score + 10
        System.out.println("After += 10: " + score);  // 60
        score -= 5;
        System.out.println("After -= 5:  " + score);  // 55

        int x1 = 10;
        int y1 = 5;
        int result = ++x1 + ++y1;
        int result1 = ++x1 + ++y1;

        System.out.println(result);
        System.out.println(result1);

        // -----------------------------------------------
        // INCREMENT / DECREMENT
        // -----------------------------------------------
        System.out.println("\n--- Increment/Decrement ---");
        int count = 5;
        System.out.println("count++: " + count++);  // prints 5, then count becomes 6
        System.out.println("count now: " + count);  // 6
        System.out.println("++count: " + ++count);  // count becomes 7, then prints 7
    }
}
