package com.bel.c22.class1.s6_control_flow;

/**
 * IfElseDemo — Demonstrates if, if-else, and else-if ladder.
 *
 * Control flow statements decide WHICH code to run based on conditions.
 */
public class IfElseDemo {

    public static void main(String[] args) {

        // -----------------------------------------------
        // Simple if — runs only when condition is true
        // -----------------------------------------------
        int age = 20;

        if (age >= 18) {
            System.out.println("You are an adult.");
        }

        // -----------------------------------------------
        // if-else — runs one block OR the other
        // -----------------------------------------------
        int balance = 500;
        int amount  = 800;

        if (balance >= amount) {
            System.out.println("Transaction successful.");
        } else {
            System.out.println("Insufficient balance.");
        }

        // -----------------------------------------------
        // else-if ladder — checks multiple conditions
        // First matching condition runs; rest are skipped.
        // -----------------------------------------------
        int score = 82;
        String grade;

        if (score >= 90) {
            grade = "A";
        } else if (score >= 75) {
            grade = "B";
        } else if (score >= 60) {
            grade = "C";
        } else {
            grade = "F";
        }

        System.out.println("Score " + score + " → Grade: " + grade);
    }
}
