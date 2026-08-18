package com.bel.c22.class3.a_packages.school.science;

public class Teacher {
    private String name;

    public Teacher(String name) {
        this.name = name;
    }

    public void teach() {
        System.out.println(name + " is teaching Science.");
    }
}
