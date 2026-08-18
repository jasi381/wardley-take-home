# Singleton Pattern

## Intent
Ensure a class has **only one instance** and provide a **global access point** to it.

## The Core Rules
| Rule | How |
|------|-----|
| Nobody can call `new` | private constructor |
| One instance lives in the class | `private static` field |
| Everyone accesses it the same way | `public static getInstance()` |

## Four Variants (Know the Trade-offs)

### 1. Eager Initialization
```java
private static final Singleton INSTANCE = new Singleton();
```
- ✅ Simplest. Thread-safe (class loading is synchronized by JVM).
- ❌ Instance created even if never used (wasted memory).

### 2. Lazy Initialization
```java
if (instance == null) instance = new Singleton();
```
- ✅ Creates only when needed.
- ❌ **NOT thread-safe** — two threads can both see `null` and create two instances.

### 3. Double-Checked Locking
```java
if (instance == null) {               // fast path
    synchronized (Singleton.class) {
        if (instance == null) {       // safe path inside lock
            instance = new Singleton();
        }
    }
}
```
- ✅ Lazy + thread-safe.

## Real-World Uses
- Logger (one shared log stream)
- Database connection pool
- Application config / settings
- Thread pool manager

## Common Pitfall
Singleton breaks if:
- Serialized and deserialized (implement `readResolve()` to fix)
- Cloned (override `clone()` to throw exception)
- Multiple ClassLoaders (rare, but possible in OSGi/app servers)

## Quick Memory Aid
> "One instance, private constructor, static field, static getter."
