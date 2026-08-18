# Protected Access Modifier

## Why learn `protected` now?
In the previous section we saw inheritance with `extends`. But what happens when
the parent's fields are `private`? The child class **cannot access them directly**,
even though it inherited them. This is where `protected` comes in.

## Access Modifier Recap

| Modifier | Same Class | Same Package | Subclass (diff pkg) | Everywhere |
|----------|-----------|-------------|---------------------|------------|
| `private` | Yes | No | No | No |
| default (none) | Yes | Yes | No | No |
| `protected` | Yes | Yes | **Yes** | No |
| `public` | Yes | Yes | Yes | Yes |

## Key Point
`protected` = `default` + **subclass access across packages**.

A `protected` member is accessible:
- Within the same package (just like default)
- By subclasses even in a **different package** (this is the extra power!)

## When to use `protected`?
- When you want child classes to access parent fields/methods directly,
  but NOT allow random unrelated classes to access them.
- Common pattern: parent class has `protected` fields, child classes use them directly.

## Example in this folder
- `parent/Animal.java` — parent class with private, protected, and public fields
- `parent/SamePackageTest.java` — shows same-package access to protected
- `child/Dog.java` — subclass in a DIFFERENT package accessing protected members
