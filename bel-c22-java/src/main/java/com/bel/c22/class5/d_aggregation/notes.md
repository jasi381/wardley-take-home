# Aggregation

## What is Aggregation?
A specific form of association where one class (the whole) contains a collection
of other classes (the parts), but the **lifecycle of the parts does NOT depend on
the whole**. It represents a **"has-a" relationship with weak ownership**.

## Key Points:
- Shows a **weak ownership** relationship.
- The lifetime of parent and child objects are **independent**.
- If the parent is removed, the **child continues to exist**.
- The child object is typically **passed in** from outside (not created inside the parent).

## Examples:
- A **Library** contains **Books**, but a Book can exist without the Library.
- A **Team** has a **Coach**, but the Coach is not dependent on the Team.
- A **Department** has **Employees**, but Employees exist independently.

## How to Identify Aggregation?
- The child object is created **outside** the parent and **passed in**.
- Deleting the parent does NOT delete the child.
- Think: "Can the part exist without the whole?" — if YES, it's aggregation.

## Example in this folder
- `AggregationDemo.java` — Library and Books aggregation example.
