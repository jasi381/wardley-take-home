package com.bel.c22.class1.s3_variables_scope;

/**
 * VariableScopeDemo — Shows the 3 types of variables by scope.
 *
 * 1. Static variable  — belongs to the class, shared by all objects
 * 2. Instance variable — belongs to each individual object
 * 3. Local variable   — only exists inside the method/block it's declared in
 */
/*
______


________
_______

_______
 */
public class VariableScopeDemo {
/*
---------------
---------------
---------------
---------------
---------------
3 variables
obj1, obj2, obj3



1 variable - class variable
 */
    // --- STATIC variable ---
    // Shared across ALL instances. Only one copy in memory.
     static int totalStudents = 0;

    // --- INSTANCE variable ---
    // Each object gets its own copy. Default value is 0 / null / false.
    String studentName;
    int    xp;

    // Constructor to set up each student object
    public VariableScopeDemo(String name, int xp) {
        this.studentName = name;  // 'this' refers to the current object
        this.xp = xp;
        totalStudents++;// update the shared counter
    }

    void printInfo() {
        // static int totalStudent = 1;
        // --- LOCAL variable ---
        // Only exists inside this method. Must be initialized before use.

        String label;
        label = "Student";// local to printInfo()
        //String studentName = "";

        System.out.println(label + ": " + studentName + " | XP: " + xp);
    }

    public static void main(String[] args) {

        VariableScopeDemo s1 = new VariableScopeDemo("Alice", 120);
        VariableScopeDemo s2 = new VariableScopeDemo("Bob", 95);
        s1.printInfo();
        s2.printInfo();
        String studentName;

        int x =5;

        // Static variable accessed via class name (best practice)
        System.out.println("Total students: " + VariableScopeDemo.totalStudents);

        // Each object has its OWN instance variables
        System.out.println("s1 name: " + s1.studentName);
        System.out.println("s2 name: " + s2.studentName);
        System.out.println("s2 total students: " + s2.totalStudents);
        s2.totalStudents = 10;
        System.out.println("Total students: " + s1.totalStudents);

        //x++;
    }
}
