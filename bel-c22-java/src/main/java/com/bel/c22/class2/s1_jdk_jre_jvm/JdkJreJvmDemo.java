package com.bel.c22.class2.s1_jdk_jre_jvm;

/**
 * JDK, JRE and JVM
 *
 * JDK (develop) contains the JRE (run), which contains the JVM (execute).
 * This demo doesn't "show" the JDK/JRE/JVM directly (they're tooling, not
 * classes) - instead it inspects the JVM your code is actually running on,
 * using values that only exist because a JRE/JVM is present.
 */
public class JdkJreJvmDemo {

    public static void main(String[] args) {
        System.out.println("=== What the running JVM tells us about itself ===");
        System.out.println("JVM name:      " + System.getProperty("java.vm.name"));
        System.out.println("Java version:  " + System.getProperty("java.version"));
        System.out.println("Java vendor:   " + System.getProperty("java.vendor"));
        System.out.println("OS:            " + System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ")");

        System.out.println("\n=== Why you can't tell from here if a JDK is installed ===");
        System.out.println("This class was already compiled to bytecode (.class) before it ran.");
        System.out.println("Compiling needed 'javac', which only ships in a JDK.");
        System.out.println("Running it only needs the JVM + core libraries, i.e. a JRE.");
        System.out.println("That's the whole point: build-time needs the JDK, run-time only needs the JRE.");

        System.out.println("\n=== Memory managed by the JVM (garbage collection) ===");
        Runtime runtime = Runtime.getRuntime();
        System.out.println("Max heap the JVM will use:   " + (runtime.maxMemory() / (1024 * 1024)) + " MB");
        System.out.println("Heap currently allocated:    " + (runtime.totalMemory() / (1024 * 1024)) + " MB");
        System.out.println("Free within allocated heap:  " + (runtime.freeMemory() / (1024 * 1024)) + " MB");
        System.out.println("You never freed any of this by hand - the JVM's garbage collector does it.");
    }
}
