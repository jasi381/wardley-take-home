package com.bel.c22.class8.b_association_aggregation_composition;

import java.util.ArrayList;
import java.util.List;

/**
 * COMPOSITION — "has-a" (strong ownership) relationship
 *
 * One object OWNS another. The contained object CANNOT exist without the container.
 * The container controls the lifecycle — it creates and destroys the contained objects.
 * If the container dies, all contained objects die too.
 *
 * Real-world analogy: A House has Rooms.
 * - Rooms are CREATED when the house is built.
 * - Rooms CANNOT exist without the house.
 * - If the house is demolished, all rooms are destroyed.
 * - You can't take a room and put it in another house!
 *
 * KEY CHARACTERISTICS:
 * - Strong "has-a" — container OWNS the parts
 * - Child lifecycle is DEPENDENT on parent
 * - Child is typically created INSIDE the parent (in constructor/methods)
 * - Deleting parent DELETES all children
 * - Child cannot belong to multiple parents simultaneously
 */
public class CompositionDemo {

    static class Room {
        private String name;
        private double areaSqFt;

        Room(String name, double areaSqFt) {
            this.name = name;
            this.areaSqFt = areaSqFt;
        }

        public String toString() { return name + " (" + areaSqFt + " sq ft)"; }
    }

    static class House {
        private String address;
        private List<Room> rooms;  // House OWNS rooms (composition)

        House(String address) {
            this.address = address;
            this.rooms = new ArrayList<>();

            // Rooms are created BY the house — they don't exist independently
            rooms.add(new Room("Living Room", 300));
            rooms.add(new Room("Bedroom", 200));
            rooms.add(new Room("Kitchen", 150));
            rooms.add(new Room("Bathroom", 80));

            System.out.println("  House built at: " + address);
            System.out.println("  Rooms created: " + rooms);
        }

        void addRoom(String name, double area) {
            // House creates the room — no external room can be added
            rooms.add(new Room(name, area));
            System.out.println("  Added room: " + name);
        }

        void demolish() {
            System.out.println("  Demolishing house at: " + address);
            rooms.clear();  // All rooms are destroyed with the house
            System.out.println("  All rooms destroyed. rooms.size() = " + rooms.size());
        }

        // No getRoom() that returns the Room itself — we don't leak ownership
        void showRooms() {
            System.out.println("  Rooms in " + address + ": " + rooms);
        }
    }

    // --- Another example: Order and OrderItems ---

    static class OrderItem {
        private String product;
        private int quantity;
        private double price;

        OrderItem(String product, int qty, double price) {
            this.product = product;
            this.quantity = qty;
            this.price = price;
        }

        double getTotal() { return quantity * price; }
        public String toString() { return product + " x" + quantity; }
    }

    static class Order {
        private String orderId;
        private List<OrderItem> items;  // Order OWNS items (composition)

        Order(String orderId) {
            this.orderId = orderId;
            this.items = new ArrayList<>();
        }

        // Items are created through the order — not passed from outside
        void addItem(String product, int qty, double price) {
            items.add(new OrderItem(product, qty, price));
        }

        double getTotal() {
            return items.stream().mapToDouble(OrderItem::getTotal).sum();
        }

        void cancel() {
            System.out.println("  Cancelling order " + orderId);
            items.clear();  // All items are destroyed — they have no meaning without the order
            System.out.println("  All order items removed.");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== COMPOSITION: 'has-a' (strong) relationship ===\n");

        // Example 1: House and Rooms
        System.out.println("1. House and Rooms:");
        House house = new House("123 MG Road, Bangalore");
        house.addRoom("Study", 120);
        house.showRooms();

        System.out.println();
        house.demolish();
        // Rooms don't exist anymore! You can't access them independently.

        // Example 2: Order and OrderItems
        System.out.println("\n2. Order and OrderItems:");
        Order order = new Order("ORD-001");
        order.addItem("Laptop", 1, 75000);
        order.addItem("Mouse", 2, 500);
        System.out.printf("  Order total: Rs %.2f%n", order.getTotal());

        order.cancel();  // Items cease to exist

        System.out.println("\n  Key point: Rooms were created BY the house, not passed in.");
        System.out.println("  House demolished → rooms destroyed.");
        System.out.println("  Order cancelled → items destroyed.");
        System.out.println("  Children CANNOT exist without the parent.\n");

        System.out.println("  Other examples of composition:");
        System.out.println("  - Car has Engine (engine is built for that car)");
        System.out.println("  - Invoice has LineItems (no meaning without invoice)");
        System.out.println("  - Human has Heart (heart can't exist independently)");
    }
}
