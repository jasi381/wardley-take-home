package com.bel.c22.class4.a_inheritance;
/*
Vehicle - no of wheels, capacity, weight, colour

Car, Bike, Truck
class A {

   move() {
   Logic 1
   }
}

class B {

   move() {
   Logic 2
   }
}

class C extends A,B {
}

class A {

   move();
   xyz();
}

class B extends A {

   move();
   abc();
   // overriden
   xyz();
}

class C extends B {
   move();
   abc();
   // overriden
   xyz();
}

-----------------
Animal - (___, ____)

Dog - (__, animal)

Dog d = new Dog();
d.name;

 */






public class InheritanceDemo {

    // ==================== SINGLE INHERITANCE ====================
    // One parent, one child

    static class Animal {
        private String name  = "abc";

        void eat() {
            System.out.println(name + " is eating.");
        }

        void sleep() {
            System.out.println(name + " is sleeping.");
        }

        static void help() {
            System.out.println("Help Animal");
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    // Dog "is-a" Animal — inherits name, eat(), sleep()
    static class Dog extends Animal {
        String breed;

        void bark() {
            System.out.println(super.name + " says: Woof! Woof!");
        }

        //@Override
//        void eat() {
//            System.out.println(name + " dog is eating.");
//        }


    }

    // ==================== MULTILEVEL INHERITANCE ====================
    // Chain: Vehicle -> Car -> ElectricCar

    static class Vehicle {
        String brand;

        void move() {
            System.out.println(brand + " vehicle started.");
        }
    }

    static class Car extends Vehicle {
        int numDoors;
        //String brand;

        void move() {
            System.out.println(brand + " car started.");
        }

        /*void drive() {
            System.out.println(brand + " car is driving with " + numDoors + " doors.");
        }*/
    }

    // ElectricCar inherits from Car, which inherits from Vehicle
    static class ElectricCar extends Car {
        int batteryLife;
        /*int numDoors;
        /*
        String brand;*/

        @Override
        void move() {
            System.out.println(brand + " tesla started.");
        }

        void drive() {
            System.out.println(brand + " car is driving with " + numDoors + " doors.");
        }
        void chargeBattery() {
            System.out.println(brand + " is charging. Battery: " + batteryLife + " hrs.");
        }
    }

    // ==================== HIERARCHICAL INHERITANCE ====================
    // One parent, multiple children

    static class Shape {
        String color;

        void displayColor() {
            System.out.println("Color: " + color);
        }
    }

    static class Circle extends Shape {
        double radius;

        double area() {
            return Math.PI * radius * radius;
        }

        void displayColor() {
            System.out.println("Color: " + color);
        }
    }

    static class Rectangle extends Shape {
        double length, width;

        double area() {
            return length * width;
        }
    }

    // ==================== MAIN ====================
    public static void main(String[] args) {

        // --- Single Inheritance ---
        System.out.println("=== Single Inheritance ===");
        Dog dog = new Dog();
        //dog.name = "Buddy";       // inherited from Animal
        dog.breed = "Labrador";   // Dog's own field
        dog.setName("Buddy");
       // dog.name = "Buddy1";
        dog.eat();                // inherited method
        dog.sleep();              // inherited method
        dog.bark();               // Dog's own method

        Animal.help();
        Dog.help();

        Animal animal = new Animal();
        Animal animal1 = new Dog();
        // Dog dog1 = new Animal();
        animal1.eat();
        ((Dog) animal1).bark();
        //((Dog) animal1).bark();

        // --- Multilevel Inheritance ---
        /*System.out.println("\n=== Multilevel Inheritance ===");
        ElectricCar tesla = new ElectricCar();
        tesla.brand = "Tesla";       // from Vehicle (grandparent)
        tesla.numDoors = 4;          // from Car (parent)
        tesla.batteryLife = 12;      // ElectricCar's own field
        tesla.move();               // from Vehicle
        tesla.drive();               // from Car
        tesla.chargeBattery();       // ElectricCar's own method

        // --- Hierarchical Inheritance ---
        /*System.out.println("\n=== Hierarchical Inheritance ===");
        Circle circle = new Circle();
        circle.color = "Red";        // from Shape
        circle.radius = 5;
        circle.displayColor();       // inherited
        System.out.println("Circle area: " + circle.area());

        Rectangle rect = new Rectangle();
        rect.color = "Blue";         // from Shape
        rect.length = 4;
        rect.width = 6;
        rect.displayColor();         // inherited
        System.out.println("Rectangle area: " + rect.area());*/
    }
}
