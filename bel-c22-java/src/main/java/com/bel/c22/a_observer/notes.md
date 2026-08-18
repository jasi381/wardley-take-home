# Observer Pattern

## Intent
Define a **one-to-many** dependency so that when one object changes state, all dependents are **notified automatically**.

## The Core Roles
| Role | Responsibility |
|------|---------------|
| **Subject** (Observable) | Maintains a list of observers, notifies on state change |
| **Observer** (interface) | Declares `update()` — how to react to changes |
| **Concrete Observer** | Implements specific reaction logic |

## How It Works
```
Subject ──notify()──► Observer A
                  ──► Observer B
                  ──► Observer C
```
1. Observers **register** with the Subject (`addObserver`)
2. Subject's state changes
3. Subject calls `notifyObservers()` → loops through list, calls `update()` on each
4. Observers can **unregister** at any time (`removeObserver`)

## Code Structure
```java
// Subject
class Stock {
    List<Observer> observers = new ArrayList<>();
    void addObserver(Observer o)    { observers.add(o); }
    void removeObserver(Observer o) { observers.remove(o); }
    void setPrice(double p) {
        this.price = p;
        for (Observer o : observers) o.update(name, price);
    }
}

// Observer
interface Observer {
    void update(String name, double price);
}
```

## Real-World Uses
- **Event listeners** in GUI frameworks (button click → handler)
- **Pub/Sub** messaging systems (Kafka consumers)
- **MVC architecture** (Model changes → View updates)
- **Java's built-in**: `PropertyChangeListener`, `java.util.Observable` (deprecated)
- **Spring**: `ApplicationEvent` + `@EventListener`

## Observer vs. Polling
| Approach | How | Problem |
|----------|-----|---------|
| Polling | Observer keeps checking "did it change?" | Wasteful, tight coupling |
| Observer | Subject pushes changes to observers | Efficient, loose coupling |

## Common Pitfalls
- **Memory leaks**: Forgetting to unregister observers (they stay in the list forever)
- **Order dependency**: Don't assume observers are notified in a specific order
- **Cascading updates**: Observer A's reaction triggers Subject B, which notifies Observer C... can cause infinite loops
- **Thread safety**: In multi-threaded apps, the observer list needs synchronization

## Quick Memory Aid
> "Don't call us, we'll call you." — The Subject notifies Observers, not the other way around.
