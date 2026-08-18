package com.bel.c22.class2.s10_jvm_tools;

/**
 * JvmToolsDemo — A class to practice using JVM inspection tools.
 *
 * After compiling, try these commands:
 *
 *  1. javap -c JvmToolsDemo.class
 *     → See the bytecode instructions for each method
 *
 *  2. java -verbose:class com.bel.c22.class2.s10_jvm_tools.JvmToolsDemo
 *     → Watch which classes the JVM loads
 *
 *  3. While the program runs (it pauses), open another terminal:
 *     jps -l           → find the PID
 *     jcmd <pid> VM.flags   → see JVM flags
 */
public class JvmToolsDemo {

    static int counter = 0;

    // Simple method — look at its bytecode with javap -c
    public static int multiply(int a, int b) {
        return a * b;
    }

    // Method with a loop — more interesting bytecode
    public static int sumUpTo(int n) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println("=== JVM Tools Demo ===\n");

        System.out.println("multiply(6, 7) = " + multiply(6, 7));
        System.out.println("sumUpTo(100)   = " + sumUpTo(100));

        // Print some JVM info
        System.out.println("\n--- JVM Details ---");
        System.out.println("JVM: " + System.getProperty("java.vm.name"));
        System.out.println("Version: " + System.getProperty("java.version"));
        System.out.println("PID: " + ProcessHandle.current().pid());

        System.out.println("\n--- Try These Commands ---");
        System.out.println("javap -c JvmToolsDemo.class");
        System.out.println("java -verbose:class com.bel.c22.class2.s10_jvm_tools.JvmToolsDemo");
        System.out.println("jps -l");
        System.out.println("jcmd " + ProcessHandle.current().pid() + " VM.flags");
    }
}
