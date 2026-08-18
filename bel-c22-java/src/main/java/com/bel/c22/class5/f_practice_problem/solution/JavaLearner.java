package com.bel.c22.class5.f_practice_problem.solution;

// Inheritance — JavaLearner IS-A Learner
public class JavaLearner extends Learner {
    private int assignmentsCompleted;
    private int projectsCompleted;

    public JavaLearner(int id, String name, int assignmentsCompleted, int projectsCompleted) {
        super(id, name);
        this.assignmentsCompleted = assignmentsCompleted;
        this.projectsCompleted = projectsCompleted;
    }

    // XP = 10 points per assignment + 50 points per project
    @Override
    public double calculateXP() {
        return (assignmentsCompleted * 10) + (projectsCompleted * 50);
    }

    @Override
    public void displayInfo() {
        System.out.println("  [Java Learner] ID: " + getId() + " | Name: " + getName()
                + " | Assignments: " + assignmentsCompleted
                + " | Projects: " + projectsCompleted
                + " | XP: " + calculateXP());
    }
}
