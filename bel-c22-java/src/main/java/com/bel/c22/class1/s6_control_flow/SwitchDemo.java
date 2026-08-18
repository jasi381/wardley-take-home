package com.bel.c22.class1.s6_control_flow;

/**
 * SwitchDemo — Demonstrates the switch statement.
 *
 * Use switch when matching a single variable against several exact values.
 * The 'break' prevents fall-through to the next case.
 */
public class SwitchDemo {

    public static void main(String[] args) {

        // -----------------------------------------------
        // switch on an int — classic menu selection
        // -----------------------------------------------
        int menuChoice = 2;
        String name = "Test";

        System.out.println("--- Menu ---");
        Integer i;
        /*
        switch (name) {
            case "123":
                System.out.println("Add Learner");
                break;
            case "Test1":
                System.out.println("Display All Learners");
                break;
            case "abc":
                System.out.println("Calculate Average XP");
                break;
            default:
                System.out.println("Invalid choice. Please select 1-3.");
        }*/

        // -----------------------------------------------
        // switch on a String — common in real applications
        // -----------------------------------------------
        String day = "Saturday";

        switch (day) {
            case "Saturday":
                //break;
            case "Sunday":
                System.out.println(day + " is a weekend.");
                break;
            default:
                System.out.println(day + " is a weekday.");
        }

        // -----------------------------------------------
        // What happens WITHOUT break? (fall-through demo)
        // -----------------------------------------------
        int x = 2;
        System.out.println("\n--- Fall-through (no break) ---");
        switch (x) {
            case 1: System.out.println("Case 1");  // prints this
            case 2: System.out.println("Case 2");  // also prints this!
            case 3: System.out.println("Case 3");  // and this!
        }
    }
}
