package com.bel.c22.class6.h_composition_over_inheritance;

/**
 * Composition over Inheritance
 *
 * "Prefer HAS-A relationships (composition) over IS-A relationships (inheritance)."
 *
 * Inheritance can lead to rigid, tightly coupled hierarchies.
 * Composition gives you flexibility to mix and match behaviors at runtime.
 */
/*


A -> B -> C
class A {
 p2
}
class B {
  p3,p4
}
class F extends A {
  p1
}
class D extends F {

}

class E extends F {

}

class C extends A {

}





class C extends A, B -> Not possible





class A {

p2 p1
}
class B extends A {

}

class C {
 p2
}
























 */
public class CompositionOverInheritanceDemo {

    // =====================================================
    // BEFORE (Bad) - Using inheritance for code reuse
    // =====================================================

    // Problem: What if we want a RobotDuck that can swim but can't quack?
    // We'd have to override quack() to do nothing — fragile!
    static class DuckBad {
        public void swim() { System.out.println("Swimming..."); }
        public void quack() { System.out.println("Quack quack!"); }
        public void fly() { System.out.println("Flying..."); }
    }

    static class RubberDuckBad extends DuckBad {
        @Override
        public void quack() { System.out.println("Squeak!"); } // Different sound

        @Override
        public void fly() { /* Rubber ducks can't fly — empty override! */ }
        // LSP violation + messy hierarchy!
    }

    // =====================================================
    // AFTER (Good) - Composition: behaviors as objects
    // =====================================================

    // Step 1: Define behavior interfaces
    interface SwimBehavior {
        void swim();
    }

    interface SoundBehavior {
        void makeSound();
    }

    interface FlyBehavior {
        void fly();
    }

    // Step 2: Create concrete behaviors
    static class CanSwim implements SwimBehavior {
        @Override
        public void swim() { System.out.println("Swimming gracefully!"); }
    }

    static class CannotSwim implements SwimBehavior {
        @Override
        public void swim() { System.out.println("Can't swim, staying on land."); }
    }

    static class Quack implements SoundBehavior {
        @Override
        public void makeSound() { System.out.println("Quack quack!"); }
    }

    static class Squeak implements SoundBehavior {
        @Override
        public void makeSound() { System.out.println("Squeak!"); }
    }

    static class Silent implements SoundBehavior {
        @Override
        public void makeSound() { System.out.println("(silence)"); }
    }

    static class CanFly implements FlyBehavior {
        @Override
        public void fly() { System.out.println("Soaring through the sky!"); }
    }

    static class CannotFly implements FlyBehavior {
        @Override
        public void fly() { System.out.println("Can't fly."); }
    }

    static class AnimalProperties {
        private int age;
        private String name;
    }

    static class UniqueAnimalProperties {
        private String color;
        private String habitat;
        private String diet;
    }

    static class Lion {
        private AnimalProperties properties;        // HAS-A
        private UniqueAnimalProperties unique;

        Lion(AnimalProperties p1, UniqueAnimalProperties p2) {
            this.properties = p1;
            this.unique = p2;
        }

        public String getName() {
            return this.properties.name;
        }
    }

    // Step 3: Compose behaviors into a Duck — HAS-A, not IS-A!
    static class Duck {
        private String name;
        private SwimBehavior swimBehavior;   // HAS-A swim behavior
        private SoundBehavior soundBehavior; // HAS-A sound behavior
        private FlyBehavior flyBehavior;  // HAS-A fly behavior

        Duck(String name, SwimBehavior swim, SoundBehavior sound, FlyBehavior fly) {
            this.name = name;
            this.swimBehavior = swim;
            this.soundBehavior = sound;
            this.flyBehavior = fly;
        }

        public void display() {
            System.out.println("--- " + name + " ---");
            swimBehavior.swim();
            soundBehavior.makeSound();
            flyBehavior.fly();
        }
    }

    public static void main(String[] args) {
        System.out.println("=== BEFORE: Inheritance (rigid, fragile) ===");
        DuckBad mallard = new DuckBad();
        mallard.swim();
        mallard.quack();
        mallard.fly();

        System.out.println();
        RubberDuckBad rubber = new RubberDuckBad();
        rubber.swim();
        rubber.quack();
        rubber.fly(); // Does nothing — empty override!

        System.out.println("\n=== AFTER: Composition (flexible, clean) ===");

        // Real duck: swims, quacks, flies
        Duck realDuck = new Duck("Mallard Duck",
                new CanSwim(), new Quack(), new CanFly());
        realDuck.display();

        System.out.println();

        CannotFly abc = new CannotFly();
        // Rubber duck: swims, squeaks, can't fly
        Duck rubberDuck = new Duck("Rubber Duck",
                new CanSwim(), new Squeak(), new CannotFly());
        /*
        Duck {
           Swim; //
           Fly; // fly
           Sound; // squeak
        }
        RubberDuck implements Swimmable, Soundable {
           swim() {

           }
           makeSound() {
            // squeak
           }
        }
        RealDuckWhoCanSqueak implements Swimmable, Soundable {
           swim() {
           }
           makeSound() {
            // squeak
           }
        }

        RealDuckWhoCanQuack implements Swimmable, Soundable {
           swim() {
           }
           makeSound() {
            // quack
           }
        }
         */
        rubberDuck.display();

        System.out.println();

        // Decoy duck: can't swim, silent, can't fly
        Duck decoyDuck = new Duck("Decoy Duck",
                new CannotSwim(), new Silent(), abc);
        decoyDuck.display();

        System.out.println("\n--- Key Takeaway ---");
        System.out.println("Inheritance: IS-A (RubberDuck IS A Duck) — rigid");
        System.out.println("Composition: HAS-A (Duck HAS A SwimBehavior) — flexible");
        System.out.println("Mix and match behaviors without changing existing classes!");
    }
}
/*1. Examples of Composition over inheritance (excercise)
2. Aggregation, Composition, Association
3. Solve 1 problem
 */

