package com.bel.c22.class6.g_dip;

/**
 * D - Dependency Inversion Principle (DIP)
 *
 * "High-level modules should NOT depend on low-level modules.
 *  Both should depend on ABSTRACTIONS (interfaces)."
 *
 * Don't hardcode dependencies. Use interfaces so you can
 * easily swap implementations!
 *
 *
 *
 * DB - MySQL , PostGres, Mongo, Cassandra
 *
 * UserRepo {
 *     DBOperations db;
 *
 *     createUser() {
 *         db.createUser();
 *     }
 * }
 *
 * interface DBOperation {
 *     getUser(userID);
 *     insertUser(User);
 * }
 *
 *
 * Mysql DB implements DBOperation {
 *     getUser(userID) {
 *
 *     }
 *
 *     insertUser(User) {
 *
 *     }
 * }
 *
 * Mongo DB implements DBOperation {
 *  *     getUser(userID) {
 *  *
 *  *     }
 *  *
 *  *     insertUser(User) {
 *  *
 *  *     }
 *  * }
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
public class DipDemo {

    // =====================================================
    // BEFORE DIP (Bad) - Switch is TIGHTLY coupled to LightBulb
    // =====================================================
    static class LightBulb {
        public void turnOn() { System.out.println("LightBulb turned ON"); }
        public void turnOff() { System.out.println("LightBulb turned OFF"); }
    }

    static class SwitchBad {
        private LightBulb bulb;

        SwitchBad() {
            this.bulb = new LightBulb(); // Hardcoded! Can ONLY control LightBulb!
        }

        public void operate() {
            bulb.turnOn();
            // Want to control a Fan instead? Must MODIFY this class!
        }
    }














    // =====================================================
    // AFTER DIP (Good) - Switch depends on abstraction
    // =====================================================

    // Step 1: Define an abstraction (interface)
    interface Switchable {
        void turnOn();
        void turnOff();
    }

    // Step 2: Low-level modules implement the interface
    static class SmartBulb implements Switchable {
        @Override
        public void turnOn() { System.out.println("SmartBulb turned ON"); }

        @Override
        public void turnOff() { System.out.println("SmartBulb turned OFF"); }
    }

    static class Fan implements Switchable {
        @Override
        public void turnOn() { System.out.println("Fan turned ON, spinning!"); }

        @Override
        public void turnOff() { System.out.println("Fan turned OFF"); }
    }

    static class AC implements Switchable {
        @Override
        public void turnOn() { System.out.println("AC turned ON, cooling!"); }

        @Override
        public void turnOff() { System.out.println("AC turned OFF"); }
    }

    // Step 3: High-level module depends on ABSTRACTION, not concrete class
    static class SmartSwitch {
        private Switchable device; // Depends on interface, not LightBulb!

        SmartSwitch(Switchable device) {
            this.device = device; // Injected from outside!
        }

        public void operate() {
            device.turnOn();
        }

        public void shutdown() {
            device.turnOff();
        }
    }
    /*
    interface MsgSender {

        send();
    }

    EmailSender implements MsgSender {

      send() {
      }
    }

    SMSSender implements MsgSender {

      send() {
      }
    }
     // Send SMS & Email
    OrderService {

    private List<MsgSender> msgSender;

    OrderService(List<MsgSender> senders) {
         this.msgSender = senders;
    }

    notify() {
       for MsgSender in senders:
          senders.send();
    }

    }

    // Email
    LoginService {
    private MsgSender msgSender;

    }

    void main() {

       EmailSender emailSender = new EmailSender();
              SmsSender smsSender = new SmsSender();

       OrderService orderService = new OrderService(List.of(smsSender, pushNotificationSender));

    }

    // SMS
    PaymentService {


    }

     */

    public static void main(String[] args) {
        System.out.println("=== BEFORE DIP (tightly coupled) ===");
        SwitchBad badSwitch = new SwitchBad();
        badSwitch.operate();
        System.out.println("(Can ONLY control LightBulb — hardcoded!)");

        System.out.println("\n=== AFTER DIP (loosely coupled via interface) ===");

        // Same SmartSwitch class controls ANY device!
        SmartSwitch bulbSwitch = new SmartSwitch(new SmartBulb());
        bulbSwitch.operate();
        bulbSwitch.shutdown();

        SmartSwitch fanSwitch = new SmartSwitch(new Fan());
        fanSwitch.operate();
        fanSwitch.shutdown();

        SmartSwitch acSwitch = new SmartSwitch(new AC());
        acSwitch.operate();
        acSwitch.shutdown();

        System.out.println("\n--- Key Takeaway ---");
        System.out.println("High-level (SmartSwitch) depends on Interface (Switchable)");
        System.out.println("Low-level (Bulb, Fan, AC) also depends on Interface");
        System.out.println("Neither depends on the other directly — both depend on abstraction!");
    }
}
