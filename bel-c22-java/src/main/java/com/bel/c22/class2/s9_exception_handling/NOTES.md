# Exception Handling in Java

## What are Exceptions?
Events that **disrupt the normal flow** of program execution.
Examples: dividing by zero, accessing null, file not found.

## Why Handle Exceptions?
- **Robustness** — prevent abrupt program termination
- **Graceful recovery** — handle errors and continue
- **Debugging** — understand what went wrong and where

## Exception Hierarchy

```
              Throwable
              /       \
          Error      Exception
           |          /        \
     OutOfMemory   Checked    RuntimeException
     StackOverflow IOException  (Unchecked)
                   SQLException  NullPointerException
                                ArithmeticException
                                ArrayIndexOutOfBounds
```

## Checked vs Unchecked Exceptions

| Type      | Checked At    | Must Handle?  | Examples                          |
|-----------|---------------|---------------|-----------------------------------|
| Checked   | Compile-time  | Yes (try/catch or throws) | IOException, SQLException |
| Unchecked | Runtime       | No (but you should)       | NullPointerException, ArithmeticException |

## Checked Exceptions
- Compiler **forces** you to handle them
- Represent conditions a well-behaved app should anticipate
- Must use `try-catch` or declare with `throws`
- Examples: `IOException`, `SQLException`, `ClassNotFoundException`

## Unchecked Exceptions (RuntimeException)
- Compiler does **not** force handling
- Usually caused by **programming errors**
- Examples: `NullPointerException`, `ArithmeticException`, `ArrayIndexOutOfBoundsException`

## Error (Not Exception)
- **Irrecoverable** issues — you typically don't catch these
- Examples: `OutOfMemoryError`, `StackOverflowError`

## try-catch-finally Syntax
```java
try {
    // code that might throw an exception
} catch (SpecificException e) {
    // handle the exception
} finally {
    // always runs — cleanup code
}
```

## The throws Keyword
```java
public void readFile() throws IOException {
    // If this method can throw a checked exception,
    // declare it with 'throws' to let the caller handle it
}
```
