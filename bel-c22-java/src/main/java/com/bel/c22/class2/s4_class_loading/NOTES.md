# Class Loading in JVM

## Three Types of Class Loaders

```
   Bootstrap ClassLoader        (loads core Java: java.lang, java.util)
          ▲
          │  parent
   Extension/Platform ClassLoader  (loads ext libraries)
          ▲
          │  parent
   Application ClassLoader      (loads YOUR classes from classpath)
```

## 1. Bootstrap ClassLoader
- Loads **core Java classes** (`java.lang.String`, `java.util.List`)
- Written in C/C++ (not a Java class)
- Returns `null` when queried: `String.class.getClassLoader()` → `null`
- Loads from `rt.jar` (Java 8) or `java.base` module (Java 9+)

## 2. Extension (Platform) ClassLoader
- Loads classes from `lib/ext/` directory
- Parent: Bootstrap ClassLoader
- In Java 9+, called **Platform ClassLoader**

## 3. Application (System) ClassLoader
- Loads **your classes** from the classpath (`-cp` or `CLASSPATH`)
- Parent: Extension ClassLoader
- `MyClass.class.getClassLoader()` → `AppClassLoader`

## Parent Delegation Model
When a class needs to be loaded:
1. Application ClassLoader asks its parent (Platform)
2. Platform asks its parent (Bootstrap)
3. Bootstrap tries to load — if it can't, control goes back down
4. Platform tries — if it can't, Application tries
5. If nobody can load it → `ClassNotFoundException`

**Why?** This prevents user code from overriding core Java classes (security!).

## Class Loading Process
1. **Loading** — find and read the `.class` file
2. **Linking** — verify, prepare, and resolve (see s5)
3. **Initialization** — run static blocks and assign static values (see s6)
