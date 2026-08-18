package com.bel.c22.class2.s2_compilation_bytecode;

/**
 * CompilationDemo — Understanding the Java compilation process.
 *
 * Steps:
 *  1. javac CompilationDemo.java   →  compiles to bytecode (.class)
 *  2. java CompilationDemo          →  JVM runs the bytecode
 *  3. javap -c CompilationDemo      →  view the bytecode instructions
 *
 * Try running: javap -c CompilationDemo.class
 * to see what the bytecode looks like!
 */
public class CompilationDemo {

    // This simple method will help us see bytecode with javap
    public static int add(int a, int b) {
        return a + b;  // In bytecode: iload_0, iload_1, iadd, ireturn
    }

    public static String greet(String name) {
        return "Hello, " + name + "!";  // String concatenation in bytecode
    }

    public static void main(String[] args) {
        System.out.println("=== Java Compilation Demo ===");

        // Step 1: This .java file was compiled to bytecode by javac
        System.out.println("This .java file was compiled into a .class file.");
        System.out.println("The .class file contains bytecode (JVM instructions).");

        // Step 2: JVM is executing the bytecode right now
        int result = add(10, 20);
        System.out.println("\nadd(10, 20) = " + result);

        String greeting = greet("Student");
        System.out.println(greeting);

        // Step 3: You can inspect bytecode with javap
        System.out.println("\n--- Try this in terminal ---");
        System.out.println("javap -c CompilationDemo.class");
        System.out.println("You'll see JVM instructions like iload, iadd, areturn");
    }
}
