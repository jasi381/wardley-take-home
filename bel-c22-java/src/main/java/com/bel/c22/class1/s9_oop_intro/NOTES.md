# Introduction to Object-Oriented Programming (OOP)

## What is OOP?
A programming style where you model real-world things as **objects**.
- Objects have **state** (fields/variables) and **behaviour** (methods)
- A **class** is the blueprint; an **object** is the instance

## The 4 Pillars of OOP

### 1. Encapsulation
Bundling data + methods together, and hiding internal details.
- Use `private` fields + `public` getters/setters
- Example: ATM machine — you interact with buttons, not internal circuits

### 2. Inheritance
A class can inherit fields and methods from another class.
- Promotes code reuse
- `extends` keyword
- Example: `Dog extends Animal` — Dog inherits eat(), sleep()

### 3. Polymorphism
Same method name, different behaviour depending on the object.
- Example: `shape.draw()` — draws a circle for Circle, square for Square

### 4. Abstraction
Hiding implementation details and showing only what's necessary.
- Use `abstract` classes or `interface`
- Example: You drive a car without knowing how the engine works internally

> These 4 pillars will be covered in depth in upcoming sessions.
> This session introduces the concept; upcoming classes go deep.
