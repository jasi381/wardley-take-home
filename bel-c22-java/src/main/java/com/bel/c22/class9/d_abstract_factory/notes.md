# Abstract Factory Pattern

## Intent
Provide an interface for creating **families of related objects** without specifying
their concrete classes.

## Factory Method vs Abstract Factory
| | Factory Method | Abstract Factory |
|--|--|--|
| Creates | **One** product type | A **suite** of related products |
| Use when | Type chosen at runtime | Products must match as a consistent set |

Think of it as: **Abstract Factory = a Factory of Factories**.

## The Problem: Inconsistent Products
```java
// Mixed families — macOS button with Windows checkbox = visual mess
Button btn = new MacButton();
Checkbox chk = new WinCheckbox();   // ← wrong family!
```
Abstract Factory prevents this by grouping creation into one factory per family.

## Structure

```
<<interface>> UIFactory
    + createButton()    : Button
    + createCheckbox()  : Checkbox
    + createTextField() : TextField

MacUIFactory     implements UIFactory   → MacButton,  MacCheckbox,  MacTextField
WindowsUIFactory implements UIFactory   → WinButton,  WinCheckbox,  WinTextField

<<interface>> Button / Checkbox / TextField   ← Abstract Products
    MacButton / WinButton                     ← Concrete Products

Client (LoginForm)
    LoginForm(UIFactory factory)              ← receives factory via injection
    ← never imports Mac* or Win* directly
```

## How It Works (Step by Step)

1. Define **abstract product interfaces** — one per product kind.
2. Define **concrete products** grouped by family (Mac family, Windows family).
3. Define **abstract factory interface** — one `create*()` per product kind.
4. Define **concrete factories** — one per family, each returns its own products.
5. **Client** receives the factory (via constructor injection). Never uses `new` on concrete types.
6. A **factory selector** picks the right factory at startup (from config / OS / env var).

## Client is Completely Isolated
```java
class LoginForm {
    LoginForm(UIFactory factory) {
        this.btn = factory.createButton();    // gets Mac or Win, doesn't care
        this.chk = factory.createCheckbox();
    }
}
```
Swap `MacUIFactory` for `WindowsUIFactory` → entire UI changes; `LoginForm` unchanged.

## Adding a New Family
Add `LinuxUIFactory` + `LinuxButton` + `LinuxCheckbox` + `LinuxTextField`.
→ **Zero changes** to `LoginForm` or any existing factory.
→ Only the factory selector needs updating.

## Real-World Uses
- Java AWT/Swing — `UIManager.getLookAndFeel()` returns a themed component factory
- Database drivers — connection + statement + result-set must come from same driver family
- Game engines — render a consistent set of assets (textures, shaders, physics) per platform
- Cloud SDKs — AWS/GCP/Azure clients + loggers + auth handlers as matched sets

## When NOT to Use
- Products don't need to be consistent with each other → use simple Factory Method
- Only one product type → Factory Method is enough
- Families are added very rarely → the extra abstraction may not be worth it

## Quick Memory Aid
> "Abstract Factory enforces consistency.
>  You get a whole matching set, not just one object."
