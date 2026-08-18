# Loops and Jump Statements

## Why Use Loops?
To repeat a block of code without writing it multiple times.

## 1. for Loop
Use when you know **exactly how many times** to repeat.
```java
for (initialization; condition; update) {
    // body
}
```

## 2. while Loop
Use when you want to repeat **as long as a condition is true**.
Check condition **before** running the body.
```java
while (condition) {
    // body
}
```

## 3. do-while Loop
Similar to while, but checks condition **after** running the body.
**Always runs at least once.**
```java
do {
    // body
} while (condition);
```

## Comparison Table
| Loop     | When to Use                      | Runs at least once? |
|----------|----------------------------------|---------------------|
| for      | Known count (1 to 10)            | Only if count > 0   |
| while    | Unknown count, check first       | No                  |
| do-while | Unknown count, must run once     | Yes                 |

## Jump Statements
| Statement  | What it does                                |
|------------|---------------------------------------------|
| `break`    | Exits the loop immediately                  |
| `continue` | Skips current iteration, goes to next       |
| `return`   | Exits the entire method (also returns value)|
