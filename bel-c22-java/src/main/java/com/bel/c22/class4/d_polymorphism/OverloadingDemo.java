package com.bel.c22.class4.d_polymorphism;

public class OverloadingDemo {

    // ==================== METHOD OVERLOADING ====================
    // Same method name, different parameter lists
    // Compiler decides which to call at COMPILE TIME

    static class Calculator {

        // add with 2 ints
        int add(int a, int b) {
            System.out.println("add(int, int) called");
            return a + b;
        }

//        int add(int a, int b) {
//            //System.out.println("add(int, int) called");
//            return a + b;
//        }

        // add with 3 ints — different NUMBER of params
        int add(int a, int b, int c) {
            System.out.println("add(int, int, int) called");
            return a + b + c;
        }

        // add with 2 doubles — different TYPE of params
//        double add(double a, double b) {
//            System.out.println("add(double, double) called");
//            return a + b;
//        }

        // add with different ORDER of params
        String add(String text, int number) {
            System.out.println("add(String, int) called");
            return text + number;
        }

        String add(int number, String text) {
            System.out.println("add(int, String) called");
            return number + text;
        }

        // NOTE: Changing ONLY return type is NOT overloading — it causes compile error!
        // int add(int a, int b) { ... }  // ERROR: already defined
    }

    // ==================== CONSTRUCTOR OVERLOADING ====================

    static class Employee {
        String name;
        int age;
        double salary;

        // No-arg constructor
        Employee() {
            this.name = "Unknown";
            this.age = 0;
            this.salary = 0;
            System.out.println("No-arg constructor called");
        }

        // 1-arg constructor
        Employee(String name) {
            this.name = name;
            this.age = 0;
            this.salary = 0;
            System.out.println("1-arg constructor called");
        }

        // All-arg constructor
        Employee(String name, int age, double salary) {
            this.name = name;
            this.age = age;
            this.salary = salary;
            System.out.println("All-arg constructor called");
        }

        void display() {
            System.out.println("  " + name + " | Age: " + age + " | Salary: " + salary);
        }
    }

    // ==================== TYPE PROMOTION ====================
    // When no exact match, Java promotes smaller types automatically
    // byte -> short -> int -> long -> float -> double

    static class TypePromotion {
        void show(int a) {
            System.out.println("show(int): " + a);
        }

        void show(double a) {
            System.out.println("show(double): " + a);
        }
    }

    // ==================== MAIN ====================
    public static void main(String[] args) {

        // --- Method Overloading ---
        System.out.println("=== Method Overloading ===");
        Calculator calc = new Calculator();
        System.out.println("Result: " + calc.add(5, 3));
        System.out.println("Result: " + calc.add(5, 3, 2));
        System.out.println("Result: " + calc.add((int)5.5, (int)3.2));
        System.out.println("Result: " + calc.add("Score: ", 100));

        // --- Constructor Overloading ---
        System.out.println("\n=== Constructor Overloading ===");
        Employee e1 = new Employee();
        e1.display();

        Employee e2 = new Employee("Rahul");
        e2.display();

        Employee e3 = new Employee("Priya", 25, 75000);
        e3.display();

        // --- Type Promotion ---
        System.out.println("\n=== Type Promotion in Overloading ===");
        TypePromotion tp = new TypePromotion();
        tp.show(10);       // exact match: show(int)
        tp.show(10.5);     // exact match: show(double)

        byte b = 5;
        tp.show(b);        // no show(byte) — promoted to int -> show(int)

        float f = 3.14f;
        tp.show(f);        // no show(float) — promoted to double -> show(double)
    }
}
