package com.bel.c22.class1.s2_datatypes;

/**
 * PrimitiveTypes — Demonstrates the 8 primitive data types in Java.
 *
 * Primitives hold values directly. They are NOT objects.
 * They CANNOT be null.
 *
 *
 * String s = "ABC";
 * s = "ABCD"
 * char c = 'A';
 *
 * ____________i=5_____________
 * ________variable(s)_________________
 * ___"ABCD"______________"ABCDDEJWnivbdvdsvbidvbpd"________
 *
 *
 *
 * -----------------------
 */
public class PrimitiveTypes {

    public static void main(String[] args) {

        // Integer types (whole numbers)
        byte  smallNum  = 100;          // -128 to 127
        short mediumNum = 30000;        // -32,768 to 32,767
        int   age       = 25;           // most common integer type
        long  bigNum    = 9_999_999_999L; // L suffix required for long literals

        // Floating point types (decimal numbers)
        float  price   = 9.99f;   // f suffix required for float literals
        double precise = 3.14159; // more precise; default for decimals

        // Character type (a single character)
        char grade = 'A'; // use single quotes for char

        // Boolean type (true or false only)
        boolean isJavaFun = true;

        // Print them all
        System.out.println("byte:    " + smallNum);
        System.out.println("short:   " + mediumNum);
        System.out.println("int:     " + age);
        System.out.println("long:    " + bigNum);
        System.out.println("float:   " + price);
        System.out.println("double:  " + precise);
        System.out.println("char:    " + grade);
        System.out.println("boolean: " + isJavaFun);
    }
}
