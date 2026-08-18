# Packages in Java

## What is a Package?
A package is a container for a collection of classes, sub-packages, and interfaces.

## Why use Packages?
1. **Namespace Management** - Avoids naming conflicts. E.g., `school.math.Teacher` and `school.science.Teacher` can coexist.
2. **Access Protection** - Controls access to classes and interfaces.
3. **Code Organization** - Groups related classes together.

## Types of Packages
1. **Built-in Packages** - Provided by Java (e.g., `java.util`, `java.lang`, `java.io`)
2. **User-defined Packages** - Created by developers (what we do here)

## Key Rules
- Package declaration must be the **first statement** in a Java file.
- Package names are written in **all lowercase** by convention.
- Sub-packages are NOT automatically imported; they must be imported manually.
- Use `import` to use classes from other packages.

## Example Structure in this folder
```
a_packages/
  school/
    math/
      Teacher.java       <- package class3.a_packages.school.math
    science/
      Teacher.java       <- package class3.a_packages.school.science (same class name, different package!)
  PackageDemo.java       <- imports and uses both Teacher classes
```

## How to Run
```bash
# From the src directory:
javac class3/a_packages/school/math/Teacher.java
javac class3/a_packages/school/science/Teacher.java
javac class3/a_packages/PackageDemo.java
java class3.a_packages.PackageDemo
```
