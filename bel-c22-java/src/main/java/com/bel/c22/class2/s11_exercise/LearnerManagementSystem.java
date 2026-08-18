package com.bel.c22.class2.s11_exercise;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * LearnerManagementSystem — Menu-driven LMS for Airtribe.
 *
 * Demonstrates concepts from Session 1 & 2:
 *  - Variables, data types, arrays
 *  - if-else validation, switch for menu
 *  - Loops (while for menu, for to iterate learners)
 *  - Exception handling (try-catch for invalid input)
 */
public class LearnerManagementSystem {

    // Max capacity for learners
    static final int MAX_LEARNERS = 50;

    // Parallel arrays to store learner data
    static String[] names = new String[MAX_LEARNERS];
    static int[] ages = new int[MAX_LEARNERS];
    static int[] xpScores = new int[MAX_LEARNERS];
    static int learnerCount = 0;

    // Add a new learner with validation
    public static void addLearner(Scanner scanner) {
        if (learnerCount >= MAX_LEARNERS) {
            System.out.println("Error: Maximum capacity reached!");
            return;
        }

        try {
            System.out.print("Enter learner name: ");
            String name = scanner.nextLine();

            System.out.print("Enter learner age: ");
            int age = scanner.nextInt();

            // Validate age using if-else
            if (age < 18 || age > 100) {
                System.out.println("Invalid age! Must be between 18 and 100.");
                scanner.nextLine(); // consume leftover newline
                return;
            }

            System.out.print("Enter learner XP: ");
            int xp = scanner.nextInt();
            scanner.nextLine(); // consume leftover newline

            // Store the learner
            names[learnerCount] = name;
            ages[learnerCount] = age;
            xpScores[learnerCount] = xp;
            learnerCount++;

            System.out.println("Learner added successfully!\n");

        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a number.");
            scanner.nextLine(); // clear bad input
        }
    }

    // Display all learners using a loop
    public static void displayAllLearners() {
        if (learnerCount == 0) {
            System.out.println("No learners registered yet.\n");
            return;
        }

        System.out.println("\n--- All Learners ---");
        for (int i = 0; i < learnerCount; i++) {
            System.out.printf("%d. %s (Age: %d, XP: %d)%n",
                    i + 1, names[i], ages[i], xpScores[i]);
        }
        System.out.println();
    }

    // Calculate average XP using a loop
    public static void calculateAverageXP() {
        if (learnerCount == 0) {
            System.out.println("No learners to calculate average.\n");
            return;
        }

        int totalXP = 0;
        for (int i = 0; i < learnerCount; i++) {
            totalXP += xpScores[i];
        }

        double average = (double) totalXP / learnerCount;
        System.out.printf("Average XP of %d learners: %.2f%n%n", learnerCount, average);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("=== Airtribe Learner Management System ===\n");

        // Menu loop — keeps running until user exits
        while (running) {
            System.out.println("1. Add Learner");
            System.out.println("2. Display All Learners");
            System.out.println("3. Calculate Average XP");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                // Switch statement for menu selection
                switch (choice) {
                    case 1:
                        addLearner(scanner);
                        break;
                    case 2:
                        displayAllLearners();
                        break;
                    case 3:
                        calculateAverageXP();
                        break;
                    case 4:
                        running = false;
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option! Choose 1-4.\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number!\n");
                scanner.nextLine(); // clear bad input
            }
        }

        scanner.close();
    }
}
