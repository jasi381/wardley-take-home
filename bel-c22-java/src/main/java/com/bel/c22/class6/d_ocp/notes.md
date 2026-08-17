# O - Open/Closed Principle (OCP)

## Definition
> "Software entities (classes, modules) should be **open for extension** but **closed for modification**."

Add new features by **writing new code**, not by **changing existing code**.

## Why OCP Matters
| Benefit | Explanation |
|---------|-------------|
| **Non-Invasive Changes** | Add new features without modifying existing code |
| **Reduced Risk** | Minimizes introducing bugs in working code |
| **Plug and Play** | New functionality = new class implementing existing interface |

## Before OCP (Bad) - The `if-else` anti-pattern
```java
public class Shape {
    private String type;
    public void draw() {
        if (type.equals("circle")) { drawCircle(); }
        else if (type.equals("rectangle")) { drawRectangle(); }
        // Adding triangle? MODIFY this method... risky!
    }
}
```

## After OCP (Good) - Interface-based design
```java
public interface Shape {
    void draw();
}

public class Circle implements Shape {
    public void draw() { /* Draw circle */ }
}

public class Rectangle implements Shape {
    public void draw() { /* Draw rectangle */ }
}

// Adding Triangle? Just create a NEW class!
public class Triangle implements Shape {
    public void draw() { /* Draw triangle */ }
}
// Circle and Rectangle are NEVER touched!
```

## How to Achieve OCP
1. Use **interfaces** or **abstract classes** to define behavior contracts
2. Use **polymorphism** — the processor works with the interface, not specific classes
3. New behavior = new implementation class

## The Pattern
```
1. Define an interface (the contract)
2. Implement it for each variant
3. Code against the interface, not the implementation
4. New variant? New class. Done.
```

## Real-World Analogy
> A USB port is open for extension (plug in keyboard, mouse, phone — anything!) but closed for modification (you never need to rewire the USB port itself).
