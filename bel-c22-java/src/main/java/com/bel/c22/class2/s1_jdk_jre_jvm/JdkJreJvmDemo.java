package com.bel.c22.class2.s1_jdk_jre_jvm;

/**
 * JdkJreJvmDemo — Demonstrating the relationship between JDK, JRE, and JVM.
 *
 * JDK = JRE + Development Tools (javac, javadoc, jdb)
 * JRE = JVM + Core Libraries (java.lang, java.util)
 * JVM = The engine that executes bytecode
 */
public class JdkJreJvmDemo {

    public static void main(String[] args) {
        /*
        A -> B -> C
        D -> C

        C
        B D
        A
         */
        // --- JVM Info ---
        // The JVM is what runs this program right now
        String jvmName = System.getProperty("java.vm.name");
        String jvmVersion = System.getProperty("java.vm.version");
        System.out.println("=== JVM Info ===");
        System.out.println("JVM Name:    " + jvmName);
        System.out.println("JVM Version: " + jvmVersion);

        // --- JRE Info ---
        // The JRE provides the runtime environment
        String javaVersion = System.getProperty("java.version");
        String javaHome = System.getProperty("java.home");
        System.out.println("\n=== JRE Info ===");
        System.out.println("Java Version: " + javaVersion);
        System.out.println("Java Home:    " + javaHome);

        // --- JDK Info ---
        // The JDK includes the compiler (javac) and other dev tools
        String javaVendor = System.getProperty("java.vendor");
        String classVersion = System.getProperty("java.class.version");
        System.out.println("\n=== JDK Info ===");
        System.out.println("Vendor:        " + javaVendor);
        System.out.println("Class Version: " + classVersion);

        // --- Platform Independence ---
        String os = System.getProperty("os.name");
        String arch = System.getProperty("os.arch");
        System.out.println("\n=== Platform Independence ===");
        System.out.println("This same bytecode runs on: " + os + " (" + arch + ")");
        System.out.println("The JVM makes it platform-independent!");
    }
}
