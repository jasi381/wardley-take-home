# JVM Architecture — 100 Feet View

## JVM High-Level Components

```
┌──────────────────────────────────────────────────┐
│                    JVM                            │
│                                                   │
│  ┌──────────────────────────────────────────┐     │
│  │       Class Loader Subsystem             │     │
│  │  (Loading → Linking → Initialization)    │     │
│  └──────────────────────────────────────────┘     │
│                      │                            │
│                      ▼                            │
│  ┌──────────────────────────────────────────┐     │
│  │       Runtime Memory Areas               │     │
│  │  Method Area | Heap | Stack | PC | Native│     │
│  └──────────────────────────────────────────┘     │
│                      │                            │
│                      ▼                            │
│  ┌──────────────────────────────────────────┐     │
│  │          Execution Engine                │     │
│  │   Interpreter | JIT Compiler | GC        │     │
│  └──────────────────────────────────────────┘     │
│                      │                            │
│  ┌──────────────────────────────────────────┐     │
│  │    Native Interface (JNI) & Libraries    │     │
│  └──────────────────────────────────────────┘     │
└──────────────────────────────────────────────────┘
```

## 1. Class Loader Subsystem
- Loads `.class` files into memory
- Performs **Loading → Linking → Initialization**
- Ensures bytecode verification before execution

## 2. Runtime Memory Areas
| Area               | Stores                                        |
|--------------------|-----------------------------------------------|
| Method Area        | Class metadata, static variables, method code |
| Heap               | Objects and instance variables                |
| Java Stack         | Method call frames, local variables           |
| PC Register        | Current instruction being executed            |
| Native Method Stack| Native (JNI) method execution                 |

## 3. Execution Engine
- **Interpreter** — executes bytecode line-by-line (slow but quick startup)
- **JIT Compiler** — converts hot bytecode to native code (fast execution)
- **Garbage Collector** — frees memory by removing unused objects

## 4. Native Interface (JNI)
- Enables Java code to call native code (C, C++)
- Used for system-level operations and performance
