# Association

## What is Association?
A general relationship between two independent classes that allows one object
to use the functionality of another. It represents a **"uses-a"** relationship.

## Key Points:
- Represents a **"uses-a"** relationship.
- Objects are **independent** of each other.
- **No ownership** is implied.
- Can be **one-to-one**, **one-to-many**, or **many-to-many**.

## Example:
- A **Car** and a **Driver** are associated because a car is driven by a driver,
  but neither owns the other. Both can exist independently.
- A **Teacher** and a **Student** — a teacher teaches students, but neither
  owns the other.

## Association vs Aggregation vs Composition

| Aspect            | Association         | Aggregation              | Composition                |
|-------------------|---------------------|--------------------------|----------------------------|
| Relationship Type | "Uses-a"            | "Has-a" (weak ownership) | "Has-a" (strong ownership) |
| Ownership         | No ownership        | Weak ownership           | Strong ownership           |
| Dependency        | Objects independent | Loosely coupled          | Child depends on parent    |
| Lifetime          | Independent         | Independent              | Dependent                  |
| Example           | Driver <-> Car      | Library -> Books         | House -> Rooms             |

## Example in this folder
- `AssociationDemo.java` — Driver and Car association example.
