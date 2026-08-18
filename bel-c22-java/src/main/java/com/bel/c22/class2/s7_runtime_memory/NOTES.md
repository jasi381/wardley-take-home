# JVM Runtime Memory Areas

## Five Memory Areas

```
┌─────────────────────────────────────────────┐
│              JVM Runtime Memory              │
│                                              │
│  ┌────────────┐  ┌─────────────────────┐    │
│  │ Method Area │  │        Heap         │    │
│  │  (shared)   │  │      (shared)       │    │
│  └────────────┘  └─────────────────────┘    │
│                                              │
│  ┌────────────┐  ┌──────────┐ ┌──────────┐ │
│  │ Java Stack │  │    PC    │ │  Native  │  │
│  │(per thread)│  │ Register │ │  Method  │  │
│  │            │  │(per thrd)│ │  Stack   │  │
│  └────────────┘  └──────────┘ └──────────┘ │
└─────────────────────────────────────────────┘
```

## 1. Method Area (Shared across all threads)
- Stores **class metadata** (class name, parent, interfaces)
- Stores **static variables** and **method code**
- One per JVM — shared by all threads

## 2. Heap (Shared across all threads)
- Stores **objects** and **instance variables**
- Where `new` keyword allocates memory
- **Garbage Collector** cleans up unused objects here
- Largest memory area in the JVM

## 3. Java Stack (One per thread)
- Stores **method call frames** (local variables, return address)
- Each method call pushes a new frame; method return pops it
- **StackOverflowError** = too many nested calls (e.g., infinite recursion)

## 4. PC Register (One per thread)
- Tracks the **current instruction** being executed
- Points to the next bytecode instruction

## 5. Native Method Stack (One per thread)
- For **native (C/C++) methods** called via JNI
- Similar to Java Stack but for non-Java code

## Key Differences

| Area         | Shared? | Stores                    | Error When Full        |
|-------------|---------|---------------------------|------------------------|
| Method Area | Yes     | Class info, static vars   | OutOfMemoryError       |
| Heap        | Yes     | Objects, instance vars    | OutOfMemoryError       |
| Java Stack  | No      | Local vars, method frames | StackOverflowError     |
| PC Register | No      | Current instruction       | —                      |
| Native Stack| No      | Native method frames      | StackOverflowError     |
