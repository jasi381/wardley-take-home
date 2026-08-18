# Initialization Phase in JVM

## What is Initialization?
The **final step** of class loading. This is where:
1. Static variables get their **user-defined values**
2. Static blocks are **executed**

## Order of Execution
1. Static variable assignments (in order they appear)
2. Static blocks (in order they appear)
3. Superclass is initialized **before** subclass

## Example

```java
class Example {
    static int x = 10;           // Step 1: x = 10

    static {
        System.out.println("Static block 1");  // Step 2
        x = 20;                                 // Step 3: x = 20
    }

    static int y = x + 5;       // Step 4: y = 25

    static {
        System.out.println("Static block 2");  // Step 5
    }
}
```

## Key Rules
- Static blocks run **only once** — when the class is first loaded
- Order matters — top to bottom
- Superclass static blocks run before subclass
- Static blocks cannot access instance variables (no `this`)

## When Does a Class Get Initialized?
A class is initialized when:
- You create an instance with `new`
- You access a static field or method
- You use `Class.forName("ClassName")`
- A subclass is initialized (triggers parent first)
