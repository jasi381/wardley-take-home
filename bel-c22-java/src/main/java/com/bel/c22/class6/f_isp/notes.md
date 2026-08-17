# I - Interface Segregation Principle (ISP)

## Definition
> "Clients should **not be forced** to depend on interfaces they **don't use**."

Create **smaller, focused interfaces** instead of one large, bloated one.

## Why ISP Matters
- Reduces coupling between components
- Improves maintainability and flexibility
- Classes only implement what they actually need
- Prevents empty/exception-throwing method implementations

## Before ISP (Bad) - Fat Interface
```java
public interface Worker {
    void work();
    void eat();    // Robot doesn't eat!
}

public class Robot implements Worker {
    public void work() { /* OK */ }
    public void eat() {
        throw new UnsupportedOperationException(); // Forced!
    }
}
```

## After ISP (Good) - Segregated Interfaces
```java
public interface Workable { void work(); }
public interface Eatable  { void eat();  }

public class Employee implements Workable, Eatable {
    public void work() { /* works */ }
    public void eat()  { /* eats  */ }
}

public class Robot implements Workable {
    public void work() { /* works */ }
    // No eat() needed — clean!
}
```

## How to Spot ISP Violations
1. A class implements an interface but throws `UnsupportedOperationException` for some methods
2. A class implements an interface but leaves some methods with empty bodies
3. You see "this method doesn't apply to this class" comments

## ISP + LSP Connection
Notice that ISP violations often **cause** LSP violations:
- Fat interface → forced empty methods → child can't fulfill parent contract → LSP broken!

## Real-World Analogy
> Think of a restaurant menu. A vegetarian shouldn't be forced to read through 50 non-veg items to find their options. A better design: separate Veg Menu and Non-Veg Menu. Each customer picks only what's relevant to them.
