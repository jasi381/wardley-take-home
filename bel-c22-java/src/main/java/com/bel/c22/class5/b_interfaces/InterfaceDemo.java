package com.bel.c22.class5.b_interfaces;


/*
interface A {

   void abc();
}

interface B {

   int abc();
}

class C implements A,B {

    abc() {
     //
    }
}
 */
public class InterfaceDemo {

    // ==================== BASIC INTERFACE ====================
    // Defines a contract — implementing class MUST provide the body

    interface Drawable {
         void draw();    // abstract by default (no body)
         double area();  // abstract by default
    }

    // Class implementing the interface — MUST override all abstract methods
    static class Circle implements Drawable {
        double radius;

        Circle(double radius) {
            this.radius = radius;
        }

        @Override
        public void draw() {
            System.out.println("Drawing a circle with radius " + radius);
        }

        @Override
        public double area() {
            return Math.PI * radius * radius;
        }
    }

    // ==================== MULTIPLE INTERFACES ====================
    // A class can implement multiple interfaces — solves the diamond problem!

    interface Flyable {
        void fly();
    }

    interface Swimmable {
        void swim();
        void fly();
    }

    static class Bird {
       public void fly() {
            System.out.println("I can fly");
        }
    }

    // Duck implements BOTH interfaces — multiple inheritance via interfaces
    static class Duck extends Bird implements Flyable, Swimmable {
        String name;

        Duck(String name) {
            this.name = name;
        }

//        @Override
//        public void fly() {
//            System.out.println(name + " is flying!");
//        }

        @Override
        public void swim() {
            System.out.println(name + " is swimming!");
        }
    }

    // ==================== DEFAULT AND STATIC METHODS (Java 8+) ====================

    interface Logger {
        void log(String message);  // abstract — must be implemented

        // Default method — has a body, can be inherited or overridden
        default void logInfo(String message) {
            System.out.println("[INFO] " + message);
        }

        // Static method — belongs to the interface, not the implementing class
        static void logError(String message) {
            System.out.println("[ERROR] " + message);
        }
    }

    static class ConsoleLogger implements Logger {
        @Override
        public void log(String message) {
            System.out.println("[LOG] " + message);
        }
        // logInfo() is inherited from Logger (default method)
        // logError() is NOT inherited — it's a static method on Logger
    }

    // ==================== INTERFACE VARIABLES ====================
    // Interface variables are always public, static, and final

    interface AppConfig {
        String APP_NAME = "MyApp";  // implicitly public static final
        int MAX_USERS = 100;        // implicitly public static final
    }

    // ==================== MAIN ====================
    public static void main(String[] args) {

        // --- Basic Interface ---
//        System.out.println("=== Basic Interface ===");
//        Drawable circle = new Circle(5);   // interface reference, class object
//        circle.draw();
//        System.out.println("Area: " + circle.area());

        // --- Multiple Interfaces ---
        System.out.println("\n=== Multiple Interfaces ===");
        Duck duck = new Duck("Donald");
        duck.fly();
        duck.swim();

        // --- Default and Static Methods ---
//        System.out.println("\n=== Default & Static Methods ===");
//        ConsoleLogger logger = new ConsoleLogger();
//        logger.log("Application started");       // implemented method
//        logger.logInfo("User logged in");         // inherited default method
//        Logger.logError("Something went wrong");  // static — called on interface

        // --- Interface Variables ---
//        System.out.println("\n=== Interface Variables ===");
//        System.out.println("App: " + AppConfig.APP_NAME);
//        System.out.println("Max Users: " + AppConfig.MAX_USERS);
    }
}
