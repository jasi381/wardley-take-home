# Solution Guide — Online Food Delivery System

---

## 0. Recap the Problem (2 min)

Read the requirements out loud. Highlight the key nouns and verbs.

> *"A restaurant has a menu with items. A customer can browse and place an
> order. Calculate the total. Two payment methods with their own validation.
> A delivery agent gets assigned. The order moves through a lifecycle.
> Both order and agent should be 'trackable'."*

**Ask the class:** *"Before writing any code — what classes do we need?"*

Let them shout out names. Write them on the board. Don't filter yet.

---

## 1. Identify the Entities (5 min)

Group the student suggestions. The final list should look like this:

| Entity            | Why it exists                             |
| ----------------- | ----------------------------------------- |
| `MenuItem`        | A dish (name, price, type)                |
| `Restaurant`      | Holds a menu                              |
| `Customer`        | Places orders                             |
| `Order`           | A customer's request                      |
| `OrderItem`       | A line in an order (item + quantity)      |
| `DeliveryAgent`   | Picks up and delivers                     |
| `PaymentMethod`   | Abstract base for payments                |
| `Category`        | enum: VEG / NON_VEG / BEVERAGE            |
| `OrderStatus`     | enum: PLACED → DELIVERED                  |
| `Trackable`       | interface for anything trackable          |

**Teaching moment — why two enums?**
*"Why not just use strings like `"VEG"` or numbers like `1`?"*
→ Compile-time safety. The compiler catches typos. Auto-complete works.

**Teaching moment — why split `Order` and `OrderItem`?**
*"Couldn't an Order just hold a list of MenuItems?"*
→ It needs the **quantity** too. `OrderItem = MenuItem + quantity`. This is
a value object that only makes sense inside an Order.

---

## 2. Identify the Relationships (10 min)

This is the heart of the lesson. Go relationship by relationship.

For each pair, ask the class: **"Is this composition, aggregation, or
association? Why?"**

### 2a. `Restaurant` ↔ `MenuItem` → **AGGREGATION**

> *"If Punjab Grill shuts down tomorrow, does 'Butter Chicken' as a concept
> cease to exist?"*

No. The same dish could be on another restaurant's menu. The restaurant
**holds** menu items but doesn't **own** them. Items are created outside
and passed in.

```java
restaurant.addMenuItem(butterChicken);  // ← passed in from outside
```

**Lifecycle test:** Child can outlive the parent → aggregation.

### 2b. `Order` ↔ `OrderItem` → **COMPOSITION**

> *"If Order #1001 is cancelled, what happens to '2x Butter Chicken in
> Order #1001'?"*

It's meaningless. That line item exists *because of* that order. Outside
code never creates an `OrderItem` directly — the `Order` creates them
internally.

```java
order.addItem(butterChicken, 2);  // ← Order creates the OrderItem inside
```

Notice: the `OrderItem` constructor is **package-private** to enforce this.

**Lifecycle test:** Child cannot exist without parent → composition.

### 2c. `Customer` ↔ `Order` → **ASSOCIATION**

> *"Does the customer own the order? Does the order own the customer?"*

Neither. They interact. Customer existed before this order, will exist
after. Order doesn't control the Customer's lifecycle and vice versa.

**Lifecycle test:** Independent lifecycles, no ownership → association.

### 2d. `DeliveryAgent` ↔ `Order` → **ASSOCIATION**

Same story. The agent delivers many orders over time. Temporary assignment,
no ownership.

### 2e. `CreditCardPayment`, `UPIPayment` → `PaymentMethod` → **INHERITANCE**

> *"All payments share what? What's different?"*

- **Same:** the amount, generating a receipt.
- **Different:** the validation and processing logic.

That's exactly when inheritance with an abstract class fits:
- Shared state (`amount`) → field on parent
- Shared behavior (`generateReceipt`) → concrete method on parent
- Differing behavior (`processPayment`) → abstract method, each subclass implements

### 2f. `Order`, `DeliveryAgent` → `Trackable` → **INTERFACE**

> *"Order and DeliveryAgent have nothing else in common. Why use an interface?"*

Because tracking is a **capability**, not an **identity**. They don't share
a hierarchy, they share a contract. Interface is the right tool when:
- The classes are otherwise unrelated
- You want to treat them uniformly (`Trackable[] trackables = {order, agent}`)

---

## 3. Build Order — Foundation First (10 min)

Always build bottom-up: things with no dependencies first.

### Step 1 — Enums (no dependencies)

```java
public enum Category { VEG, NON_VEG, BEVERAGE }
public enum OrderStatus { PLACED, CONFIRMED, PREPARING, OUT_FOR_DELIVERY, DELIVERED }
```

### Step 2 — Value object: `MenuItem`

```java
public class MenuItem {
    private String name;
    private double price;
    private Category category;
    // constructor + getters
}
```

**Ask:** *"Should MenuItem have setters?"* → No. Once a dish is defined,
the price/name doesn't change for that instance. Immutable value object.

### Step 3 — `Restaurant` (aggregates MenuItems)

```java
public class Restaurant {
    private List<MenuItem> menu;
    public void addMenuItem(MenuItem item) { menu.add(item); }
}
```

**Ask:** *"What if I write `getMenu()` that returns the list directly?"*
→ External code could mutate it. That breaks encapsulation. Either return
a copy or expose only specific operations (`displayMenu`, `getMenuItem(i)`).

---

## 4. Build the Order Pair (5 min)

### `OrderItem` — package-private constructor

```java
public class OrderItem {
    private MenuItem menuItem;
    private int quantity;
    OrderItem(MenuItem item, int qty) { ... }   // ← no `public`
    public double getSubtotal() { return menuItem.getPrice() * quantity; }
}
```

> *"Why no `public` on the constructor?"*

So only classes in the same package (i.e., `Order`) can create them.
This is how we **enforce** composition at the language level.

### `Order` — owns the items, tracks the lifecycle, is Trackable

```java
public class Order implements Trackable {
    private String orderId;
    private Customer customer;
    private List<OrderItem> items;
    private OrderStatus status;

    public void addItem(MenuItem item, int qty) {
        items.add(new OrderItem(item, qty));   // ← Order creates OrderItem
    }

    public double getTotal() { /* sum subtotals */ }
    public void updateStatus(OrderStatus next) { this.status = next; }

    @Override
    public String getTrackingInfo() { return "Order " + orderId + " | " + status; }
}
```

**Ask:** *"Why `updateStatus()` instead of `setStatus()`?"*
→ Naming hints intent. Later we could add validation:
*"You can't go from PLACED straight to DELIVERED."* A setter implies a dumb
field write; `updateStatus` reads as a controlled state transition.

---

## 5. Build Customer & DeliveryAgent (5 min)

### `Customer` — placeOrder() returns a new Order

```java
public Order placeOrder(Restaurant restaurant) {
    return new Order(this);
}
```

Notice: customer doesn't *store* the order. They hand it off and walk away.
That's association, not composition.

### `DeliveryAgent` — assigned to one order at a time, also Trackable

```java
public class DeliveryAgent implements Trackable {
    private boolean available;
    private Order currentOrder;

    public void assignOrder(Order o) {
        if (!available) return;
        this.currentOrder = o;
        this.available = false;
    }

    public void completeDelivery() {
        currentOrder.updateStatus(OrderStatus.DELIVERED);
        currentOrder = null;
        available = true;
    }
}
```

> *"Could the agent hold a list of orders?"*

Yes — but the requirement says one at a time, so we keep it simple
(YAGNI from class6).

---

## 6. Build the Payment Hierarchy (10 min)

### Abstract parent

```java
public abstract class PaymentMethod {
    protected double amount;
    public abstract boolean processPayment();   // ← differs per type
    public void generateReceipt() { ... }       // ← shared, concrete
}
```

### Two concrete subclasses

```java
public class CreditCardPayment extends PaymentMethod {
    @Override public boolean processPayment() {
        if (cardNumber.length() != 16) return false;
        // process...
    }
}

public class UPIPayment extends PaymentMethod {
    @Override public boolean processPayment() {
        if (!upiId.contains("@")) return false;
        // process...
    }
}
```

### Why this design? (Open/Closed Principle from class6)

To add **WalletPayment**, you create one new file. You don't touch
`PaymentMethod`, `CreditCardPayment`, `UPIPayment`, `Order`, or `Main`.
That's the magic of polymorphism + abstract methods.

```java
PaymentMethod pm = new UPIPayment(500, "rahul@upi");
pm.processPayment();   // ← caller doesn't know or care which type
```

---

## 7. The Trackable Interface (3 min)

```java
public interface Trackable {
    String getTrackingInfo();
}
```

That's it. One method. Now in `Main`:

```java
Trackable[] trackables = { order, agent };   // ← totally different classes!
for (Trackable t : trackables) {
    System.out.println(t.getTrackingInfo());
}
```

**This is the payoff.** A loop that treats unrelated types uniformly.
Try doing this with inheritance — you can't, because `Order` and
`DeliveryAgent` don't share a parent (and Java has no multiple inheritance).

---

## 8. Wire It Up in `Main` (5 min)

Walk through the flow:

1. Create `MenuItem`s → add to `Restaurant`               *(aggregation)*
2. Create `Customer`
3. `customer.placeOrder(restaurant)` → returns `Order`     *(association)*
4. `order.addItem(menuItem, qty)` × N                      *(composition)*
5. `order.printOrderSummary()` — verify totals
6. Try a **bad** card → payment fails (validation works!)
7. Pay via UPI → success → `generateReceipt()`             *(inherited method)*
8. `order.updateStatus(CONFIRMED → PREPARING)`
9. `agent.assignOrder(order)`                              *(association)*
10. `order.updateStatus(OUT_FOR_DELIVERY)`
11. Track both via `Trackable[]`                           *(polymorphism)*
12. `agent.completeDelivery()` → status becomes DELIVERED

---

## 9. Recap the Big Ideas (5 min)

| Concept                      | Where you saw it in this problem        |
| ---------------------------- | --------------------------------------- |
| **Composition**              | `Order` → `OrderItem`                   |
| **Aggregation**              | `Restaurant` → `MenuItem`               |
| **Association**              | `Customer` ↔ `Order`, `Agent` ↔ `Order` |
| **Inheritance**              | `CreditCardPayment` extends `PaymentMethod` |
| **Abstraction (abstract)**   | `PaymentMethod`                         |
| **Abstraction (interface)**  | `Trackable`                             |
| **Polymorphism**             | `processPayment()`, `getTrackingInfo()` |
| **Encapsulation**            | All fields private, controlled mutation |
| **Enums for fixed sets**     | `Category`, `OrderStatus`               |
| **Open/Closed (class6)**     | Add new payment type = one new file     |
| **Single Responsibility**    | Each class does one thing well          |

---

## 10. Discussion Questions (open-ended, 5 min)

Pick 2-3 depending on time.

1. **The Restaurant has a `getMenuItem(int index)` getter. Is that good
   encapsulation?** What's a better signature?
   *(Hint: returning by index leaks the internal data structure.)*

2. **Right now `Order.updateStatus()` accepts any status.** How would you
   prevent illegal transitions like `PLACED → DELIVERED`? *(Validation
   inside `updateStatus`, or look up the **State pattern** in class10.)*

3. **Where would you put a discount/coupon system?** On `Order`?
   `OrderItem`? A separate `DiscountStrategy`? *(Foreshadow the
   **Strategy pattern**.)*

4. **What if a customer wants to split payment across two methods?**
   How does that change the design? *(Hint: `Order` would hold a
   `List<PaymentMethod>` instead of one.)*

5. **`DeliveryAgent` has a `Order currentOrder` field — is that
   composition or association?** *(Association. The agent doesn't create
   or destroy orders.)*

6. **Why isn't `Trackable` an abstract class?** *(Because `Order` and
   `DeliveryAgent` have no common state or behavior to share. And Java
   forbids multiple inheritance — if either later needed to extend
   another class, an abstract `Trackable` would block them.)*

---

## 11. Extension Exercises (homework / next session)

Pick one to give as homework:

- **Easy:** Add a `WalletPayment` class. *(Goal: see how OCP makes this
  trivial — no other file changes.)*

- **Medium:** Add a `RatingService` so customers can rate the agent and
  the restaurant after delivery. *(Goal: practice association + value
  objects.)*

- **Hard:** Add the **State pattern** to `Order` so the status transitions
  enforce themselves. *(Goal: foreshadow class10.)*

---

## Cheat-sheet for the whiteboard

```
                  ┌──────────────┐
        creates   │   Customer   │  uses
       ┌─────────►│              │◄────────┐
       │          └──────────────┘         │
       │                                    │
       ▼                                    │
  ┌─────────┐ COMPOSITION ┌───────────┐    │
  │  Order  │◄────────────│ OrderItem │    │
  │         │             └───────────┘    │
  │         │                              │
  │ <<Trackable>>                          │
  └────┬────┘                              │
       │ assigned to                       │
       │                                   │
  ┌────▼──────────┐                        │
  │ DeliveryAgent │                        │
  │ <<Trackable>> │                        │
  └───────────────┘                        │
                                           │
  ┌────────────┐ AGGREGATION ┌──────────┐  │
  │ Restaurant │────────────►│ MenuItem │──┘
  └────────────┘             └──────────┘

  ┌──────────────────┐
  │  PaymentMethod   │  (abstract)
  │  +amount         │
  │  +generateReceipt│
  │  *processPayment │
  └────────┬─────────┘
           ▲
     ┌─────┴───────────┐
     │                 │
┌─────────────┐   ┌──────────┐
│ CreditCard  │   │   UPI    │
│  Payment    │   │ Payment  │
└─────────────┘   └──────────┘
```
