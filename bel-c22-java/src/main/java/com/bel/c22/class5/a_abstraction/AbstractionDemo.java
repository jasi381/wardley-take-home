package com.bel.c22.class5.a_abstraction;

import com.bel.c22.class9.a_singleton.SingletonDemo;
/*

src/employee
  -
   class FTE {
   }
src/vehicle
import
  import FTE;
  - TwoWheeler {
  }

a/b/c
a/b/d
x/y

a.b.c
a.b.d
x.y
abstract class Shape {
    abstract double area();       // abstract — NO body, subclass MUST override
    void describe() {             // concrete — HAS a body, inherited as-is
        System.out.println("I am a shape.");
    }
}

class Shape {
    double area() {
    return 1.0;
          }
    void describe() {             // concrete — HAS a body, inherited as-is
        System.out.println("I am a shape.");
    }
}

PaymentMethod {
   abstract pay();
}

CCPM extends PaymentMethod{
   pay() {
   }
}
DCPM {
   pay(); {
   }
}


processPayment(PaymentMethod method) {
   method.pay();
}
 */







public class AbstractionDemo {

    // ==================== ABSTRACT CLASS ====================
    // Cannot be instantiated — provides a template for subclasses

    static abstract class Shape {
        String color;

        // Constructor — called by subclasses via super()

        Shape(String color) {
            this.color = color;
        }

        // Abstract method — NO body, subclass MUST provide implementation
        abstract double area();

        // Concrete method — has a body, inherited by all subclasses
        void displayInfo() {
            System.out.println("Shape color: " + color);
            System.out.println("Area: " + area()); // calls the subclass version!
        }
    }

    // ==================== CONCRETE SUBCLASSES ====================

    static class Circle extends Shape {
        double radius;

        Circle(String color, double radius) {
            super(color);       // calls Shape constructor
            this.radius = radius;
        }

        //abstract double area1();


        // MUST override abstract method — provides Circle-specific implementation
        @Override
        double area() {
            return Math.PI * radius * radius;
        }
    }

    static class Rectangle extends Shape {
        double length, width;

        Rectangle(String color, double length, double width) {
            super(color);
            this.length = length;
            this.width = width;
        }

        @Override
        double area() {
            return length * width;
        }
    }

    // ==================== ABSTRACT CLASS WITH MIXED METHODS ====================

    static abstract class Vehicle {
        String brand;

        Vehicle(String brand) {
            this.brand = brand;
        }

        // Abstract — each vehicle type starts differently
        abstract void start();

        // Concrete — all vehicles stop the same way
        void stop() {
            System.out.println(brand + " has stopped.");
        }
    }

    static class Car extends Vehicle {
        Car(String brand) {
            super(brand);
        }

        @Override
        void start() {
            System.out.println(brand + " car starts with a key ignition.");
        }
    }

    static class Bike extends Vehicle {
        Bike(String brand) {
            super(brand);
        }

        @Override
        void start() {
            System.out.println(brand + " bike starts with a kick.");
        }
    }

    // ==================== MAIN ====================
    public static void main(String[] args) {

        // Cannot do: Shape s = new Shape("Red"); // Compile error!
        // Shape s1 = new Shape("abc");

        // --- Abstract class with subclasses ---
        //Shape s1 = new Shape();
        System.out.println("=== Shape Abstraction ===");
        Shape circle = new Circle("Red", 5);
        circle.displayInfo();
        // calls concrete method, which internally calls abstract area()
        circle.area();

        System.out.println();
        Shape rect = new Rectangle("Blue", 4, 6);
        rect.displayInfo();

        // --- Polymorphism with abstract class ---
        System.out.println("\n=== Vehicle Abstraction (Polymorphism) ===");
        Vehicle v1 = new Car("Toyota");     // parent reference, child object
        Vehicle v2 = new Bike("Honda");

        v1.start();  // Car's version
        v1.stop();   // inherited concrete method
        System.out.println();
        v2.start();  // Bike's version
        v2.stop();   // inherited concrete method
    }
}
