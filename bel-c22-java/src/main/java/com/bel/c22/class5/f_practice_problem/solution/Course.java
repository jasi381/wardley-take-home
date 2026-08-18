package com.bel.c22.class5.f_practice_problem.solution;

// Encapsulation — private fields, public getters
public class Course {
    private String name;
    private String type;            // "online" or "offline"
    private String specialization;  // "Java" or "Node.js"

    public Course(String name, String type, String specialization) {
        this.name = name;
        this.type = type;
        this.specialization = specialization;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void displayInfo() {
        System.out.println("Course: " + name + " | Type: " + type
                + " | Specialization: " + specialization);
    }
}
