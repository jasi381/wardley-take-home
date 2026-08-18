# Type Casting in Java

## What is Type Casting?
Converting a value from one data type to another.

## 1. Widening (Implicit / Automatic)
- Small type → Larger type
- Java does this **automatically**
- **No data loss**

```
byte → short → int → long → float → double
```

```java
int x = 100;
double d = x;  // automatically converts int to double
```

## 2. Narrowing (Explicit / Manual)
- Large type → Smaller type
- You must **explicitly cast**
- **Possible data loss** (decimals are truncated)

```java
double price = 99.99;
int rounded = (int) price;  // must explicitly cast → 99 (loses .99)
```

## Common Mistake
```java
double d = 9.7;
int i = d;       // ❌ Compile error — you must cast explicitly
int i = (int) d; // ✅ Works — i = 9
```
