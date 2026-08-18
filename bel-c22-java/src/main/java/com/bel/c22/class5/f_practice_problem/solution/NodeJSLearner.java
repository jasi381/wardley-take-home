package com.bel.c22.class5.f_practice_problem.solution;

// Inheritance — NodeJSLearner IS-A Learner
public class NodeJSLearner extends Learner {
    private int modulesCompleted;
    private int quizScore;

    public NodeJSLearner(int id, String name, int modulesCompleted, int quizScore) {
        super(id, name);
        this.modulesCompleted = modulesCompleted;
        this.quizScore = quizScore;
    }

    // XP = 20 points per module + quiz score
    @Override
    public double calculateXP() {
        return (modulesCompleted * 20) + quizScore;
    }

    @Override
    public void displayInfo() {
        System.out.println("  [Node.js Learner] ID: " + getId() + " | Name: " + getName()
                + " | Modules: " + modulesCompleted
                + " | Quiz Score: " + quizScore
                + " | XP: " + calculateXP());
    }
}
