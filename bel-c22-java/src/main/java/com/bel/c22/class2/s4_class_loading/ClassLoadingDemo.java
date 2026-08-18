package com.bel.c22.class2.s4_class_loading;

/**
 * ClassLoadingDemo — Exploring the three JVM class loaders.
 *
 * Class Loaders (parent delegation order):
 *  1. Bootstrap ClassLoader — loads core Java (java.lang.*)
 *  2. Platform/Extension ClassLoader — loads platform extensions
 *  3. Application ClassLoader — loads YOUR classes from classpath
 */
public class ClassLoadingDemo {

    public static void main(String[] args) {
        System.out.println("=== Class Loader Demo ===\n");

        // 1. Bootstrap ClassLoader — loads core classes
        // Returns null because it's written in native C code
        ClassLoader stringLoader = String.class.getClassLoader();
        System.out.println("String loaded by: " + stringLoader);  // null (Bootstrap)

        // 2. Application ClassLoader — loads our custom classes
        ClassLoader myLoader = ClassLoadingDemo.class.getClassLoader();
        System.out.println("ClassLoadingDemo loaded by: " + myLoader);

        // 3. Parent delegation chain
        System.out.println("\n--- Parent Delegation Chain ---");
        ClassLoader current = ClassLoadingDemo.class.getClassLoader();
        while (current != null) {
            System.out.println("  → " + current);
            current = current.getParent();
        }
        System.out.println("  → null (Bootstrap ClassLoader - native code)");

        // 4. Demonstrating ClassNotFoundException
        System.out.println("\n--- ClassNotFoundException Demo ---");
        try {
            Class.forName("com.nonexistent.FakeClass");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not find class: " + e.getMessage());
            System.out.println("All 3 class loaders failed to find it!");
        }
    }
}
