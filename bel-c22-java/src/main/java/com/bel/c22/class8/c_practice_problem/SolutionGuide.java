package com.bel.c22.class8.c_practice_problem;

/**
 * ============================================================================
 * SOLUTION WALKTHROUGH — Why the Code is Written This Way
 * ============================================================================
 *
 *
 * ============================================================================
 * ENTITIES
 * ============================================================================
 *
 * MenuItem     — Represents a food item with a name, price, and category.
 *                Category is an enum (VEG, NON_VEG, BEVERAGE) for type safety.
 *
 * Restaurant   — Has a name and holds a collection of MenuItems.
 *
 * OrderItem    — Represents a specific item inside an order with a quantity.
 *                Knows how to calculate its own subtotal (price × quantity).
 *
 * Order        — Represents a customer's order. Holds OrderItems, tracks its
 *                status through a lifecycle (PLACED → CONFIRMED → PREPARING →
 *                OUT_FOR_DELIVERY → DELIVERED). Status is an enum.
 *
 * Customer     — Has a name, phone, and delivery address. Can place orders.
 *
 * DeliveryAgent — Has a name and availability. Gets assigned to orders
 *                 and marks them as delivered.
 *
 * PaymentMethod — Abstract class with an amount. Subclasses implement their
 *                 own processPayment() logic. generateReceipt() is shared.
 *
 * CreditCardPayment — Validates card number is 16 digits, then processes.
 *
 * UPIPayment   — Validates UPI ID contains '@', then processes.
 *
 * Trackable    — Interface with getTrackingInfo(). Implemented by both
 *                Order and DeliveryAgent.
 *
 *
 * ============================================================================
 * RELATIONSHIPS
 * ============================================================================
 *
 * Restaurant → MenuItem                                      AGGREGATION
 *   MenuItems are created outside the Restaurant and added to it.
 *   "Butter Chicken" is a concept that exists independently — it doesn't
 *   get destroyed if the restaurant shuts down. The same item could appear
 *   in multiple restaurants. The Restaurant doesn't create MenuItems
 *   internally — it receives them from outside.
 *   Weak "has-a": child outlives the parent.
 *
 * Order → OrderItem                                          COMPOSITION
 *   OrderItems are created by the Order itself. "2x Butter Chicken in
 *   Order #1001" has no meaning without that specific order. If the order
 *   is cancelled, those line items cease to exist. That's why addItem()
 *   takes a MenuItem and quantity, and the Order creates the OrderItem
 *   internally. Outside code never creates an OrderItem directly.
 *   Strong "has-a": children cannot exist without the parent.
 *
 * Customer ↔ Order                                           ASSOCIATION
 *   A Customer places an Order, but neither side owns the other. The
 *   Customer exists long before and after any particular order. They
 *   interact but their lifecycles are completely independent.
 *   "Uses-a" relationship with no ownership.
 *
 * DeliveryAgent ↔ Order                                      ASSOCIATION
 *   An agent is assigned to deliver an order temporarily. The agent
 *   delivers many orders over time and is not tied to any single one.
 *   Neither controls the other's lifecycle.
 *   "Uses-a" relationship with no ownership.
 *
 * CreditCardPayment, UPIPayment → PaymentMethod              INHERITANCE
 *   PaymentMethod is abstract because all payment types share common
 *   state (the amount) and common behavior (generating a receipt). Only
 *   the actual processing differs — so processPayment() is abstract while
 *   generateReceipt() is concrete and inherited. Adding a new payment type
 *   means creating one new file — no existing code changes.
 *
 * Order, DeliveryAgent → Trackable                           INTERFACE
 *   These are completely unrelated classes that both need to provide
 *   tracking information. An interface is used because this is a shared
 *   capability ("can be tracked"), not a shared identity. It lets us treat
 *   both types uniformly — e.g., looping through a Trackable[] array and
 *   calling getTrackingInfo() on each regardless of the actual type.
 *
 *
 * ============================================================================
 * DESIGN DECISIONS
 * ============================================================================
 *
 * Why enums for Category and OrderStatus?
 *   These are fixed sets of values. Enums give compile-time safety — you
 *   can't accidentally write "veg" instead of "VEG" because the compiler
 *   catches it. OrderStatus.PREPARING is also clearer than status = 2.
 *
 * Why abstract class for PaymentMethod instead of interface?
 *   Because payment types share common state (amount) and common behavior
 *   (generateReceipt). An interface can't hold instance state. Abstract
 *   class lets subclasses inherit the shared parts and only override what
 *   differs.
 *
 * Why interface for Trackable instead of abstract class?
 *   Order and DeliveryAgent have nothing in common except this one
 *   capability. Java doesn't allow multiple inheritance, so if these classes
 *   already extend something else, an abstract class wouldn't work.
 *   Interface is the right tool for shared capability across unrelated types.
 *
 * Why are all fields private?
 *   Encapsulation. Access is through getters, and only where needed. Order
 *   doesn't expose setStatus() — it has updateStatus() which could include
 *   validation. The internal list of OrderItems is never returned directly,
 *   so outside code can't accidentally modify the order's contents.
 *
 *
 * ============================================================================
 * Now look at the 'solution' package to see the full implementation.
 * ============================================================================
 */
public class SolutionGuide {
    // This file is intentionally left as documentation only.
}
