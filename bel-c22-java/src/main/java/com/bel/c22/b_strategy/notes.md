# Strategy Pattern

## Intent
Define a **family of algorithms**, encapsulate each one, and make them **interchangeable** at runtime.

## The Core Roles
| Role | Responsibility |
|------|---------------|
| **Strategy** (interface) | Declares the algorithm method signature |
| **Concrete Strategy** | Implements a specific algorithm |
| **Context** | Holds a Strategy reference, delegates work to it |

## How It Works
```
Context ──delegates──► Strategy (interface)
                           ├── ConcreteStrategyA
                           ├── ConcreteStrategyB
                           └── ConcreteStrategyC
```
1. Context has a `setStrategy()` method
2. Client picks a strategy and injects it into the Context
3. Context calls `strategy.execute()` — doesn't know which concrete class it is

## Code Structure
```java
interface PaymentStrategy {
    void pay(double amount);
}

class CreditCardPayment implements PaymentStrategy { ... }
class PayPalPayment implements PaymentStrategy { ... }

class ShoppingCart {
    private PaymentStrategy strategy;
    void setPaymentStrategy(PaymentStrategy s) { this.strategy = s; }
    void checkout(double total) { strategy.pay(total); }
}
```

## Strategy vs. If-Else
| Approach | Adding new behavior | Problem |
|----------|-------------------|---------|
| If-else/switch | Modify existing code | Violates OCP, grows endlessly |
| Strategy | Add new class | Existing code untouched |

## Real-World Uses
- **Sorting algorithms**: Choose quicksort vs mergesort at runtime
- **Compression**: ZIP, GZIP, LZ4 — same interface, different algorithms
- **Validation**: Different validation rules per country/region
- **Java's built-in**: `Comparator<T>` is a Strategy! `Collections.sort(list, comparator)`
- **Spring**: `AuthenticationStrategy`, `ResourceLoader`

## Strategy vs. State Pattern
| | Strategy | State |
|--|----------|-------|
| Who decides | Client picks the strategy | Object transitions itself |
| Intent | Swap algorithms | Manage state-dependent behavior |
| Awareness | Strategies don't know about each other | States often trigger transitions |

## Quick Memory Aid
> "Same job, different ways to do it. Pick one at runtime."
