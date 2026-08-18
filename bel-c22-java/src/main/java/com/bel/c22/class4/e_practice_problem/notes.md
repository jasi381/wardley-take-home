# Practice Problem: Employee Payroll System

## Problem Statement
Design a simple Employee payroll system using **inheritance**, **constructor chaining with super**,
**method overriding**, and **method overloading**.

### Requirements:

1. Create a **base class** `Employee` with:
   - `private` fields: `name` (String), `id` (int), `baseSalary` (double)
   - A constructor that takes all 3 fields
   - A method `calculatePay()` that returns the `baseSalary`
   - A method `displayInfo()` that prints name, id, and calculated pay

2. Create a subclass `FullTimeEmployee extends Employee` with:
   - An extra field: `bonus` (double)
   - A constructor that takes name, id, baseSalary, and bonus (use `super()` to call parent constructor)
   - **Override** `calculatePay()` to return `baseSalary + bonus`

3. Create a subclass `PartTimeEmployee extends Employee` with:
   - Extra fields: `hoursWorked` (int), `hourlyRate` (double)
   - A constructor that takes name, id, hoursWorked, and hourlyRate (pass 0 as baseSalary to super)
   - **Override** `calculatePay()` to return `hoursWorked * hourlyRate`

4. In `FullTimeEmployee`, **overload** `displayInfo()`:
   - `displayInfo()` — no args, prints everything including bonus
   - `displayInfo(boolean showBonus)` — if false, hides the bonus line

5. In a `Main` class:
   - Create one `FullTimeEmployee` and one `PartTimeEmployee`
   - Store both in an `Employee[]` array
   - Loop through the array and call `displayInfo()` — observe polymorphism!

### Expected Output:
```
=== Employee Details ===
Name: Rahul | ID: 101
Total Pay: 85000.0

Name: Priya | ID: 102
Total Pay: 12000.0
```

## Solution
See the `solution/` folder after you have attempted the problem.
