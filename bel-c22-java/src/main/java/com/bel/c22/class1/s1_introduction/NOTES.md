# Session 1 — Introduction to Java

## What is Java?
- A **high-level, object-oriented** programming language
- Created by Sun Microsystems (now Oracle)
- "Write Once, Run Anywhere" — compile once, run on any platform

## The Java Execution Model

```
Your Code (.java)
      ↓  javac (compiler)
  Bytecode (.class)
      ↓  JVM
   Machine Code (runs on your OS)
```

## JDK vs JRE vs JVM

| Tool | Full Name               | Purpose                                         |
|------|-------------------------|-------------------------------------------------|
| JDK  | Java Development Kit    | Everything needed to **write & compile** Java   |
| JRE  | Java Runtime Environment| Everything needed to **run** Java programs      |
| JVM  | Java Virtual Machine    | Actually **executes** the bytecode              |

- JDK ⊃ JRE ⊃ JVM  (JDK contains JRE, JRE contains JVM)

## Key Features of Java
- **Simple** — clean syntax, easy to learn
- **Platform Independent** — bytecode runs on any OS with JVM
- **Object-Oriented** — everything is an object
- **Robust & Secure** — strong type checking, no pointer manipulation
- **Multithreaded** — supports concurrent execution
- **High Performance** — JIT compiler optimizes at runtime

## Where is Java Used?
- Backend web services (REST APIs)
- Android apps
- Enterprise applications (banking, e-commerce)
- CPU-intensive applications

## Compile & Run Commands
```bash
javac HelloWorld.java   # Compile → generates HelloWorld.class
java HelloWorld         # Run the program
```
