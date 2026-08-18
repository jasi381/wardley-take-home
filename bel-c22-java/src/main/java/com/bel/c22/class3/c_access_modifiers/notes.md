# Access Modifiers

## What are Access Modifiers?
Access modifiers determine the **visibility and accessibility** of classes, methods,
and variables. They help implement encapsulation.

## The Four Access Modifiers (Most Restrictive to Least)

### 1. `private` (Most Restrictive)
- Accessible **only within the class** it is declared in.
- Not visible to any other class, even in the same package.
- Use for: internal data that should be hidden.

### 2. `default` (Package-Private) - No keyword used
- Accessible **only within the same package**.
- When you don't write any access modifier, this is what you get.
- Use for: helper classes/methods only needed within the package.

### 3. `public` (Least Restrictive)
- Accessible from **any other class**, in any package.
- Use for: APIs, methods/classes meant to be used everywhere.

## Visibility Summary Table (Focus: private, default, public)

| Visibility                      | private | default | public |
|---------------------------------|---------|---------|--------|
| Same Class                      | YES     | YES     | YES    |
| Same Package (non-Subclass)     | NO      | YES     | YES    |
| Different Package (non-Subclass)| NO      | NO      | YES    |

> Note: `protected` is the 4th access modifier (between default and public).
> It will be covered when we learn about Inheritance.

## Example in this folder
- `samepackage/Person.java` - Class with private, default, and public members.
- `samepackage/SamePackageDemo.java` - Shows what is accessible within the same package.
- `differentpackage/DifferentPackageDemo.java` - Shows what is accessible from another package.
