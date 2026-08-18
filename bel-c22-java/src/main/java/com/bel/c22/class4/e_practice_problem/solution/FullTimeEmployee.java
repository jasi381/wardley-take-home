package com.bel.c22.class4.e_practice_problem.solution;

public class FullTimeEmployee extends Employee {

    private double bonus;

    // Uses super() to call parent constructor
    public FullTimeEmployee(String name, int id, double baseSalary, double bonus) {
        super(name, id, baseSalary);  // call Employee constructor
        this.bonus = bonus;
    }

    // Method Overriding — runtime polymorphism
    @Override
    public double calculatePay() {
        return getBaseSalary() + bonus;
    }

    // Method Overloading — compile-time polymorphism
    // Overloaded version with a flag to show/hide bonus
    public void displayInfo(boolean showBonus) {
        System.out.println("Name: " + getName() + " | ID: " + getId());
        System.out.println("Total Pay: " + calculatePay());
        if (showBonus) {
            System.out.println("(Base: " + getBaseSalary() + " + Bonus: " + bonus + ")");
        }
        System.out.println();
    }
}
