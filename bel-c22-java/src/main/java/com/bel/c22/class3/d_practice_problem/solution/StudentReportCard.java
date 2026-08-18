package com.bel.c22.class3.d_practice_problem.solution;

public class StudentReportCard {

    private String name;
    private int rollNumber;
    private double mathScore;
    private double scienceScore;
    private double englishScore;

    public StudentReportCard(String name, int rollNumber) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.mathScore = 0;
        this.scienceScore = 0;
        this.englishScore = 0;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public double getMathScore() {
        return mathScore;
    }

    public double getScienceScore() {
        return scienceScore;
    }

    public double getEnglishScore() {
        return englishScore;
    }

    // Setters with validation
    public void setMathScore(double mathScore) {
        if (mathScore >= 0 && mathScore <= 100) {
            this.mathScore = mathScore;
        } else {
            System.out.println("Invalid math score: " + mathScore + ". Must be between 0 and 100.");
        }
    }

    public void setScienceScore(double scienceScore) {
        if (scienceScore >= 0 && scienceScore <= 100) {
            this.scienceScore = scienceScore;
        } else {
            System.out.println("Invalid science score: " + scienceScore + ". Must be between 0 and 100.");
        }
    }

    public void setEnglishScore(double englishScore) {
        if (englishScore >= 0 && englishScore <= 100) {
            this.englishScore = englishScore;
        } else {
            System.out.println("Invalid english score: " + englishScore + ". Must be between 0 and 100.");
        }
    }

    public double getPercentage() {
        return (mathScore + scienceScore + englishScore) / 3.0;
    }

    public String getGrade() {
        double percentage = getPercentage();
        if (percentage >= 80) {
            return "A";
        } else if (percentage >= 60) {
            return "B";
        } else if (percentage >= 40) {
            return "C";
        } else {
            return "F";
        }
    }

    public void printReportCard() {
        System.out.println("=== Report Card ===");
        System.out.println("Name: " + name);
        System.out.println("Roll Number: " + rollNumber);
        System.out.println("Math: " + mathScore);
        System.out.println("Science: " + scienceScore);
        System.out.println("English: " + englishScore);
        System.out.printf("Percentage: %.2f%%\n", getPercentage());
        System.out.println("Grade: " + getGrade());
        System.out.println("===================");
    }
}
