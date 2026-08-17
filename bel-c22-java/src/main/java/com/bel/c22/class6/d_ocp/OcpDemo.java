package com.bel.c22.class6.d_ocp;

/**
 * O - Open/Closed Principle (OCP)
 *
 * "Software entities should be open for extension, but closed for modification."
 * Add new behavior by writing new code, not by editing existing code.
 */
public class OcpDemo {

    // =====================================================
    // BEFORE OCP (Bad) - if-else anti-pattern
    // =====================================================
    static class ShapeBad {
        private final String type;

        ShapeBad(String type) {
            this.type = type;
        }

        void draw() {
            if (type.equals("circle")) {
                System.out.println("Drawing a circle");
            } else if (type.equals("rectangle")) {
                System.out.println("Drawing a rectangle");
            }
            // Adding "triangle" means MODIFYING this method - risky for existing shapes.
        }
    }

    // =====================================================
    // AFTER OCP (Good) - interface-based, extend without modifying
    // =====================================================
    interface Shape {
        void draw();
    }

    static class Circle implements Shape {
        public void draw() {
            System.out.println("Drawing a circle");
        }
    }

    static class Rectangle implements Shape {
        public void draw() {
            System.out.println("Drawing a rectangle");
        }
    }

    // New requirement: add a Triangle. Circle and Rectangle are NEVER touched.
    static class Triangle implements Shape {
        public void draw() {
            System.out.println("Drawing a triangle");
        }
    }

    static class Canvas {
        void render(Shape shape) {
            shape.draw();
        }
    }

    public static void main(String[] args) {
        System.out.println("=== BEFORE OCP (must edit ShapeBad.draw() for every new type) ===");
        new ShapeBad("circle").draw();
        new ShapeBad("rectangle").draw();

        System.out.println("\n=== AFTER OCP (new shape = new class, nothing existing touched) ===");
        Canvas canvas = new Canvas();
        Shape[] shapes = {new Circle(), new Rectangle(), new Triangle()};
        for (Shape shape : shapes) {
            canvas.render(shape);
        }

        System.out.println("\n--- Key Takeaway ---");
        System.out.println("Canvas.render() never changed when Triangle was added.");
        System.out.println("It only knows the Shape interface - open for extension, closed for modification.");
    }
}
