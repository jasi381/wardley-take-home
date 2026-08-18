package com.bel.c22.class5.c_association;

public class AssociationDemo {

    // ==================== ASSOCIATION ("uses-a") ====================
    // Two independent classes that interact but don't own each other.
    // Both objects can exist independently.

    static class Driver {
        String name;

        Driver(String name) {
            this.name = name;
        }

        void displayInfo() {
            System.out.println("Driver: " + name);
        }
    }

    static class Car {
        String model;
        Driver driver;  // Association — Car "uses-a" Driver, but doesn't own them

        Car(String model) {
            this.model = model;
        }

        // Driver is assigned from outside — Car doesn't create or destroy the Driver
        void assignDriver(Driver driver) {
            this.driver = driver;
        }

        void displayInfo() {
            System.out.println("Car: " + model);
            if (driver != null) {
                System.out.println("  Driven by: " + driver.name);
            } else {
                System.out.println("  No driver assigned.");
            }
        }
    }

    // ==================== ONE-TO-MANY ASSOCIATION ====================

    static class Teacher {
        String name;
        String subject;

        Teacher(String name, String subject) {
            this.name = name;
            this.subject = subject;
        }
    }

    static class Student {
        String name;
        Teacher[] teachers;  // A student can have multiple teachers

        Student(String name, Teacher[] teachers) {
            this.name = name;
            this.teachers = teachers;
        }

        void displayInfo() {
            System.out.println("Student: " + name);
            System.out.println("  Teachers:");
            for (Teacher t : teachers) {
                System.out.println("    - " + t.name + " (" + t.subject + ")");
            }
        }
    }

    // ==================== MAIN ====================
    public static void main(String[] args) {

        // --- One-to-One Association ---
        System.out.println("=== Association: Driver <-> Car ===");
        Driver driver1 = new Driver("Rahul");
        Driver driver2 = new Driver("Priya");

        Car car = new Car("Tesla Model 3");
        car.displayInfo();  // no driver yet

        car.assignDriver(driver1);
        car.displayInfo();  // now has a driver

        // Driver still exists independently — can be reassigned
        car.assignDriver(driver2);
        car.displayInfo();  // different driver now
        driver1.displayInfo();  // driver1 still exists!

        // --- One-to-Many Association ---
        System.out.println("\n=== Association: Student <-> Teachers ===");
        Teacher t1 = new Teacher("Mr. Sharma", "Math");
        Teacher t2 = new Teacher("Ms. Gupta", "Science");
        Teacher t3 = new Teacher("Mr. Verma", "English");

        Student student = new Student("Aarav", new Teacher[]{t1, t2, t3});
        student.displayInfo();
    }
}
