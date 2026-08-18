# this and super Keywords

## `this` Keyword
A reference to the **current object**. Used to:

1. **Differentiate instance variables from parameters** (most common use):
   ```java
   public Student(String name) {
       this.name = name;  // this.name = instance variable, name = parameter
   }
   ```

2. **Call another constructor** in the same class (constructor chaining):
   ```java
   public Student() {
       this("Unknown", 0);  // calls the 2-arg constructor
   }
   ```

3. **Pass current object as a parameter** to another method.

## `super` Keyword
A reference to the **parent class object**. Used to:

1. **Call parent class constructor**:
   ```java
   public Dog(String name, String breed) {
       super(name);  // calls Animal(String name) constructor
       this.breed = breed;
   }
   ```

2. **Call parent class method** (when overridden in child):
   ```java
   void display() {
       super.display();  // calls parent's display()
       System.out.println("Child extra info");
   }
   ```

3. **Access parent class variable** (when shadowed by child):
   ```java
   System.out.println(super.color);  // parent's color
   ```

## Important Rules
- `this()` and `super()` must be the **first statement** in a constructor.
- You **cannot use both** `this()` and `super()` in the same constructor.
- If you don't write `super()`, Java automatically inserts `super()` (no-arg) as the first line.

## Example in this folder
- `ThisSuperDemo.java` - Demonstrates all uses of this and super keywords.
