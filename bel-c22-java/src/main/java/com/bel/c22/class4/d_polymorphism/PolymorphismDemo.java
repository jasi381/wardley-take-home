package com.bel.c22.class4.d_polymorphism;

public class PolymorphismDemo {

    // ==================== RUNTIME POLYMORPHISM (Method Overriding) ====================

    static class Animal {
        String name;

        Animal(String name) {
            this.name = name;
        }

        // This method will be OVERRIDDEN by child classes
        void makeSound() {
            System.out.println(name + " makes a generic sound.");
        }

        void displayInfo(int xyz) {
            System.out.println("I am an animal named " + name);
        }
    }

    static class Dog extends Animal {
        String breed;
        Dog(String name, String breed) {
            super(name);
            this.breed = breed;
        }

        @Override  // tells compiler we intend to override
        void makeSound() {
            System.out.println(name + " says: Woof! Woof!");
        }

        void displayInfo(int xyz) {
            System.out.println("I am an dog named " + name);
        }

        void test() {
            System.out.println("Hello");
        }
    }

    static class Cat extends Animal {
        Cat(String name) {
            super(name);
        }

        @Override
        void makeSound() {
            System.out.println(name + " says: Meow!");
        }

        void x1() {

        }
    }

    static class Cow extends Animal {
        Cow(String name) {
            super(name);
        }

        @Override
        void makeSound() {
            System.out.println(name + " says: Moo!");
        }
    }

    // ==================== MAIN ====================
    public static void main(String[] args) {

        // --- Normal usage (no polymorphism) ---
//        System.out.println("=== Normal Object Creation ===");
//        Dog dog = new Dog("Buddy", "XYZ");
//        dog.makeSound();
//        dog.displayInfo(2);
//
//        Cat cat = new Cat("Whiskers");
//        cat.makeSound();
//        cat.displayInfo(2);

        // --- Runtime Polymorphism: Parent reference, child object ---
        System.out.println("\n=== Runtime Polymorphism ===");
        // The REFERENCE type is Animal, but the ACTUAL object is Dog/Cat/Cow
        // JVM decides which makeSound() to call at RUNTIME based on actual object
        Animal a1 = new Dog("Rex", "XYZ");

        a1.makeSound();
       // a1.
        String breed = ((Dog) a1).breed;
        //System.out.println(a1.breed);
        Animal a2 = new Cat("Luna");
        Animal a3 = new Cow("Gau Mata");

        //Dog a4 = new Animal("test");
       // a1.test();
        a3.displayInfo(1);
        //a1.xyz();
        //Dog d1 = (Dog) a2;
        //a1.makeSound();
        //a1.name

//        a1.makeSound();  // Dog's makeSound() — decided at runtime
//        a2.makeSound();  // Cat's makeSound() — decided at runtime
//        a3.makeSound();  // Cow's makeSound() — decided at runtime
//
//        // displayInfo() is NOT overridden, so Animal's version is called
//        a1.displayInfo(1);
//
//        // --- Polymorphism with arrays (powerful pattern!) ---
//        System.out.println("\n=== Polymorphism with Array ===");
        Animal[] animals = { new Dog("Tommy", "XYZ"), new Cat("Kitty"), new Cow("Nandi") };

        // Same loop, different behavior — this is the power of polymorphism!
        for (Animal animal : animals) {
            animal.makeSound();
        }
    }
}
