 # Foundational Principles: DRY, KISS, YAGNI

---

## 1. DRY - Don't Repeat Yourself

### What is DRY?
- **Principle**: Avoid duplicating code
- **Objective**: Every piece of knowledge or logic should have a single, unambiguous representation

### Benefits
| Benefit | Why? |
|---------|------|
| **Maintenance** | Changes in logic require updates in only ONE place |
| **Readability** | Code is concise and easier to understand |
| **Consistency** | Uniformity in implementation reduces errors |

### How to Apply DRY
1. Identify repeated patterns in your code
2. Refactor common logic into methods or classes
3. Use inheritance or composition for code reuse
4. Extract constants (like `GST_RATE = 0.18`) instead of magic numbers

### Real-World Analogy
> Imagine updating your phone number. Would you rather update it in ONE place (contacts app) or in 50 different apps manually? DRY = update once, reflected everywhere.

---

## 2. KISS - Keep It Simple, Stupid

### What is KISS?
- **Principle**: Simplicity should be a key goal in design
- **Objective**: Keep designs simple and easy to understand

### Benefits
| Benefit | Why? |
|---------|------|
| **Clarity** | Simple designs are easier to comprehend |
| **Maintenance** | Easier to debug and modify |
| **Scalability** | Simple solutions are more adaptable to change |

### How to Apply KISS
1. Use **guard clauses** (early returns) instead of deep nesting
2. Use clear and straightforward solutions
3. Refactor overly complex code into smaller, manageable parts
4. If a junior developer can't understand it, simplify it!

### Before vs After Pattern
```
BEFORE (nested):                AFTER (guard clauses):
if (x != null) {                if (x == null) return;
    if (x > 0) {               if (x <= 0) return;
        if (isPaid) {           if (!isPaid) return;
            // do work          // do work (clean!)
        }
    }
}
```

---

## 3. YAGNI - You Aren't Gonna Need It

### What is YAGNI?
- **Principle**: Only implement functionality when it is needed
- **Objective**: Avoid unnecessary complexity and features

### Benefits
| Benefit | Why? |
|---------|------|
| **Focus** | Develop features based on current requirements |
| **Efficiency** | Allocate resources wisely |
| **Flexibility** | Adapt to changing needs without excess overhead |

### How to Apply YAGNI
1. Focus on current requirements only
2. Resist the temptation to over-engineer
3. Refactor or add features when they become necessary
4. Ask: "Does anyone need this RIGHT NOW?"

### Common YAGNI Violations
- Adding extra API endpoints "just in case"
- Building a plugin system when you only have one plugin
- Creating abstract classes when there's only one implementation
- Adding configuration options nobody will use

---

## Summary Table

| Principle | Mantra | Violation Example |
|-----------|--------|-------------------|
| **DRY** | "Write it once" | Copy-pasting tax calculation in 5 methods |
| **KISS** | "Keep it simple" | 6 levels of nested if-else |
| **YAGNI** | "Build it when needed" | Adding multiply() when only add() was asked |
