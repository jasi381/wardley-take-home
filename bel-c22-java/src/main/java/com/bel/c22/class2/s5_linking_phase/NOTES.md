# Linking Phase in JVM

## What is Linking?
After a class is **loaded**, it goes through **linking** before it can be used.
Linking has 3 sub-phases: **Verification → Preparation → Resolution**.

## 1. Verification
- Checks that the `.class` file is **correctly formatted**
- Ensures no illegal operations (prevents security risks)
- Uses the **Bytecode Verifier**
- Example checks:
  - Valid magic number (`0xCAFEBABE`)
  - No stack overflow/underflow
  - Type safety of all instructions

## 2. Preparation
- Allocates memory for **static variables**
- Assigns **default values** (NOT user-defined values yet!)
- Defaults: `int → 0`, `boolean → false`, `Object → null`

```java
static int count = 10;
// During Preparation: count = 0 (default)
// During Initialization: count = 10 (user value)
```

## 3. Resolution
- Converts **symbolic references** to **actual memory addresses**
- Symbolic reference: class name as a string (e.g., `"java/lang/System"`)
- Direct reference: actual pointer to the class in memory

## Summary

| Phase        | What it Does                                    |
|-------------|-------------------------------------------------|
| Verification | Checks `.class` file is valid and safe          |
| Preparation  | Allocates memory, sets default values           |
| Resolution   | Converts symbolic refs → actual memory addresses|
