package com.bel.c22.class8.c_practice_problem.solution;

/**
 * OrderItem — exists only as part of an Order (COMPOSITION).
 * Created by the Order, has no meaning without it.
 */
public class OrderItem {
    private MenuItem menuItem;
    private int quantity;

    // Package-private constructor: only Order should create OrderItems
    OrderItem(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return menuItem.getPrice() * quantity;
    }

    public String getItemName() { return menuItem.getName(); }
    public int getQuantity() { return quantity; }

    @Override
    public String toString() {
        return String.format("  %-25s x%d  = Rs %.2f",
                menuItem.getName(), quantity, getSubtotal());
    }
}
