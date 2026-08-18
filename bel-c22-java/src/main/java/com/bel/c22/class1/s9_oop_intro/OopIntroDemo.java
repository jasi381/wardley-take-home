package com.bel.c22.class1.s9_oop_intro;

/**
 * OopIntroDemo — A gentle introduction to Classes and Objects.
 *
 * Class  = Blueprint / Template (like a cookie cutter)
 * Object = An actual instance made from that blueprint (the cookie)
 *
 * This demo shows the basics before we dive into
 * Encapsulation, Inheritance, Polymorphism, Abstraction in future sessions.
 */
public class OopIntroDemo {

    // -----------------------------------------------
    // Define a Class — this is the "blueprint"
    // -----------------------------------------------
    static class Student {

        // Fields (state) — what a student HAS
        String name;
        int    age;
        int    xp;

        // Constructor — runs when we create a new Student object
        Student(String name, int age, int xp) {
            this.name = name;
            this.age  = age;
            this.xp   = xp;
        }

        // Method (behaviour) — what a student CAN DO
        void introduce() {
            System.out.println("Hi! I'm " + name + ", age " + age + ", XP: " + xp);
        }

        // Method that returns a value
        boolean isTopPerformer() {
            return xp >= 100;  // top performer if xp >= 100
        }
    }

    // -----------------------------------------------
    // Main — create objects from the blueprint
    // -----------------------------------------------
    public static void main(String[] args) {

        // Each 'new Student(...)' creates a separate object in memory
        Student s1 = new Student("Alice", 22, 150);
        Student s2 = new Student("Bob",   19, 80);
        Student s3 = new Student("Carol", 21, 110);

        // Each object has its own data but shares the same methods
        s1.introduce();
        s2.introduce();
        s3.introduce();

        System.out.println();

        // Call the method that returns a boolean
        System.out.println(s1.name + " top performer? " + s1.isTopPerformer()); // true
        System.out.println(s2.name + " top performer? " + s2.isTopPerformer()); // false
        System.out.println(s3.name + " top performer? " + s3.isTopPerformer()); // true
    }
}
