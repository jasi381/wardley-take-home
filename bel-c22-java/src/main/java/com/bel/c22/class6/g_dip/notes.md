# D - Dependency Inversion Principle (DIP)

## Definition
> "High-level modules should **not depend on low-level modules** directly. Both should depend on **abstractions** (interfaces)."

## The Problem (Without DIP)
```
High-Level Module (Switch)
        ↓ depends directly on
Low-Level Module (LightBulb)
```
If you want Switch to control a Fan, you must **modify** Switch. Tight coupling!

## The Solution (With DIP)
```
High-Level Module (Switch)
        ↓ depends on
    Interface (Switchable)
        ↑ implemented by
Low-Level Modules (LightBulb, Fan, AC)
```
Switch depends on `Switchable`, not on any specific device. Add new devices freely!

## Before DIP (Bad)
```java
class Switch {
    private LightBulb bulb = new LightBulb(); // Hardcoded!
    public void operate() { bulb.turnOn(); }
}
```

## After DIP (Good)
```java
interface Switchable {
    void turnOn();
    void turnOff();
}

class Switch {
    private Switchable device; // Abstraction!
    Switch(Switchable device) { this.device = device; }
    public void operate() { device.turnOn(); }
}
```

## Key Concepts

### Dependency Injection
Instead of a class **creating** its dependencies internally (`new LightBulb()`), they are **passed in** from outside (via constructor, setter, or method parameter).

```java
// BAD: Creates its own dependency
Switch() { this.bulb = new LightBulb(); }

// GOOD: Dependency is injected
Switch(Switchable device) { this.device = device; }
```

### Why "Inversion"?
Traditional approach: High-level depends on low-level (top-down).
DIP: **Both** depend on an interface in the middle. The dependency direction is **inverted** for the low-level module.

## Benefits
1. **Flexibility**: Easily switch out implementations
2. **Testability**: Can inject mock objects for testing
3. **Loose Coupling**: Changes to one module don't ripple to others

## Real-World Analogy
> Your phone charger uses a USB-C **interface**. Your phone doesn't care if it's a Samsung charger or Apple charger — as long as it's USB-C, it works. The phone depends on the standard (abstraction), not the specific charger (implementation).
