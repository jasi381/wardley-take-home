package com.bel.c22.class1.s10_exercise;

import java.util.Scanner;

/**
 * LearnerManagementSystem — Session #1 Exercise Solution
 *
 * A simple menu-driven console app to manage learners.
 *
 * Concepts used:
 *  - Parallel arrays (names, ages, xp)
 *  - switch for menu selection
 *  - if-else for age validation
 *  - while loop to keep menu running
 *  - Scanner for user input
 *  - Methods to keep main() clean
 */
public class LearnerManagementSystem {

    static final int MAX_LEARNERS = 10;

    // List<Learner> learners;
    // Parallel arrays to store learner data
    static String[] names  = new String[MAX_LEARNERS];
    static int[]    ages   = new int[MAX_LEARNERS];
    static int[]    xpVals = new int[MAX_LEARNERS];
    static int      count  = 0;  // how many learners have been added

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("===== Airtribe Learner Manager =====");

        boolean running = true;

        while (running) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume leftover newline after nextInt()

            // switch to decide which action to take
            switch (choice) {
                case 1:
                    addLearner();
                    break;
                case 2:
                    displayLearners();
                    break;
                case 3:
                    calculateAverage();
                    break;
                case 4:
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1-4.");
            }

            System.out.println();
        }

        scanner.close();
    }

    // -----------------------------------------------
    // Print the menu options
    // -----------------------------------------------
    static void printMenu() {
        System.out.println("1. Add Learner");
        System.out.println("2. Display All Learners");
        System.out.println("3. Calculate Average XP");
        System.out.println("4. Exit");
        System.out.print("Select: ");
    }

    // -----------------------------------------------
    // Add a new learner with validation
    // -----------------------------------------------
    static void addLearner() {
        if (count >= MAX_LEARNERS) {
            System.out.println("Learner list is full. Cannot add more.");
            return;
        }

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter age: ");
        int age = scanner.nextInt();

        // Validate age using if-else
        if (age < 18 || age > 100) {
            System.out.println("Invalid age. Age must be between 18 and 100.");
            scanner.nextLine(); // consume leftover input
            return;
        }

        System.out.print("Enter XP: ");
        int xp = scanner.nextInt();
        scanner.nextLine();

        // Store in arrays at position 'count'
        names[count]  = name;
        ages[count]   = age;
        xpVals[count] = xp;
        count++;

        System.out.println("Learner added successfully!");
    }

    // -----------------------------------------------
    // Display all learners
    // -----------------------------------------------
    static void displayLearners() {
        if (count == 0) {
            System.out.println("No learners added yet.");
            return;
        }

        System.out.println("--- Learner List ---");
        for (int i = 0; i < count; i++) {
            System.out.println((i + 1) + ". " + names[i]
                    + " | Age: " + ages[i]
                    + " | XP: "  + xpVals[i]);
        }
    }

    // -----------------------------------------------
    // Calculate and display average XP
    // -----------------------------------------------
    static void calculateAverage() {
        if (count == 0) {
            System.out.println("No learners to calculate average for.");
            return;
        }

        int total = 0;
        for (int i = 0; i < count; i++) {
            total += xpVals[i];
        }

        // Cast to double so we get a decimal result
        double average = (double) total / count;
        System.out.printf("Average XP: %.2f%n", average);
    }
}
