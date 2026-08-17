package com.bel.c22.class6.f_isp;

/**
 * I - Interface Segregation Principle (ISP)
 *
 * "Clients should NOT be forced to depend on interfaces they don't use."
 *
 * Don't create one FAT interface with everything.
 * Instead, create smaller, focused interfaces.
 */
public class IspDemo {

    // =====================================================
    // BEFORE ISP (Bad) - One FAT interface for all workers
    // =====================================================
    interface WorkerBad {
        void work();
        void eat();
        void sleep();
    }

    // Human employee - can do all 3. No problem.
    static class HumanWorkerBad implements WorkerBad {
        @Override
        public void work() { System.out.println("Human is working..."); }

        @Override
        public void eat() { System.out.println("Human is eating lunch..."); }

        @Override
        public void sleep() { System.out.println("Human is sleeping..."); }
    }

    // Robot worker - forced to implement eat() and sleep()!
    static class RobotWorkerBad implements WorkerBad {
        @Override
        public void work() { System.out.println("Robot is working..."); }

        @Override
        public void eat() {
            // Robots don't eat! But FORCED to implement this!
            throw new UnsupportedOperationException("Robots don't eat!");
        }

        @Override
        public void sleep() {
            // Robots don't sleep! But FORCED to implement this!
            throw new UnsupportedOperationException("Robots don't sleep!");
        }
    }

    // =====================================================
    // AFTER ISP (Good) - Small, focused interfaces
    // =====================================================
    interface Workable {
        void work();
    }

    interface Eatable {
        void eat();
    }

    interface Sleepable {
        void sleep();
    }

    // Human implements ALL three — makes sense!
    static class HumanWorker implements Workable, Eatable, Sleepable {
        @Override
        public void work() { System.out.println("Human is working..."); }

        @Override
        public void eat() { System.out.println("Human is eating lunch..."); }

        @Override
        public void sleep() { System.out.println("Human is sleeping..."); }
    }

    // Robot implements ONLY Workable — clean!
    static class RobotWorker implements Workable {
        @Override
        public void work() { System.out.println("Robot is working 24/7!"); }
        // No eat(), no sleep() — Robot isn't forced to fake them!
    }

    static class XYZ {
        void task(Workable workable) {
            workable.work();
        }
    }

    public static void main(String[] args) {
        System.out.println("=== BEFORE ISP (fat interface) ===");
        HumanWorkerBad human1 = new HumanWorkerBad();
        human1.work();
        human1.eat();

        RobotWorkerBad robot1 = new RobotWorkerBad();
        robot1.work();
        try {
            robot1.eat(); // CRASH! Robot can't eat!
        } catch (UnsupportedOperationException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println("\n=== AFTER ISP (segregated interfaces) ===");
        Workable human2 = new HumanWorker();

        Workable robot2 = new RobotWorker();

        XYZ xyz = new XYZ();
        xyz.task(robot2);
        // robot2.eat(); ← Compile error! Robot doesn't HAVE eat()!
        System.out.println("(Robot only has work() — clean and honest!)");

        System.out.println("\n--- Key Takeaway ---");
        System.out.println("Many small interfaces > One big interface");
        System.out.println("Each class picks ONLY the interfaces it actually needs");
    }
}
