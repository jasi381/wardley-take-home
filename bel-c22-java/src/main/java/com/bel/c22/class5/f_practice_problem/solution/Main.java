package com.bel.c22.class5.f_practice_problem.solution;

public class Main {
    public static void main(String[] args) {

        // === 1. Create Courses ===
        System.out.println("=== Courses ===");
        Course javaCourse = new Course("Java Backend Bootcamp", "online", "Java");
        Course nodeCourse = new Course("Node.js Fullstack", "offline", "Node.js");
        javaCourse.displayInfo();
        nodeCourse.displayInfo();

        // === 2. Create Instructors ===
        Instructor instructor1 = new Instructor(1, "Aashray");
        Instructor instructor2 = new Instructor(2, "Neha");

        // === 3. Create Cohorts (with Instructors — Aggregation) ===
        Cohort javaCohort = new Cohort("Java Cohort - Batch 1", instructor1);
        Cohort nodeCohort = new Cohort("Node.js Cohort - Batch 1", instructor2);

        // === 4. Create Learners and Enroll (Inheritance + Abstraction) ===
        JavaLearner jl1 = new JavaLearner(101, "Rahul", 8, 2);
        JavaLearner jl2 = new JavaLearner(102, "Sneha", 10, 3);
        JavaLearner jl3 = new JavaLearner(103, "Arjun", 6, 1);

        javaCohort.enrollLearner(jl1);
        javaCohort.enrollLearner(jl2);
        javaCohort.enrollLearner(jl3);

        NodeJSLearner nl1 = new NodeJSLearner(201, "Priya", 5, 85);
        NodeJSLearner nl2 = new NodeJSLearner(202, "Karan", 7, 90);

        nodeCohort.enrollLearner(nl1);
        nodeCohort.enrollLearner(nl2);

        // === 5. Display Cohort Details ===
        javaCohort.displayInfo();
        nodeCohort.displayInfo();

        // === 6. Polymorphism — treating all learners as Learner references ===
        System.out.println("\n=== All Learners (Polymorphism) ===");
        Learner[] allLearners = { jl1, jl2, jl3, nl1, nl2 };
        for (Learner learner : allLearners) {
            learner.displayInfo();  // calls the correct subclass version
        }
    }
}
