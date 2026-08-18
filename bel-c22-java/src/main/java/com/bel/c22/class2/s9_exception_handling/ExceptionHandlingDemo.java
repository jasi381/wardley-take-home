package com.bel.c22.class2.s9_exception_handling;

/**
 * ExceptionHandlingDemo — Understanding try-catch and exception types.
 *
 *
 * f1 () {
 *     -
 *     -
 *     f1()
 *     -
 *     -
 * }
 * t1 (1 billions)-> f1 -> f1 -> f1 -> f1 ->
 * Exception Hierarchy:
 *   Throwable → Error (irrecoverable)
 *             → Exception → Checked (compiler enforced)
 *                         → Unchecked/RuntimeException (programming errors)
 */
public class ExceptionHandlingDemo {

    public static void main(String[] args) {
        System.out.println("=== Exception Handling Demo ===\n");
       // int result1 = 10 / 0;
        // 1. ArithmeticException (Unchecked)
        System.out.println("--- ArithmeticException ---");
        int a = 5, b = 0;
        try {
            int result =  a/ b;  // division by zero!
        } catch (ArithmeticException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        // 2. NullPointerException (Unchecked)
        System.out.println("\n--- NullPointerException ---");
        try {
            String name = null;
            int length = name.length();  // null has no methods!
        } catch (NullPointerException e) {
            System.out.println("Caught: Cannot call methods on null!");
        }

        // 3. ArrayIndexOutOfBoundsException (Unchecked)
        System.out.println("\n--- ArrayIndexOutOfBoundsException ---");
        //try {
            int[] arr = {1, 2, 3};
            // int n =
            int value = arr[5];  // index 5 doesn't exist!
        //} catch (ArrayIndexOutOfBoundsException e) {
           // System.out.println("Caught: " + e.getMessage());
        //}

        // 4. Multiple catch blocks
        System.out.println("\n--- Multiple Catch Blocks ---");
        try {
            String text = "hello";
            int number = Integer.parseInt(text);  // not a number!
        } catch (NumberFormatException e) {
            System.out.println("Caught NumberFormatException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Caught generic exception: " + e.getMessage());
        }

        // 5. Finally block — always executes
        System.out.println("\n--- Finally Block ---");
        try {
            System.out.println("Inside try block");
            int x = 10 / 2;  // no error here
        } catch (Exception e) {
            System.out.println("Caught: " + e.getMessage());
        } finally {
            System.out.println("Finally block ALWAYS runs (cleanup here)");
        }

        System.out.println("\nProgram continues after exception handling!");
    }
}
