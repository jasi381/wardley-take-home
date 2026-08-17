package com.bel.c22.class5.a_abstraction;

/**
 * Abstraction
 *
 * Hide the HOW, expose only the WHAT.
 * Shape defines WHAT every shape can do (area, describe); each subclass
 * decides HOW it actually computes that area.
 */
public class AbstractionDemo {

    static abstract class Shape {
        private final String color;

        Shape(String color) {
            this.color = color;
        }

        // abstract - no body, every subclass MUST provide its own HOW
        abstract double area();

        // concrete - HAS a body, shared by every subclass as-is
        void describe() {
            System.out.printf("I am a %s shape with area %.2f%n", color, area());
        }
    }

    static class Circle extends Shape {
        private final double radius;

        Circle(String color, double radius) {
            super(color);
            this.radius = radius;
        }

        @Override
        double area() {
            return Math.PI * radius * radius;
        }
    }

    static class Rectangle extends Shape {
        private final double width;
        private final double height;

        Rectangle(String color, double width, double height) {
            super(color);
            this.width = width;
            this.height = height;
        }

        @Override
        double area() {
            return width * height;
        }
    }

    public static void main(String[] args) {
        // new Shape("red"); <- would not compile: abstract classes can't be instantiated

        Shape circle = new Circle("red", 3.0);
        Shape rectangle = new Rectangle("blue", 4.0, 5.0);

        System.out.println("=== Caller only knows the WHAT (Shape), not the HOW ===");
        for (Shape shape : new Shape[]{circle, rectangle}) {
            shape.describe(); // same call, different area() logic hidden inside each shape
        }

        System.out.println("\n--- Key Takeaway ---");
        System.out.println("describe() didn't need to know if it was talking to a Circle or a Rectangle.");
        System.out.println("It only needed the Shape abstraction.");
    }
}
