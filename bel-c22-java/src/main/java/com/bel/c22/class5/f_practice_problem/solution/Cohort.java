package com.bel.c22.class5.f_practice_problem.solution;

import java.util.ArrayList;
import java.util.List;

// Demonstrates Aggregation (Instructor) and Composition (Learners)
public class Cohort {
    private String name;
    private Instructor instructor;       // Aggregation — instructor exists independently
    private List<Learner> learners;      // Composition — learners belong to this cohort

    public Cohort(String name, Instructor instructor) {
        this.name = name;
        this.instructor = instructor;    // passed in from outside — aggregation
        this.learners = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    // Enroll a learner into this cohort
    public void enrollLearner(Learner learner) {
        learners.add(learner);
    }

    // Calculate average XP of all learners in this cohort
    public double averageXP() {
        if (learners.isEmpty()) {
            return 0;
        }
        double totalXP = 0;
        for (Learner learner : learners) {
            totalXP += learner.calculateXP();
        }
        return totalXP / learners.size();
    }

    public void displayInfo() {
        System.out.println("\n--- Cohort: " + name + " ---");
        instructor.displayInfo();
        System.out.println("  Enrolled Learners (" + learners.size() + "):");
        for (Learner learner : learners) {
            learner.displayInfo();
        }
        System.out.println("  Average XP: " + averageXP());
    }
}
