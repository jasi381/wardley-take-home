package com.bel.c22.class4.b_protected_access.parent;

public class Animal {

    public static int xyz = 5;
    // private — only accessible inside this class
    private int  id;

    // protected — accessible in same package + subclasses in other packages
    protected String name;
    protected int age;

    // public — accessible everywhere
    public String species;

    public static void check() {
        System.out.println("Animal");

    }

    public Animal() {

    }

    public Animal(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Animal(int id, String name, int age, String species) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.species = species;
    }

    // private method — only this class can call it
    private void logInternal() {
        System.out.println("[LOG] Animal id=" + id);
    }

    // protected method — subclasses can call/override it
    protected void displayInfo() {
        logInternal();  // private method called within same class — OK
        System.out.println("Name: " + name + " | Age: " + age + " | Species: " + species);
    }

    // public method
    public void eat() {
        System.out.println(name + " is eating.");
    }
}
