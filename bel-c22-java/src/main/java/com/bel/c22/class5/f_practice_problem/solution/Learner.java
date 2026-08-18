package com.bel.c22.class5.f_practice_problem.solution;

// Abstraction — abstract class that defines the template for all learners
public abstract class Learner {
    private int id;
    private String name;

    public Learner(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Abstract method — each learner type calculates XP differently
    public abstract double calculateXP();

    public void displayInfo() {
        System.out.println("  Learner ID: " + id + " | Name: " + name
                + " | XP: " + calculateXP());
    }
}
