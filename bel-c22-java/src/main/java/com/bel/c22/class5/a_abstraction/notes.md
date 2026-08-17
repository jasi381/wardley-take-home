# Abstraction

## What is Abstraction?
Hiding the complex implementation details and showing only the essential features
of the object. Focuses on **what** an object does rather than **how** it does it.

## Why Abstraction?
- Simplifies complex systems by breaking them into smaller, manageable units.
- Enhances code readability and reduces complexity.
- Provides a clear separation between interface and implementation.

## Achieving Abstraction in Java
1. **Abstract Classes** (0-100% abstraction)
2. **Interfaces** (100% abstraction — covered in next section)

## Abstract Class
An abstract class **cannot be instantiated** on its own and is meant to be subclassed.

### Characteristics:
- Can have **both** abstract methods (no body) and concrete methods (with body).
- Can have **member variables** (instance variables).
- Can have **constructors** (called via `super()` from subclass).
- Can implement methods from an interface.
- **Cannot** be instantiated directly — `new AbstractClass()` is a compile error.

### Syntax:
```java
abstract class Shape {
    abstract double area();       // abstract — NO body, subclass MUST override
    void describe() {             // concrete — HAS a body, inherited as-is
        System.out.println("I am a shape.");
    }
}
```

## When to Use Abstract Classes?
- When you want to share **common code** among related classes.
- When subclasses share **some** behavior but differ in **specific** behavior.
- Example: All shapes have a color, but each shape calculates area differently.

## Example in this folder
- `AbstractionDemo.java` — Abstract class with abstract + concrete methods.
