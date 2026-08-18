package com.bel.c22.class8.c_practice_problem.solution;

/**
 * Main — Ties everything together and demonstrates all relationships.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   ONLINE FOOD DELIVERY SYSTEM");
        System.out.println("========================================");

        // --- Step 1: Set up restaurant with menu items ---
        // AGGREGATION: Restaurant has MenuItems (items created outside, passed in)
        MenuItem butterChicken = new MenuItem("Butter Chicken", 350, Category.NON_VEG);
        MenuItem paneerTikka = new MenuItem("Paneer Tikka", 280, Category.VEG);
        MenuItem dalMakhani = new MenuItem("Dal Makhani", 220, Category.VEG);
        MenuItem chickenBiryani = new MenuItem("Chicken Biryani", 320, Category.NON_VEG);
        MenuItem masalaChai = new MenuItem("Masala Chai", 50, Category.BEVERAGE);

        Restaurant restaurant = new Restaurant("Punjab Grill");
        restaurant.addMenuItem(butterChicken);
        restaurant.addMenuItem(paneerTikka);
        restaurant.addMenuItem(dalMakhani);
        restaurant.addMenuItem(chickenBiryani);
        restaurant.addMenuItem(masalaChai);

        restaurant.displayMenu();

        // --- Step 2: Create customer ---
        Customer customer = new Customer("Rahul Sharma", "9876543210",
                "42, Koramangala, Bangalore");

        // --- Step 3: Customer places an order ---
        // ASSOCIATION: Customer uses Restaurant to place order
        Order order = customer.placeOrder(restaurant);

        // COMPOSITION: Order creates and owns OrderItems
        order.addItem(butterChicken, 1);
        order.addItem(paneerTikka, 2);
        order.addItem(masalaChai, 3);

        order.printOrderSummary();

        // --- Step 4: Process payment ---
        // POLYMORPHISM: Different payment types, same interface
        System.out.println("\n--- Payment ---");

        // Try invalid card first
        PaymentMethod badCard = new CreditCardPayment(order.getTotal(), "12345");
        boolean paid = badCard.processPayment();
        System.out.println("  Paid? " + paid);

        // Pay with UPI
        System.out.println();
        PaymentMethod upi = new UPIPayment(order.getTotal(), "rahul@upi");
        paid = upi.processPayment();
        if (paid) {
            upi.generateReceipt();  // Shared method from abstract class
        }

        // --- Step 5: Confirm order and assign delivery agent ---
        System.out.println("\n--- Delivery ---");
        order.updateStatus(OrderStatus.CONFIRMED);
        order.updateStatus(OrderStatus.PREPARING);

        // ASSOCIATION: DeliveryAgent is assigned to Order
        DeliveryAgent agent = new DeliveryAgent("Suresh Kumar");
        agent.assignOrder(order);
        order.updateStatus(OrderStatus.OUT_FOR_DELIVERY);

        // --- Step 6: Track order and agent ---
        // POLYMORPHISM via Trackable interface
        System.out.println("\n--- Tracking Info ---");
        Trackable[] trackables = { order, agent };
        for (Trackable t : trackables) {
            System.out.println("  " + t.getTrackingInfo());
        }

        // --- Step 7: Complete delivery ---
        System.out.println("\n--- Delivery Complete ---");
        agent.completeDelivery();

        // Final tracking
        System.out.println("\n--- Final Status ---");
        for (Trackable t : trackables) {
            System.out.println("  " + t.getTrackingInfo());
        }

        // --- Summary of relationships ---
        System.out.println("\n========================================");
        System.out.println("RELATIONSHIPS DEMONSTRATED:");
        System.out.println("  COMPOSITION:   Order → OrderItem (items owned by order)");
        System.out.println("  AGGREGATION:   Restaurant → MenuItem (items exist independently)");
        System.out.println("  ASSOCIATION:   Customer ↔ Order (uses, no ownership)");
        System.out.println("                 DeliveryAgent ↔ Order (assigned, not owned)");
        System.out.println("  INHERITANCE:   CreditCardPayment, UPIPayment → PaymentMethod");
        System.out.println("  ABSTRACTION:   PaymentMethod (abstract class)");
        System.out.println("  INTERFACE:     Trackable (Order and DeliveryAgent)");
        System.out.println("  POLYMORPHISM:  processPayment(), getTrackingInfo()");
        System.out.println("  ENCAPSULATION: All fields private with getters");
        System.out.println("  ENUMS:         Category, OrderStatus");
        System.out.println("========================================");
    }
}
