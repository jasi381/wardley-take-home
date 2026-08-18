# JVM Visualization Tools

## Useful Commands to Inspect JVM Internals

### 1. `javap` — Bytecode Disassembler
View the bytecode instructions of a compiled class:
```bash
javap -c MyClass.class           # Show bytecode instructions
javap -verbose MyClass.class     # Detailed info (constant pool, flags)
```

### 2. `java -verbose:class` — Class Loading Trace
See which classes the JVM loads at runtime:
```bash
java -verbose:class MyClass
```
Shows output like:
```
[Loaded java.lang.Object from ...]
[Loaded java.lang.String from ...]
[Loaded MyClass from ...]
```

### 3. `jps` — List Java Processes
```bash
jps -l    # List all running Java processes with full class names
```

### 4. `jcmd` — JVM Diagnostic Commands
```bash
jcmd <pid> VM.flags          # Show JVM flags
jcmd <pid> GC.heap_info      # Heap memory info
jcmd <pid> Thread.print      # Thread dump
```

### 5. `jvisualvm` — Visual JVM Monitor
- GUI tool to monitor JVM in real-time
- Shows memory usage, CPU, threads, GC activity
- Launch with: `jvisualvm`

## Quick Reference

| Tool              | Purpose                              |
|-------------------|--------------------------------------|
| `javap -c`        | View bytecode instructions           |
| `java -verbose:class` | Trace class loading              |
| `jps -l`          | List running Java processes          |
| `jcmd <pid>`      | JVM diagnostics                      |
| `jvisualvm`       | Visual monitoring GUI                |
