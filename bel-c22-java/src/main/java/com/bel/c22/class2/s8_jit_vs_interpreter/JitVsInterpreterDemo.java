package com.bel.c22.class2.s8_jit_vs_interpreter;

/**
 * JitVsInterpreterDemo — Seeing JIT compilation in action.
 *
 * The JVM:
 *  1. Starts by INTERPRETING bytecode (line by line)
 *  2. Monitors "hot" methods (called frequently)
 *  3. JIT compiles hot methods to native code for speed
 *
 * Run with flags to see the difference:
 *   java -Xint  JitVsInterpreterDemo     → interpreter only
 *   java -Xcomp JitVsInterpreterDemo     → aggressive JIT
 *   java -XX:+PrintCompilation JitVsInterpreterDemo → see JIT activity
 */
public class JitVsInterpreterDemo {

    // A simple method that will become "hot" after many calls
    public static int square(int n) {
        return n * n;
    }

    public static void main(String[] args) {
        System.out.println("=== JIT vs Interpreter Demo ===\n");

        // Call square() many times — JIT will notice it's "hot"
        int iterations = 100_000;
        long startTime, endTime;

        // Warm up (interpreter phase)
        System.out.println("Running " + iterations + " iterations...");

        startTime = System.nanoTime();
        int result = 0;
        for (int i = 0; i < iterations; i++) {
            result = square(i);  // called many times → becomes hot!
        }
        endTime = System.nanoTime();

        System.out.println("Last result: " + result);
        System.out.println("Time: " + (endTime - startTime) / 1_000_000.0 + " ms");

        // Explanation
        System.out.println("\n--- What Happened ---");
        System.out.println("1. First few calls: Interpreter ran square() bytecode");
        System.out.println("2. After many calls: JIT compiled square() to native code");
        System.out.println("3. Subsequent calls: Used fast native code directly");

        System.out.println("\n--- Try These Flags ---");
        System.out.println("java -Xint  → Interpreter only (slower)");
        System.out.println("java -Xcomp → Force JIT compile everything");
        System.out.println("java -XX:+PrintCompilation → See JIT decisions");
    }
}
