package com.bel.c22.class2.s3_jvm_architecture;

/**
 * JvmArchitectureDemo — Exploring JVM components at runtime.
 *
 * JVM has 4 main parts:
 *  1. Class Loader Subsystem — loads .class files
 *  2. Runtime Memory Areas   — heap, stack, method area, etc.
 *  3. Execution Engine        — interpreter + JIT + GC
 *  4. Native Interface (JNI)  — bridge to C/C++ code
 */
public class JvmArchitectureDemo {

    // Static variable → stored in Method Area
    static String courseName = "Backend Engineering Launchpad";

    // Instance variable → stored in Heap (when object is created)
    String studentName;

    public JvmArchitectureDemo(String name) {
        this.studentName = name;
    }

    public void study() {
        // Local variable → stored in Java Stack
        String topic = "JVM Architecture";
        System.out.println(studentName + " is studying: " + topic);
    }

    public static void main(String[] args) {
        System.out.println("=== JVM Architecture Demo ===\n");

        // 1. Method Area — stores class metadata, static vars
        System.out.println("[Method Area] Static var courseName = " + courseName);

        // 2. Heap — objects live here
        JvmArchitectureDemo s1 = new JvmArchitectureDemo("Alice");
        JvmArchitectureDemo s2 = new JvmArchitectureDemo("Bob");
        System.out.println("[Heap] Created 2 objects: s1 and s2");

        // 3. Java Stack — each method call creates a frame on the stack
        System.out.println("[Stack] main() frame is on the stack");
        s1.study();  // study() frame pushed, then popped
        s2.study();

        // 4. Garbage Collection — part of Execution Engine
        s1 = null;  // s1 object is now eligible for GC
        System.out.println("\n[GC] s1 set to null — eligible for garbage collection");

        // 5. Runtime info
        Runtime runtime = Runtime.getRuntime();
        System.out.println("\n[Runtime Memory]");
        System.out.println("Max Memory:   " + runtime.maxMemory() / 1024 / 1024 + " MB");
        System.out.println("Total Memory: " + runtime.totalMemory() / 1024 / 1024 + " MB");
        System.out.println("Free Memory:  " + runtime.freeMemory() / 1024 / 1024 + " MB");
    }
}
