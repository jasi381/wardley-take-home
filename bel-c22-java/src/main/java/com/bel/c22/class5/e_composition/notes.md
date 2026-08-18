# Composition

## What is Composition?
A stronger form of aggregation where the child objects are **owned** and
**lifetime-bound** to the parent object. If the whole gets destroyed, the parts
no longer exist. It represents a **"has-a" relationship with strong ownership**.

## Key Points:
- Represents a "has-a" relationship with **strong ownership**.
- The child **cannot exist without** the parent.
- When the parent is destroyed, the **child is also destroyed**.
- The child object is typically **created inside** the parent (not passed in from outside).

## Examples:
- A **House** has **Rooms** — if the House is demolished, the Rooms no longer exist.
- A **Car** has an **Engine** — the Engine is part of the Car and doesn't exist independently.
- A **Human** has a **Heart** — the Heart cannot exist without the Human.

## How to Identify Composition?
- The child is created **inside** the parent (not passed from outside).
- Deleting the parent **also deletes** the child.
- Think: "Can the part exist without the whole?" — if NO, it's composition.

## Aggregation vs Composition

| Aspect    | Aggregation                      | Composition                        |
|-----------|----------------------------------|------------------------------------|
| Ownership | Weak — child passed in           | Strong — child created inside      |
| Lifecycle | Independent — child survives     | Dependent — child dies with parent |
| Example   | Library -> Books                 | House -> Rooms                     |

## Example in this folder
- `CompositionDemo.java` — House and Rooms composition example.
