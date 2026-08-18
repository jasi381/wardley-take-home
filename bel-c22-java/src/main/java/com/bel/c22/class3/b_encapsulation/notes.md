# Encapsulation

## What is Encapsulation?
Bundling of data (variables) and methods (functions) that operate on the data
into a single unit called a **class**.

## Key Points
- Protects the internal state of an object from unintended modifications.
- Access to data is restricted through **public methods** (getters and setters).
- Ensures **data integrity** and security.
- Increases code **maintainability** and flexibility.

## How to Achieve Encapsulation
1. Declare class variables as `private`.
2. Provide public `getter` methods to read the values.
3. Provide public `setter` methods to modify the values (with validation if needed).

## Why Not Just Make Everything Public?
Without encapsulation, anyone can set invalid data:
```java
student.age = -5;       // No validation! Bad data gets in.
student.balance = 999999; // Anyone can change the balance directly!
```

With encapsulation, setters can validate:
```java
public void setAge(int age) {
    if (age > 0 && age < 150) {
        this.age = age;
    }
}
```

## Example in this folder
`BankAccount.java` - A class with private fields and public getters/setters with validation.
`BankAccountDemo.java` - Demonstrates how encapsulation protects data.
