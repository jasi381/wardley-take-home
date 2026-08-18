package com.bel.c22.class5.f_practice_problem.solution;

// Encapsulation — private fields, public getters
public class Instructor {
    private int id;
    private String name;

    public Instructor(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void displayInfo() {
        System.out.println("  Instructor ID: " + id + " | Name: " + name);
    }
}
