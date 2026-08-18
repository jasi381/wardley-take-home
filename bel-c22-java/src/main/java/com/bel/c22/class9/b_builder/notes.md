# Builder Pattern

## Intent
Construct a **complex object step-by-step**, separating the construction process
from the final representation.

## The Problem: Telescoping Constructor
```java
// Which arg is which? What are the defaults?
new User("Alice", 25, "alice@email.com", "NY", true, false, "premium");
```
- Hard to read — positional args tell you nothing.
- Error-prone — easy to swap two `boolean` args silently.
- Inflexible — adding a field means updating every call site.

## The Solution: Builder
```java
new User.Builder("Alice", "alice@email.com")  // required fields in constructor
    .age(25)
    .city("NY")
    .newsletter(true)
    .build();                                  // validate + create immutable object
```

## Structure

```
Product (HttpRequest)           — immutable, private constructor
  └── static Builder            — mutable, fluent setters, validates in build()
```

| Step | What happens |
|------|-------------|
| `new Builder(req, url)` | Required fields set, optionals get defaults |
| `.body(...).timeout(...)` | Each returns `this` → enables chaining |
| `.build()` | Validates, creates and returns immutable Product |

## Key Properties
- **Immutability**: Product fields are `final`; no setters exposed.
- **Fluent API**: Each setter returns `this` (method chaining).
- **Validation at build time**: Catch errors before the object is handed out.
- **Named parameters**: Each setter call documents what it sets.

## Fluent Chaining — How It Works
```java
Builder timeout(int seconds) {
    this.timeoutSeconds = seconds;
    return this;    // ← returns the builder itself, so next call chains on
}
```

## Real-World Uses
- `StringBuilder` (Java standard library)
- `OkHttpClient.Builder`, `Retrofit.Builder` (Android)
- `AlertDialog.Builder` (Android)
- SQL query builders (JOOQ, Hibernate Criteria)

## Builder vs Constructor
| | Telescoping Constructor | Builder |
|--|--|--|
| Readability | Poor (positional) | Great (named) |
| Optional fields | Needs many overloads | Default values in Builder |
| Immutability | Hard | Natural |
| Validation | Scattered | Centralized in `build()` |

## Quick Memory Aid
> "Builder = named parameters + validation + immutability for complex objects."
