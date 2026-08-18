# Control Flow — if / else / switch

## if Statement
Runs the block only if the condition is `true`.
```java
if (condition) {
    // runs when condition is true
}
```

## if-else Statement
Provides an alternative path when condition is `false`.
```java
if (condition) {
    // runs when true
} else {
    // runs when false
}
```

## else-if Ladder
Check multiple conditions in order. First match wins.
```java
if (score >= 90) {
    grade = "A";
} else if (score >= 75) {
    grade = "B";
} else {
    grade = "C";
}
```

## switch Statement
When you want to match a single variable against many values.
- Use `break` to stop fall-through to next case
- `default` runs if no case matches

```java
switch (day) {
    case 1: System.out.println("Monday"); break;
    case 2: System.out.println("Tuesday"); break;
    default: System.out.println("Other day");
}
```

## if-else vs switch — When to Use?
- **if-else** → for ranges, complex conditions (`> 18`, `!= null`)
- **switch** → for exact value matching (menu options, day numbers)
