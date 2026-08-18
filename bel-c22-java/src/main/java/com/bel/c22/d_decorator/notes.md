# Decorator Pattern

## Intent
Attach **additional responsibilities** to an object **dynamically**, without modifying the class. A flexible alternative to subclassing.

## The Core Roles
| Role | Responsibility |
|------|---------------|
| **Component** (interface) | Defines the base operation |
| **Concrete Component** | The base object being decorated |
| **Decorator** (abstract) | Wraps a Component, delegates to it |
| **Concrete Decorator** | Adds behavior before/after delegating |

## How It Works
```
Client sees → Coffee interface
               ↑
WhipCreamDecorator (wraps →)
    SugarDecorator (wraps →)
        MilkDecorator (wraps →)
            SimpleCoffee        ← the actual base object
```
Each decorator:
1. Holds a reference to the **wrapped** component
2. Implements the **same interface**
3. **Delegates** to wrapped + adds its own behavior

## Code Structure
```java
interface Coffee {
    String getDescription();
    double getCost();
}

class SimpleCoffee implements Coffee { ... }

abstract class CoffeeDecorator implements Coffee {
    protected final Coffee wrapped;
    CoffeeDecorator(Coffee c) { this.wrapped = c; }
}

class MilkDecorator extends CoffeeDecorator {
    String getDescription() { return wrapped.getDescription() + " + milk"; }
    double getCost()        { return wrapped.getCost() + 0.50; }
}

// Usage: stack them!
Coffee order = new WhipCreamDecorator(
                   new SugarDecorator(
                       new MilkDecorator(
                           new SimpleCoffee())));
```

## Decorator vs. Subclassing
| | Subclassing | Decorator |
|--|-------------|-----------|
| When | Compile-time | Runtime |
| Combinations | 2^N classes | N decorator classes |
| Flexibility | Fixed per class | Mix and match per object |

## Real-World Uses
- **Java I/O Streams**: `new BufferedReader(new InputStreamReader(new FileInputStream("f.txt")))`
- **Spring**: `@Transactional`, `@Cacheable` (AOP decorates methods)
- **Collections**: `Collections.unmodifiableList()`, `Collections.synchronizedList()`
- **Logging/Metrics**: Wrap service calls with timing/logging decorators

## Common Pitfalls
- **Too many small classes**: Each decorator is a new class. Can be hard to navigate.
- **Order matters**: `MilkDecorator(SugarDecorator(coffee))` ≠ `SugarDecorator(MilkDecorator(coffee))` if order affects behavior.
- **Identity**: `decoratedObj != originalObj` — they're different objects. `instanceof` checks break.

## Quick Memory Aid
> "Wrap it like a gift — each layer adds something, but it's still a gift."
