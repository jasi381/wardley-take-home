package com.bel.c22.class1.s2_datatypes;

/**
 * NonPrimitiveTypes — Demonstrates reference/non-primitive data types.
 *
 * Non-primitives store a reference (address) to the actual data.
 * They CAN be null.
 * Examples: String, Arrays, and any class you create.
 * 32 bits
 * ______________ (1 sign, 31 bits)
 *
 * ____(ref name)
 *
 * _________|_________|(Airtribe)
 *
 * |___________(AirtribeX)
 *
 *
 * User u1  = new User("ABC");
 * func1 (User u) {
 *    u - memory address
 *     User u2 = u;
 *     u2 = same memory address of u
 *     u2.name = "XYZ";
 *     Print(u.name) --- XYZ
 * }
 *
 * func2 () {
 *      User u1  = new User("ABC");
 *      Print(u1.name) --- ABC
 *     func1(u1);
        Print(u1.name) --- XYZ
 * }
 */
public class NonPrimitiveTypes {

    public static void main(String[] args) {

        String name1;
        int i;
        Integer i1 = 4;
        // String — a sequence of characters (most used non-primitive)
        String name    = "Airtribe";
        String city    = null;  // non-primitives can be null; primitives cannot

        System.out.println("Name: " + name);
        System.out.println("City: " + city);  // prints: City: null

        // Array — a fixed-size collection of the same type
        int[]    scores  = {95, 88, 72, 100};  // int array
        String[] fruits  = {"Apple", "Mango", "Banana"};

        // Access elements using index (starts at 0)
        System.out.println("First score: " + scores[0]);   // 95
        System.out.println("Last fruit:  " + fruits[2]);   // Banana

        // Array length property
        System.out.println("Number of scores: " + scores.length);

        // Strings have useful built-in methods
        System.out.println("Uppercase: " + name.toUpperCase());
        System.out.println("Length:    " + name.length());
    }
}
