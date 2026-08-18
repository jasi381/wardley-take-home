package com.bel.c22.class2.s9_exception_handling;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * CheckedExceptionDemo — Checked exceptions must be handled at compile time.
 *
 * Checked exceptions:
 *  - Compiler FORCES you to handle them (try-catch or throws)
 *  - Represent recoverable conditions
 *  - Examples: IOException, SQLException, ClassNotFoundException
 */
public class CheckedExceptionDemo {

    // Method declares it throws a checked exception
    public static void readFile(String path) throws IOException {
        FileReader reader = new FileReader(path);
        System.out.println("File opened successfully!");
        reader.close();
    }

    // Method handles the exception internally
    public static void safeReadFile(String path) {
        try {
            FileReader reader = new FileReader(path);
            System.out.println("File opened: " + path);
            reader.close();
        } catch (IOException e) {
            System.out.println("Could not open file: " + e.getMessage());
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("=== Checked Exception Demo ===\n");

        // Approach 1: Caller handles with try-catch
        System.out.println("--- Approach 1: try-catch in caller ---");
        //readFile("nonexistent.txt");

        try {
            readFile("nonexistent.txt");
        } catch (IOException e) {
            System.out.println("Caught IOException: " + e.getMessage());
           // throw new Exception("test");
        }

        // Approach 2: Method handles internally
        System.out.println("\n--- Approach 2: handled inside method ---");
        safeReadFile("nonexistent.txt");

        // ClassNotFoundException — another checked exception
        System.out.println("\n--- ClassNotFoundException ---");
        try {
            Class.forName("com.fake.NonExistentClass");
        } catch (ClassNotFoundException e) {
            System.out.println("Caught: Class not found — " + e.getMessage());
        }
    }
}
