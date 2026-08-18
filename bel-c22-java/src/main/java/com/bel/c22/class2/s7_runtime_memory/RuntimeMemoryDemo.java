package com.bel.c22.class2.s7_runtime_memory;

/**
 * RuntimeMemoryDemo — Where do things live in JVM memory?
 *
 * Method Area → class info, static variables
 * Heap        → objects, instance variables
 * Stack       → local variables, method call frames
 * PC Register → current instruction (per thread)
 *
 * T1 -> M1 -> M2
 * T1 -> f1 -> f1 -> f1 ->
 *
 * f1 () {
 *     Print(5);
 *     f1();
 * }
 *
 *
 *
 *
 *
 *
 * t1 -> u1
 * t2 -> u2
 * t3 -> u3
 * t1
 * // createUser() {
 *     f1();
 *     --
 * }
 *
 * // getUser() {
 *
 * }
 *
 * f1 () {
 *     --
 *     f2()
 *     --
 *     --
 * }
 *
 * f2() {
 *     --
 *     f3()
 *     --
 * }
 * t1 -> createUser -> f1 -> f2
 * f3() {
 *     --
 *     --
 *     --
 *     --
 * }
 */
public class RuntimeMemoryDemo {

    // Static variable → Method Area
    static String className = "Backend Engineering";

    // Instance variable → Heap (part of the object)
    String studentName;
    int xp;

    public RuntimeMemoryDemo(String name, int xp) {
        this.studentName = name;
        this.xp = xp;
    }

    // Each call to this method creates a frame on the Java Stack
    public void displayInfo() {
        // local variable → lives on the Stack
        // t1
        String label = "Learner Info";
        //infiniteRecursion(5);
        System.out.println(label + ": " + studentName + " (XP: " + xp + ")");
    }

    // Demonstrate StackOverflowError with infinite recursion
    public static void infiniteRecursion(int depth) {
        System.out.println("Depth: " + depth);
        infiniteRecursion(depth + 1);  // never stops → StackOverflow!
    }

    public static void main(String[] args) {
        System.out.println("=== Runtime Memory Demo ===\n");

        // Method Area — static variable
        System.out.println("[Method Area] className = " + className);

        // Heap — creating objects
        RuntimeMemoryDemo s1 = new RuntimeMemoryDemo("Alice", 100);
        RuntimeMemoryDemo s2 = new RuntimeMemoryDemo("Bob", 200);
        System.out.println("[Heap] Created 2 student objects\n");

        // Stack — method calls push/pop frames
        System.out.println("[Stack] Calling displayInfo() — pushes a frame:");
        // t1
        s1.displayInfo();
        // ,main
        // ------
        s2.displayInfo();
        System.out.println("[Stack] displayInfo() returned — frame popped\n");

        // StackOverflowError demo
        System.out.println("[Stack] StackOverflowError demo:");
        try {
            infiniteRecursion(1);
        } catch (StackOverflowError e) {
            System.out.println("Caught StackOverflowError! Too many frames.");
        }
    }
}
