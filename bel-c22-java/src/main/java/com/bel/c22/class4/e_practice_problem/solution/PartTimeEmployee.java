package com.bel.c22.class4.e_practice_problem.solution;

public class PartTimeEmployee extends Employee {

    private int hoursWorked;
    private double hourlyRate;

    // Passes 0 as baseSalary to parent — pay is calculated differently
    public PartTimeEmployee(String name, int id, int hoursWorked, double hourlyRate) {
        super(name, id, 0);  // no base salary for part-time
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    // Method Overriding — runtime polymorphism
    @Override
    public double calculatePay() {
        return hoursWorked * hourlyRate;
    }
}
