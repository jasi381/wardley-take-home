package com.bel.c22.class8.c_practice_problem.solution;

import java.util.ArrayList;
import java.util.List;

/**
 * Order — COMPOSITION with OrderItem (items are created and owned by the order).
 * ASSOCIATION with Customer (order references customer but doesn't own them).
 * Implements Trackable for delivery tracking.
 */
public class Order implements Trackable {
    private static int counter = 1000;

    private String orderId;
    private Customer customer;
    private List<OrderItem> items;      // Composition: owns order items
    private OrderStatus status;

    public Order(Customer customer) {
        this.orderId = "ORD-" + (++counter);
        this.customer = customer;
        this.items = new ArrayList<>();
        this.status = OrderStatus.PLACED;
    }

    // Order creates the OrderItem internally — composition
    public void addItem(MenuItem menuItem, int quantity) {
        items.add(new OrderItem(menuItem, quantity));
    }

    public double getTotal() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    public void updateStatus(OrderStatus newStatus) {
        System.out.println("  Order " + orderId + ": " + status + " → " + newStatus);
        this.status = newStatus;
    }

    public void printOrderSummary() {
        System.out.println("\n--- Order: " + orderId + " ---");
        System.out.println("  Customer: " + customer.getName());
        System.out.println("  Deliver to: " + customer.getAddress());
        System.out.println("  Items:");
        for (OrderItem item : items) {
            System.out.println("    " + item);
        }
        System.out.printf("  TOTAL: Rs %.2f%n", getTotal());
        System.out.println("  Status: " + status);
        System.out.println("---------------------------");
    }

    // Trackable interface implementation
    @Override
    public String getTrackingInfo() {
        return "Order " + orderId + " | Status: " + status +
               " | Customer: " + customer.getName() +
               " | Items: " + items.size();
    }

    public String getOrderId() { return orderId; }
    public OrderStatus getStatus() { return status; }
}
