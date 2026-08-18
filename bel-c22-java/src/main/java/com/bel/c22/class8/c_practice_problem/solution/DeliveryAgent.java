package com.bel.c22.class8.c_practice_problem.solution;

/**
 * DeliveryAgent — ASSOCIATION with Order (assigned to deliver, doesn't own the order).
 * Implements Trackable for live tracking.
 */
public class DeliveryAgent implements Trackable {
    private String name;
    private boolean available;
    private Order currentOrder;

    public DeliveryAgent(String name) {
        this.name = name;
        this.available = true;
        this.currentOrder = null;
    }

    public boolean isAvailable() { return available; }

    public void assignOrder(Order order) {
        if (!available) {
            System.out.println("  " + name + " is busy. Cannot assign order.");
            return;
        }
        this.currentOrder = order;
        this.available = false;
        System.out.println("  Delivery agent " + name + " assigned to " + order.getOrderId());
    }

    public void completeDelivery() {
        if (currentOrder == null) {
            System.out.println("  " + name + " has no active delivery.");
            return;
        }
        currentOrder.updateStatus(OrderStatus.DELIVERED);
        System.out.println("  " + name + " delivered " + currentOrder.getOrderId() + " successfully!");
        this.currentOrder = null;
        this.available = true;
    }

    // Trackable interface implementation
    @Override
    public String getTrackingInfo() {
        if (currentOrder != null) {
            return "Agent: " + name + " | Delivering: " + currentOrder.getOrderId() + " | Status: On the way";
        }
        return "Agent: " + name + " | Status: Available";
    }

    public String getName() { return name; }
}
