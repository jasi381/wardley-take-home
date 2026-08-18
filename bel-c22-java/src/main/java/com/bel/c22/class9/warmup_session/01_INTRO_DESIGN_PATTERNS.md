
## Part 1 — What is a "Design Pattern"?

Imagine you're cooking. You don't reinvent how to boil rice every time — there's a
**well-known recipe** that works. You can tweak it, but you start from the recipe.

A **design pattern** is the same idea, but for code:

> A **proven, reusable solution** to a problem that programmers keep running into.

Three things to remember:

1. **A pattern is an IDEA, not code.** It's a template you adapt — not a library you import.
2. **Patterns have NAMES.** When you say "use a Singleton here", another dev instantly
   knows what you mean. They are a **shared vocabulary**.
3. **They came from real pain.** In 1994, four engineers ("Gang of Four") collected
   23 patterns they kept seeing in good codebases. We still use most of them today.

### Patterns vs. Principles

You just learned **design principles** (SOLID, DRY, KISS, etc.) in the last class.

| Principles | Patterns |
|---|---|
| Rules to follow ("don't repeat yourself") | Reusable structures ("here's HOW to not repeat yourself in scenario X") |
| Tell you **what** good code looks like | Tell you **how** to achieve it |

> Think of principles as the *grammar* of good code, and patterns as the *sentences* you build with them.

---

## Part 2 — Why Bother? 

Without patterns, every developer solves the same problem differently. With patterns:

- ✅ You don't reinvent the wheel.
- ✅ Code becomes **predictable** — anyone reading it sees a familiar shape.
- ✅ Easier to extend later (most patterns are designed for change).
- ✅ Communication is faster: "let's use a Builder here" >> 5 minutes of explaining.

### Three Families of Patterns

| Family | Concerned with… | Examples |
|---|---|---|
| **Creational** | How objects are *created* | Singleton, Builder, Factory, Abstract Factory |
| **Structural** | How objects are *composed* together | Adapter, Decorator, Proxy |
| **Behavioral** | How objects *interact* with each other | Observer, Strategy, Command |

> Today is all about **CREATIONAL** patterns — patterns that answer the question:
> *"How should I create this object?"*

---

## Part 3 — Plant the Seeds: The 4 Patterns We'll Cover 

For each pattern, just remember **the smell** — the kind of problem that hints at it.
Don't worry about implementation yet. We'll do that after the practice problem.

---

### 🌱 Seed 1 — SINGLETON

**Real-world scene:**
A college has many classrooms, many teachers, many students…
but there is exactly **ONE Principal**. Everyone in the school refers to "the Principal" —
not "a principal".

**The smell in code:**
> "There must be **only one** of this thing in the entire program."

**Examples in real software:**
- One Logger writing to one log file
- One DatabaseConnectionPool shared across the app
- One AppConfig holding settings
- One CacheManager

**Idea (one line):**
The class itself controls its creation. Whoever asks always gets back **the same instance**.

```java
ConfigManager c1 = ConfigManager.getInstance();
ConfigManager c2 = ConfigManager.getInstance();
// c1 == c2  → TRUE. Same object.
```

---

### 🌱 Seed 2 — BUILDER

**Real-world scene:**
You're ordering a Subway sandwich. The cashier asks:
*Bread? Cheese? Veggies? Sauces? Toasted? Footlong?*
You answer the ones you care about, skip the rest, and at the end say "**that's it**".

**The smell in code:**
> "This object has **so many configuration options** that the constructor is becoming unreadable."

```java
// 😱 Awful
new EmailMessage("hi", "you@x.com", "me@x.com", null, null, true, false, 5, "high");

// 😎 Beautiful
new EmailMessage.Builder("hi", "you@x.com")
        .priority("high")
        .retries(5)
        .build();
```

**Examples in real software:**
- Building HTTP requests (`HttpRequest.Builder`)
- Building a complex form/document
- Configuring an immutable object step by step

**Idea (one line):**
Construct the object **step by step**, then call `build()` to lock it in (often immutable).

---

### 🌱 Seed 3 — FACTORY

**Real-world scene:**
You walk into a coffee shop and say *"one latte"*. You don't grind beans, steam milk,
or pour foam yourself. The barista hides all that. You just **named what you want**.

**The smell in code:**
> "Based on **input**, I need to create one of several similar things — and the caller
> shouldn't care about the `new` keyword or which exact class is used."

```java
// Without Factory — caller knows about every concrete class
PaymentHandler h;
if (method.equals("upi"))      h = new UPIHandler();
else if (method.equals("card"))h = new CardHandler();
else if (method.equals("paypal"))h = new PayPalHandler();
// ↑ this if-else lives in EVERY caller. Ugly.

// With Factory — caller just says what they want
PaymentHandler h = PaymentFactory.create(method);
```

**Examples in real software:**
- Creating the right database driver based on a connection string
- Creating the right notification sender based on user preference
- Creating the right shape from a JSON `"type"` field

**Idea (one line):**
Hide the `new`. A central factory decides **which class to instantiate** based on input.

---

### 🌱 Seed 4 — ABSTRACT FACTORY

**Real-world scene:**
Your phone has a **theme**. In *Light mode* — buttons, backgrounds, and icons all look light.
In *Dark mode* — they all look dark. You can't have a *light button on a dark dialog* — they
**come in matched families**, and one switch picks the whole family.

**The smell in code:**
> "I need to create **a group of related objects** that must belong to the **same family** —
> and which family is decided once, up front."

```java
ThemeFactory factory = isDark ? new DarkTheme() : new LightTheme();

Button   b = factory.createButton();   // dark button (or light)
Checkbox c = factory.createCheckbox(); // dark checkbox (or light)
// They MATCH because they came from the same factory.
```

**Examples in real software:**
- UI toolkit per OS (Mac vs Windows vs Linux widgets)
- Cloud SDK per provider (AWS Storage + AWS Auth must come together; same for GCP)
- Game assets per theme (Medieval set vs Sci-Fi set — character + weapon + armor all match)

**Idea (one line):**
A factory that creates **a family of matched products**, picked once and used together.

---

## Part 4 — Quick Mental Map

When you face a "how do I create this object?" question, ask:

```
Should there be only ONE in the whole program?           → SINGLETON
Does it have lots of optional knobs to set?              → BUILDER
Does the choice depend on input/config at runtime?       → FACTORY
Do I need a whole MATCHED FAMILY of related objects?     → ABSTRACT FACTORY
```

That's it. Now keep this map open in your head — you're going to see a problem next, and
your job is to spot **where each of these patterns fits**.

> Don't write code yet. Just **think and identify**. We'll discuss the full implementations
> after you've taken your shot.
