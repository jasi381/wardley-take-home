package com.bel.c22.class4.b_protected_access.child;

import com.bel.c22.class4.b_protected_access.parent.Animal;

// Dog is in a DIFFERENT package than Animal
// But it EXTENDS Animal, so it can access 'protected' members
/*
Dog {
    private String breed;
    Animal animal;
}
 */
public class Dog extends Animal {

    private String breed;
    //private Animal test;

    public Dog() {
        super();
    }
    public Dog(int a, String b, int c, String d) {
       //super(id, name, age, "Dog");
        //super();
        this.breed = breed;
    }

    public static void check() {
        System.out.println("Dog");
    }

    public void showDetails() {
        //System.out.println(test.name);
        // protected fields — accessible because Dog is a SUBCLASS of Animal
        System.out.println("Dog name: " + name);     // protected — OK via inheritance
        System.out.println("Dog age: " + age);        // protected — OK via inheritance
        System.out.println("Dog breed: " + breed);    // private to Dog — OK

        int abc = Dog.xyz;

        // public field — always accessible
        System.out.println("Species: " + species);    // public — OK

        // private field from parent — NOT accessible even in subclass
         //System.out.println("ID: " + id);           // COMPILE ERROR!

        // protected method from parent — accessible in subclass
        displayInfo();                                 // protected — OK via inheritance
    }

    public static void main(String[] args) {

        Dog.check();
        System.out.println("=== Protected Access from Different Package Subclass ===");
        Dog dog = new Dog();
        dog.showDetails();

        System.out.println("\n=== What a non-subclass in this package CANNOT do ===");
        // If we create an Animal object here (not a subclass relationship),
        // we CANNOT access protected members:
        Animal animal = new Animal(2, "Kitty", 2, "Cat");
        // animal.name = "Test";       // COMPILE ERROR! Different package, not a subclass reference
        // animal.displayInfo();       // COMPILE ERROR!
        animal.eat();                  // public — OK
        System.out.println(animal.species);  // public — OK
    }
}
