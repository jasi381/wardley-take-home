# Operators in Java

## 1. Arithmetic Operators
| Operator | Meaning     | Example        |
|----------|-------------|----------------|
| +        | Addition    | 5 + 3 = 8      |
| -        | Subtraction | 5 - 3 = 2      |
| *        | Multiply    | 5 * 3 = 15     |
| /        | Divide      | 10 / 3 = 3     |
| %        | Remainder   | 10 % 3 = 1     |

> Note: `10 / 3 = 3` (integer division truncates the decimal)

## 2. Relational (Comparison) Operators
Return `true` or `false`.
| Operator | Meaning           |
|----------|-------------------|
| ==       | Equal to          |
| !=       | Not equal to      |
| >        | Greater than      |
| <        | Less than         |
| >=       | Greater or equal  |
| <=       | Less or equal     |

## 3. Logical Operators
| Operator | Meaning | Example                        |
|----------|---------|--------------------------------|
| &&       | AND     | true && false → false          |
| \|\|     | OR      | true \|\| false → true         |
| !        | NOT     | !true → false                  |

## 4. Assignment Operators
| Operator | Shorthand for |
|----------|---------------|
| +=       | x = x + n    |
| -=       | x = x - n    |
| *=       | x = x * n    |
| /=       | x = x / n    |

## 5. Increment / Decrement
```java
x++   // post-increment: use x, then add 1
++x   // pre-increment:  add 1, then use x
x--   // post-decrement
--x   // pre-decrement
```
