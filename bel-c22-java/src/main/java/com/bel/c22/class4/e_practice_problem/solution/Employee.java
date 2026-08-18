package com.bel.c22.class4.e_practice_problem.solution;

public class Employee {

    private String name;
    private int id;
    private double baseSalary;

    public Employee(String name, int id, double baseSalary) {
        this.name = name;
        this.id = id;
        this.baseSalary = baseSalary;
    }

    // Getters
    public String getName() { return name; }
    public int getId() { return id; }
    public double getBaseSalary() { return baseSalary; }

    // Will be overridden by subclasses
    public double calculatePay() {
        return baseSalary;
    }

    public void displayInfo() {
        System.out.println("Name: " + name + " | ID: " + id);
        System.out.println("Total Pay: " + calculatePay());
        System.out.println();
    }
}
