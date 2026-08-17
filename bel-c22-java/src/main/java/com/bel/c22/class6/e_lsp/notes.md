# L - Liskov Substitution Principle (LSP)

## Definition
> "Subtypes (derived classes) must be **substitutable** for their base types (parent classes) without altering the correctness of the program."
> — Barbara Liskov

If you have a method that works with a `Parent`, it must also work correctly with **any** `Child` — no exceptions, no surprises.

## In Simpler Words
Think of a family where a parent promises to do certain tasks. Any child who steps in for the parent must also do these tasks properly, **without changing how things are supposed to work**.

## The Classic Violation: Bird → Ostrich

### Before LSP (Bad)
```java
class Bird {
    public void fly() { /* Flying logic */ }
    /*
            Bird pigeon = new Pigeon();
            Bird crow = new Crow();
            
            void xyz(Bird bird) {
            
                bird.fly();
            }
            
     */
}

class Ostrich extends Bird {
    public void fly() {
        throw new UnsupportedOperationException("Can't fly!"); // VIOLATION!
    }
}
```
**Problem**: Code that says `bird.fly()` will crash if `bird` is an Ostrich.

### After LSP (Good)
```java
abstract class Bird {
    public abstract void move(); // ALL birds can move
}

class Sparrow extends Bird {
    public void move() { fly(); }    // Sparrow moves by flying
}

class Ostrich extends Bird {
    public void move() { walk(); }   // Ostrich moves by walking
}
```
**Solution**: Redesign the hierarchy so the contract (method) makes sense for ALL children.

## How to Spot LSP Violations
Ask: "If I replace the parent with this child, will anything break?"

Red flags:
1. Child throws `UnsupportedOperationException` for a parent method
2. Child method does nothing (empty body) when parent method should do something
3. Child adds unexpected preconditions the parent didn't have
4. Child returns something the caller wouldn't expect

## Real-World Analogy
> If you hire a "driver" and they show up but say "Sorry, I can't drive manual cars", they've violated the substitution contract. A driver should be able to drive — that's the whole point of hiring a "driver."
