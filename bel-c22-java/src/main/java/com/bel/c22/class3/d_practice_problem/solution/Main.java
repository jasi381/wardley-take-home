package com.bel.c22.class3.d_practice_problem.solution;

public class Main {
    public static void main(String[] args) {

        // Student 1
        StudentReportCard rahul = new StudentReportCard("Rahul", 101);
        rahul.setMathScore(85);
        rahul.setScienceScore(72);
        rahul.setEnglishScore(90);
        rahul.printReportCard();

        System.out.println();

        // Try setting invalid score - should be rejected
        System.out.println("Trying to set invalid math score (-10):");
        rahul.setMathScore(-10);
        System.out.println("Math score is still: " + rahul.getMathScore());

        System.out.println();

        // Student 2
        StudentReportCard priya = new StudentReportCard("Priya", 102);
        priya.setMathScore(55);
        priya.setScienceScore(48);
        priya.setEnglishScore(62);
        priya.printReportCard();

        // Cannot directly access private fields:
        // rahul.mathScore = 100;  // Compile error! Encapsulation prevents this.
    }
}
