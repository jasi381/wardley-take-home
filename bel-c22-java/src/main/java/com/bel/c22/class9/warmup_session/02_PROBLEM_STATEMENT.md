# 🍕 Practice Problem — "Slice of Heaven" Pizza Ordering App

> ⏱️ **Time budget:** 30–35 minutes
> 🎯 **Goal:** Don't try to write production-grade code. Just sketch classes that
> apply each of the 4 creational patterns we briefly covered. Diagrams + rough Java
> are perfectly fine.

---

## The Story

You're building the backend for a small pizzeria called **"Slice of Heaven"**.
It's a single-restaurant app (not a chain). Customers can build their own pizza
or pick a preset. The shop offers two "themed" meal combos: **Italian** and **Mexican**,
each pairing a pizza style with a matching side.

That's the whole product. Now design the classes.

---

## Requirements

### 1. Restaurant Settings — `Pizzeria`

The pizzeria has shop-wide settings that the rest of the app needs to read:

- `shopName` (e.g. `"Slice of Heaven"`)
- `currency` (e.g. `"INR"`)
- `deliveryRadiusKm` (e.g. `5`)

Anywhere in the code — when a bill is printed, when an order is logged — we should
be able to read these settings. There should be **only one** `Pizzeria` instance
for the whole program. Nobody should be able to accidentally create a second one.

---

### 2. Custom Pizza — `Pizza`

A pizza has:

- **Required**: `size` (`"small"` / `"medium"` / `"large"`), `crust` (`"thin"` / `"thick"` / `"cheeseburst"`)
- **Optional**: `extraCheese` (boolean), `toppings` (a list of strings),
  `spicyLevel` (0–5), `glutenFree` (boolean)

The catch: **most fields are optional**, and a constructor with 6 parameters where 4
of them are usually defaults would be horrible to call. We also want each Pizza to
be **immutable** once created.

We'd like to write code like this:

---

### 3. Preset Pizzas 

90% of customers don't customize — they just say "give me a Margherita". The shop
has three presets:

- `"margherita"` → medium / thin / extra cheese / no toppings / spicy 0
- `"pepperoni"`  → medium / thick / extra cheese / topping `"pepperoni"` / spicy 2
- `"veggie"`     → medium / thin / no extra cheese / toppings `"olives", "mushrooms", "capsicum"` / spicy 1


---

### 4. Themed Meal Combos 

Every meal combo has a **pizza + a side**, and they must come from the **same theme**.
You can't mix an Italian pizza with a Mexican side.

| Theme | Pizza | Side |
|---|---|---|
| Italian | Margherita | Garlic Bread |
| Mexican | Pepperoni  | Nachos       |

The customer picks the theme **once** — and the right pizza + side are produced
together as a matched pair.

---

## Your Tasks (in order)

Outline the class structures. You don't need to
   compile. Pseudocode / partial Java is fine.

(only if time permits) — Write a `main()` that:
   - Prints the pizzeria settings via the singleton.
   - Builds one custom pizza using the Builder.
   - Creates one preset pizza using the simple Factory.
   - Creates one Italian combo and one Mexican combo using the Abstract Factory.

---
