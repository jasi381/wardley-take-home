# S - Single Responsibility Principle (SRP)

## Definition
> "A class should have only **one reason to change**."
> — Robert C. Martin (Uncle Bob)

A class should have a **single, well-defined responsibility**. If a class handles multiple concerns, a change in one area can break another.

## Why SRP Matters

| Benefit | Explanation |
|---------|-------------|
| **Testing** | A class with one responsibility has fewer test cases |
| **Lower Coupling** | Less functionality = fewer dependencies |
| **Organization** | Smaller, well-organized classes are easier to search and understand |
| **Reduced Risk** | Changes to one concern don't accidentally break another |

## Before SRP (Bad)
```java
public class OrderService {
    public void placeOrder(Order order) { /* Order logic */ }
    public void calculateTotal(Order order) { /* Calculation logic */ }
    public void sendConfirmationEmail(Order order) { /* Email logic */ }
}
// 3 reasons to change: order rules, calculation rules, email provider
```

## After SRP (Good)
```java
public class OrderService {
    public void placeOrder(Order order) { /* Only order logic */ }
}

public class OrderCalculator {
    public double calculateTotal(Order order) { /* Only calculation */ }
}

public class EmailService {
    public void sendConfirmationEmail(Order order) { /* Only email */ }
}
// Each class changes for ONE reason only
```

## How to Identify SRP Violations
Ask yourself: **"What does this class do?"**
- If your answer uses the word **"AND"**, it probably violates SRP
- "This class manages employees **AND** sends emails **AND** generates reports" = violation!

## Real-World Analogy
> A chef cooks, a waiter serves, a cashier handles payments. You wouldn't want your chef to also handle billing — that's a recipe for disaster (pun intended).
