package com.bel.c22.class6.c_srp;

/**
 * S - Single Responsibility Principle (SRP)
 *
 * "A class should have only ONE reason to change."
 *
 * Each class should do ONE thing and do it well.
 * If a class handles orders AND sends emails AND generates reports,
 * a change in email logic could accidentally break order processing!
 */
public class SrpDemo {

    // =====================================================
    // BEFORE SRP (Bad) - Employee class does EVERYTHING
    // =====================================================
    static class EmployeeBad {
        private String name;
        private double salary;
        // Database database;
        // Cache cache;
        // ITRules

        EmployeeBad(String name, double salary) {
            this.name = name;
            this.salary = salary;
        }

        public double calculatePay() {
            return salary - (salary * 0.1); // 10% tax deduction
        }

        public void saveToDatabase() {
            System.out.println("Saving " + name + " to database...");
        }

        public void generatePaySlip() {
            System.out.println("=== Pay Slip for " + name + " ===");
            System.out.println("Gross: Rs." + salary);
            System.out.println("Net: Rs." + calculatePay());
        }
















        // Problem: 3 reasons to change!
        // 1. Tax rules change → modify calculatePay
        // 2. Database changes → modify saveToDatabase
        // 3. Pay slip format changes → modify generatePaySlip
        // Any change risks breaking the other two!
    }

    // =====================================================
    // AFTER SRP (Good) - Each class has ONE responsibility
    // =====================================================

    // Class 1: Only holds employee data
    static class Employee {
        private String name;
        private double salary;

        Employee(String name, double salary) {
            this.name = name;
            this.salary = salary;
        }

        public String getName() { return name; }
        public double getSalary() { return salary; }
    }

    // Class 2: Only calculates pay
    static class PayCalculator {
        public double calculatePay(Employee emp) {
            return emp.getSalary() - (emp.getSalary() * 0.1);
        }
    }

    // Class 3: Only handles database operations
    static class EmployeeRepository {
        public void save(Employee emp) {
            System.out.println("Saving " + emp.getName() + " to database...");
        }
    }

    // Class 4: Only generates pay slips
    static class PaySlipGenerator {
        private PayCalculator calculator = new PayCalculator();

        public void generate(Employee emp) {
            System.out.println("=== Pay Slip for " + emp.getName() + " ===");
            System.out.println("Gross: Rs." + emp.getSalary());
            System.out.println("Net: Rs." + calculator.calculatePay(emp));
        }
    }

    public static void main(String[] args) {
        System.out.println("=== BEFORE SRP (one bloated class) ===");
        EmployeeBad bad = new EmployeeBad("Priya", 50000);
        bad.calculatePay();
        bad.saveToDatabase();
        bad.generatePaySlip();

        System.out.println("\n=== AFTER SRP (each class = one job) ===");
        Employee emp = new Employee("Priya", 50000);

        PayCalculator calculator = new PayCalculator();
        System.out.println("Net pay: Rs." + calculator.calculatePay(emp));

        EmployeeRepository repo = new EmployeeRepository();
        repo.save(emp);

        PaySlipGenerator generator = new PaySlipGenerator();
        generator.generate(emp);

        System.out.println("\n--- Benefits of SRP ---");
        System.out.println("1. Testing: Fewer test cases per class");
        System.out.println("2. Lower coupling: Fewer dependencies");
        System.out.println("3. Organization: Small classes are easier to find & read");
    }
}
