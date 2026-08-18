# Factory Method Pattern

## Intent
Define an interface for creating an object, but **let subclasses (or a factory class) decide**
which concrete class to instantiate.

## The Problem: `new` Everywhere
```java
// Caller is tightly coupled to a specific class
Notifier n = new EmailNotifier();

// Caller must change when you add SMS, Push, Slack...
if (channel.equals("email")) n = new EmailNotifier();
else if (channel.equals("sms")) n = new SMSNotifier();
// ← this if-else is repeated at EVERY call site
```

## The Solution: Factory
```java
Notifier n = NotifierFactory.create(channel);  // caller never writes `new`
// Add SlackNotifier? Only the factory changes. All callers stay the same.
```

## Two Common Variants

### A) Static Factory Method
A single class with a `static create()` method that switches on a type string/enum.

```java
static class NotifierFactory {
    public static Notifier create(String type) {
        return switch (type) {
            case "email" -> new EmailNotifier();
            case "sms"   -> new SMSNotifier();
            ...
        };
    }
}
```
- ✅ Simple. Easy to understand.
- ❌ Adding a new type means modifying this class (minor OCP violation).

### B) Factory Method
Abstract creator declares `createNotifier()`. Each subclass overrides it.

```java
abstract class NotificationService {
    protected abstract Notifier createNotifier();   // ← factory method

    public void notifyUser(String event) {
        Notifier n = createNotifier();               // polymorphic creation
        n.send(event);
    }
}

class EmailNotificationService extends NotificationService {
    protected Notifier createNotifier() { return new EmailNotifier(); }
}
```
- ✅ Adding new product = new subclass only. No changes to `NotificationService`.
- ✅ Complex creation logic can live in each subclass independently.
- ❌ More boilerplate than Static Factory.

## Structure

```
<<interface>> Notifier        ← Product interface
    EmailNotifier             ← Concrete Product
    SMSNotifier               ← Concrete Product

NotifierFactory               ← Creator (static factory variant)
    + create(type): Notifier  ← Factory Method
```

## Key Principle
> Callers depend on the **Product interface** (`Notifier`), not the concrete class
> (`EmailNotifier`). The factory is the only place that knows the concrete class.

## Real-World Uses
- `Calendar.getInstance()` — returns the right calendar type for the locale
- `NumberFormat.getInstance()` — locale-aware formatter
- JDBC `DriverManager.getConnection()` — returns the right DB driver
- Spring `BeanFactory.getBean()` — IoC container is a factory

## Factory Method vs Abstract Factory
| | Factory Method | Abstract Factory |
|--|--|--|
| Creates | **One** product type | A **family** of related products |
| Use when | Type is chosen at runtime | Products must be consistent as a set |

## Quick Memory Aid
> "Factory hides `new`. Caller asks for a product by name; factory decides the class."
