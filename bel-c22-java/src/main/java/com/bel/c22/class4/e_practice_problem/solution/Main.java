package com.bel.c22.class4.e_practice_problem.solution;

public class Main {
    public static void main(String[] args) {

        // Create employees
        Employee fte = new FullTimeEmployee("Rahul", 101, 75000, 10000);
        Employee pte = new PartTimeEmployee("Priya", 102, 120, 100);

        // --- Runtime Polymorphism: Parent reference, child objects ---
        System.out.println("=== Employee Details (Polymorphism) ===");
        Employee[] employees = { fte, pte };

        // Same method call, different behavior — runtime polymorphism!
        for (Employee emp : employees) {
            emp.displayInfo();  // calls the correct calculatePay() at runtime
        }

        // --- Method Overloading: displayInfo(boolean) on FullTimeEmployee ---
        System.out.println("=== Full-Time with Bonus Details ===");
        //fte.displayInfo(true);   // overloaded version showing bonus breakdown
        //fte.displayInfo(false);  // overloaded version hiding bonus
    }
}
