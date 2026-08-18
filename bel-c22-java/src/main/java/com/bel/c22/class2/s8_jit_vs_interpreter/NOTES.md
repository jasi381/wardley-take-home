# JIT Compiler vs Interpreter

## Two Ways the JVM Executes Bytecode

### Interpreter
- Executes bytecode **one instruction at a time**
- **Slow** for repeated code (translates the same code every time)
- **Fast startup** — no compilation delay
- Low memory usage

### JIT (Just-In-Time) Compiler
- Compiles **hot** bytecode to **native machine code** at runtime
- **Faster execution** after initial compilation
- Higher memory usage (stores compiled code)
- Slower startup due to compilation overhead

## How They Work Together
1. JVM starts by **interpreting** all bytecode
2. JVM monitors which methods are called frequently ("hot spots")
3. Hot methods are **JIT compiled** to native code
4. Future calls to those methods run the fast native code

```
First few calls:   Interpreter (bytecode → execute)
After many calls:  JIT Compiler (bytecode → native code → execute fast!)
```

## Comparison Table

| Feature      | Interpreter              | JIT Compiler               |
|-------------|--------------------------|----------------------------|
| Speed       | Slower (repeated work)   | Faster (compiled once)     |
| Startup     | Quick                    | Slower (compilation time)  |
| Memory      | Low                      | Higher (stores native code)|
| Best For    | Rarely-used code paths   | Hot/frequent code paths    |

## Useful JVM Flags
```bash
java -Xint MyClass              # Interpreter only (no JIT)
java -Xcomp MyClass             # Aggressive JIT compilation
java -XX:+PrintCompilation MyClass  # Show which methods JIT compiled
```

## Compilation vs Interpretation (Summary)
- **Compilation** converts source → bytecode (done once by `javac`)
- **Interpretation** executes bytecode at runtime (done by JVM)
- **JIT** optimizes by compiling hot bytecode → native code at runtime
