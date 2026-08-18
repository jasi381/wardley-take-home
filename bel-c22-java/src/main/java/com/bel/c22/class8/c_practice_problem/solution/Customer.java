package com.bel.c22.class8.c_practice_problem.solution;

/**
 * Customer — ASSOCIATION with Order and Restaurant.
 * Customer uses them but doesn't own them.
 */
public class Customer {
    private String name;
    private String phone;
    private String address;

    public Customer(String name, String phone, String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    // Customer places an order — creates an Order (association, not composition)
    public Order placeOrder(Restaurant restaurant) {
        System.out.println("\n  " + name + " is ordering from " + restaurant.getName());
        return new Order(this);
    }

    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
}
