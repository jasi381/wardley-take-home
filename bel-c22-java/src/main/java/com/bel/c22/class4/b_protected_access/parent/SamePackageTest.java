package com.bel.c22.class4.b_protected_access.parent;

// This class is in the SAME package as Animal
/*
BA {
accNumber
}

HDFCBA extends BA {
}
ICICIBA
 */
public class SamePackageTest {

    public static void main(String[] args) {
        Animal animal = new Animal(1, "Leo", 5, "Lion");

        // public — accessible everywhere
        System.out.println("species (public): " + animal.name);
        animal.eat();

        // protected — accessible in same package (even without inheritance!)
        System.out.println("name (protected): " + animal.name);
        System.out.println("age (protected): " + animal.age);
        animal.displayInfo();

        // private — NOT accessible outside the class
        // System.out.println(animal.id);        // COMPILE ERROR!
        // animal.logInternal();                  // COMPILE ERROR!
    }
}
