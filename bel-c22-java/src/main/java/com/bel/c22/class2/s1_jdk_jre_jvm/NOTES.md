# JDK, JRE, and JVM

## The Three Layers

```
┌─────────────────────────────────────────┐
│  JDK (Java Development Kit)             │
│  ┌───────────────────────────────────┐  │
│  │  JRE (Java Runtime Environment)   │  │
│  │  ┌─────────────────────────────┐  │  │
│  │  │  JVM (Java Virtual Machine) │  │  │
│  │  └─────────────────────────────┘  │  │
│  └───────────────────────────────────┘  │
└─────────────────────────────────────────┘
```

**JDK ⊃ JRE ⊃ JVM** — each one wraps the next.

## JDK (Java Development Kit)
- Everything you need to **develop** Java applications
- Includes: `javac` (compiler), `javadoc`, `jdb` (debugger), `jar`, and more
- Contains the JRE + development tools

## JRE (Java Runtime Environment)
- Everything needed to **run** Java applications
- Includes: JVM + core libraries (`java.lang`, `java.util`, etc.)
- No compiler — you can only run, not build

## JVM (Java Virtual Machine)
- The engine that **executes** Java bytecode
- Provides platform independence — same bytecode runs on Windows, Mac, Linux
- Handles memory management, garbage collection, security

## Quick Comparison

| Component | Contains              | Purpose                  |
|-----------|-----------------------|--------------------------|
| JDK       | JRE + dev tools       | Develop & compile Java   |
| JRE       | JVM + core libraries  | Run Java programs        |
| JVM       | Execution engine      | Execute bytecode         |

## Key Takeaway
- **Developers** install the JDK (to write and compile code)
- **End users** only need the JRE (to run Java programs)
- The JVM is the magic that makes "Write Once, Run Anywhere" work
