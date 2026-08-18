package com.bel.c22.class2.s5_linking_phase;

/**
 * LinkingPhaseDemo — Understanding Verification, Preparation, and Resolution.
 *
 * After a class is loaded, the JVM performs LINKING in 3 steps:
 *  1. Verification  → is the .class file valid?
 *  2. Preparation   → allocate memory for static vars, set defaults
 *  3. Resolution    → convert symbolic refs to actual memory addresses
 */
public class LinkingPhaseDemo {

    // --- Preparation Phase Demo ---
    // During Preparation: these get DEFAULT values (0, null, false)
    // During Initialization: they get the values you assigned
    static int studentCount = 50;
    // Prep: 0, then Init: 50
    static String batchName = "C18";    // Prep: null, then Init: "C18"
    static boolean isActive = true;     // Prep: false, then Init: true

    public static void main(String[] args) {
        System.out.println("=== Linking Phase Demo ===\n");
        int c = studentCount / 0;
        // After linking + initialization, we see user-defined values
        System.out.println("--- After Preparation + Initialization ---");
        System.out.println("studentCount = " + studentCount);  // 50 (not 0)
        System.out.println("batchName    = " + batchName);     // C18 (not null)
        System.out.println("isActive     = " + isActive);      // true (not false)

        // --- Verification Demo ---
        // The JVM verified this .class file before running it
        // If verification fails, you get a VerifyError
        System.out.println("\n--- Verification ---");
        System.out.println("This class passed bytecode verification!");
        System.out.println("Invalid .class files would throw VerifyError.");

        // --- Resolution Demo ---
        // When we reference System.out, the JVM resolves the symbolic
        // reference "java/lang/System" to its actual memory address
        System.out.println("\n--- Resolution ---");
        System.out.println("System.class resolved to: " + System.class);
        System.out.println("String.class resolved to: " + String.class);
    }
}
