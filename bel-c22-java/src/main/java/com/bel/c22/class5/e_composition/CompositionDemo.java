package com.bel.c22.class5.e_composition;

import java.util.ArrayList;
import java.util.List;

public class CompositionDemo {

    // ==================== COMPOSITION ("has-a" with strong ownership) ====================
    // Parent OWNS children — children are created INSIDE the parent.
    // If parent is destroyed, children are destroyed too.

    static class Room {
        String name;
        double area;

        Room(String name, double area) {
            this.name = name;
            this.area = area;
        }

        void displayInfo() {
            System.out.println("    Room: " + name + " (" + area + " sq ft)");
        }
    }

    static class House {
        String address;
        List<Room> rooms;  // Composition — House OWNS rooms

        // Rooms are created INSIDE the House — not passed from outside
        House(String address) {
            this.address = address;
            this.rooms = new ArrayList<>();

            // Rooms are created as part of the House — they don't exist independently
            rooms.add(new Room("Living Room", 300));
            rooms.add(new Room("Bedroom", 200));
            rooms.add(new Room("Kitchen", 150));
        }

        void displayInfo() {
            System.out.println("House at: " + address);
            System.out.println("  Rooms:");
            for (Room room : rooms) {
                room.displayInfo();
            }
        }
    }

    // ==================== ANOTHER EXAMPLE: Car and Engine ====================

    static class Engine {
        String type;
        int horsepower;

        Engine(String type, int horsepower) {
            this.type = type;
            this.horsepower = horsepower;
        }

        void start() {
            System.out.println("  " + type + " engine (" + horsepower + " HP) started.");
        }
    }

    static class Car {
        String brand;
        Engine engine;  // Composition — Car OWNS the Engine

        // Engine is created INSIDE the Car — it's part of the Car
        Car(String brand, String engineType, int hp) {
            this.brand = brand;
            this.engine = new Engine(engineType, hp);  // created here, not passed in
        }

        void start() {
            System.out.println("Starting " + brand + "...");
            engine.start();  // delegates to the owned Engine
        }
    }

    // ==================== MAIN ====================
    public static void main(String[] args) {

        // --- House and Rooms (Composition) ---
        System.out.println("=== Composition: House -> Rooms ===");
        House house = new House("123 Main Street");
        house.displayInfo();

        // If house is destroyed, rooms are also destroyed!
        // There is NO way to access the rooms independently.
        System.out.println("\nHouse demolished — rooms cease to exist too.");
        house = null;  // rooms are garbage collected along with the house

        // --- Car and Engine (Composition) ---
        System.out.println("\n=== Composition: Car -> Engine ===");
        Car car = new Car("Tesla", "Electric", 450);
        car.start();

        // Cannot access the engine outside the car independently
        // car.engine exists only as part of the car
    }
}
