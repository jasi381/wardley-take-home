package com.bel.c22.class8.c_practice_problem.solution;

/** Represents a food item available in a restaurant */
public class MenuItem {
    private String name;
    private double price;
    private Category category;

    public MenuItem(String name, double price, Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public Category getCategory() { return category; }

    @Override
    public String toString() {
        String tag = (category == Category.VEG) ? "[V]" :
                     (category == Category.NON_VEG) ? "[NV]" : "[B]";
        return String.format("  %s %-25s Rs %.2f", tag, name, price);
    }
}
