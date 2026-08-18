# Polymorphism

## What is Polymorphism?
"Many forms" — the ability of a method, object, or variable to take multiple forms.

## Two Types

### 1. Compile-Time Polymorphism (Static / Early Binding)
- Achieved via **Method Overloading** and **Constructor Overloading**.
- The compiler decides which method to call based on the **method signature**
  (name + number/type/order of parameters).
- Resolved at **compile time** — faster performance.

### 2. Runtime Polymorphism (Dynamic / Late Binding)
- Achieved via **Method Overriding**.
- The JVM decides which method to call based on the **actual object type** at runtime.
- Requires **inheritance** and a parent reference pointing to a child object.
- Resolved at **runtime** — more flexible.

## Compile-Time vs Runtime

| Feature | Compile-Time | Runtime |
|---------|-------------|---------|
| Achieved By | Method Overloading | Method Overriding |
| Resolution | Compile time | Runtime |
| Binding | Early Binding | Late Binding |
| Method Signature | Same name, different params | Same name AND same params |
| Inheritance Required | No | Yes |
| Performance | Faster | Slightly slower |

## Method Overload Resolution
When multiple overloaded methods exist, the compiler picks using:
1. **Exact Match** — looks for exact parameter types first.
2. **Type Promotion** — if no exact match, promotes smaller types
   (byte -> short -> int -> long -> float -> double).
3. **Ambiguity Error** — if multiple methods match after promotion.

## Example in this folder
- `PolymorphismDemo.java` - Method overriding (runtime polymorphism) with parent references.
- `OverloadingDemo.java` - Method and constructor overloading (compile-time polymorphism).
