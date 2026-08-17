package com.bel.c22.class6.g_dip;

/**
 * D - Dependency Inversion Principle (DIP)
 *
 * "High-level modules should not depend on low-level modules directly.
 * Both should depend on abstractions."
 */
public class DipDemo {

    // =====================================================
    // BEFORE DIP (Bad) - Switch is hardcoded to LightBulb
    // =====================================================
    static class LightBulb {
        void turnOn() {
            System.out.println("LightBulb: ON");
        }
    }

    static class SwitchBad {
        private final LightBulb bulb = new LightBulb(); // creates its own dependency - tightly coupled

        void operate() {
            bulb.turnOn();
        }
        // Want SwitchBad to control a Fan instead? You must MODIFY this class.
    }

    // =====================================================
    // AFTER DIP (Good) - Switch depends on an abstraction
    // =====================================================
    interface Switchable {
        void turnOn();
    }

    static class Bulb implements Switchable {
        public void turnOn() {
            System.out.println("Bulb: ON");
        }
    }

    static class Fan implements Switchable {
        public void turnOn() {
            System.out.println("Fan: spinning");
        }
    }

    static class Switch {
        private final Switchable device; // abstraction, not a concrete class

        // dependency injection: the device is PASSED IN, not created here
        Switch(Switchable device) {
            this.device = device;
        }

        void operate() {
            device.turnOn();
        }
    }

    public static void main(String[] args) {
        System.out.println("=== BEFORE DIP (Switch locked to LightBulb) ===");
        new SwitchBad().operate();

        System.out.println("\n=== AFTER DIP (Switch works with ANY Switchable) ===");
        Switch lightSwitch = new Switch(new Bulb());
        Switch fanSwitch = new Switch(new Fan());
        lightSwitch.operate();
        fanSwitch.operate();

        System.out.println("\n--- Key Takeaway ---");
        System.out.println("Switch never mentions Bulb or Fan by name - only the Switchable abstraction.");
        System.out.println("Add an AirConditioner implements Switchable tomorrow - Switch needs zero changes.");
    }
}
