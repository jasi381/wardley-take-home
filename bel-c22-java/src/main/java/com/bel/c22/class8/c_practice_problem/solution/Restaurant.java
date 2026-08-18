package com.bel.c22.class8.c_practice_problem.solution;

import java.util.ArrayList;
import java.util.List;

/**
 * Restaurant — AGGREGATION with MenuItem.
 * The restaurant HAS menu items, but menu items can exist independently
 * (same item could appear in another restaurant's menu).
 */
public class Restaurant {
    private String name;
    private List<MenuItem> menu;  // Aggregation: items passed in from outside

    public Restaurant(String name) {
        this.name = name;
        this.menu = new ArrayList<>();
    }

    // Menu items are created outside and added — aggregation indicator
    public void addMenuItem(MenuItem item) {
        menu.add(item);
    }

    public void removeMenuItem(MenuItem item) {
        menu.remove(item);
    }

    public void displayMenu() {
        System.out.println("\n--- " + name + " Menu ---");
        for (int i = 0; i < menu.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + menu.get(i));
        }
        System.out.println("----------------------------");
    }

    public MenuItem getMenuItem(int index) {
        if (index >= 0 && index < menu.size()) return menu.get(index);
        return null;
    }

    public String getName() { return name; }
}
