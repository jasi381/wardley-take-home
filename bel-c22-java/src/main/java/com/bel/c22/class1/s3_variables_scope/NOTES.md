# Variables and Scope

## What is a Variable?
A named container for storing a value.
```java
int age = 25;      // type  name  value
```

## 3 Types of Variables by Scope

### 1. Local Variable
- Declared **inside a method or block**
- Only accessible within that method/block
- **Must be initialized before use** (no default value)

### 2. Instance Variable
- Declared **inside a class, outside any method**
- Each object gets its **own copy**
- Has default values: `0` for numbers, `null` for objects, `false` for boolean

### 3. Static (Class) Variable
- Declared with the `static` keyword inside a class
- **Shared across all objects** — only one copy exists
- Good for constants or shared counters

## Quick Comparison

| Feature         | Local       | Instance      | Static          |
|-----------------|-------------|---------------|-----------------|
| Declared In     | Method/block | Class body   | Class body      |
| Access          | Within block | Via object   | Via class name  |
| Default Value   | None (error) | Yes (0/null) | Yes (0/null)    |
| Memory          | Stack        | Heap          | Method area     |
