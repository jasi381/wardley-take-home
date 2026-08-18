# Inheritance

## What is Inheritance?
A mechanism where a new class (child/subclass) inherits properties and behaviors
from an existing class (parent/superclass) using the `extends` keyword.

## Why Inheritance?
- **Code Reuse**: Don't repeat common fields/methods in every class.
- **Hierarchical Relationships**: Models real-world "is-a" relationships (Dog IS-A Animal).
- **Extensibility**: Child class can add new fields/methods or override existing ones.

## Types of Inheritance in Java

| Type | Description | Supported? |
|------|-------------|------------|
| **Single** | One child extends one parent | Yes |
| **Multilevel** | A -> B -> C (chain) | Yes |
| **Hierarchical** | One parent, multiple children | Yes |
| **Multiple** | One child extends two parents | No (Diamond Problem) |
| **Hybrid** | Combination of above types | Partially (no multiple) |

## Diamond Problem
Java does NOT support multiple inheritance with classes to avoid ambiguity.
If class D extends both B and C, and both B and C have the same method,
which version should D inherit? This ambiguity is the Diamond Problem.

## Example in this folder
- `InheritanceDemo.java` - Single, Multilevel, and Hierarchical inheritance examples.
