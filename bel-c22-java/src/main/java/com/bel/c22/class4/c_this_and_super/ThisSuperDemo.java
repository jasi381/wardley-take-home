package com.bel.c22.class4.c_this_and_super;

/*
Animal {
   variables a,b,c
   Animal(int a) {
      this.a = a;
   }


}

A {
  xyz() {
  }
}

B extends A{
  xyz() {
  }
}

C extends B{
}

---Dog(---reference - animal ------)--
----Animal(      )---------
--------------

Dog extends Animal {

   int d;
   Dog(int d) {

   }
}


A extends B {

   abc() {
   // own logic for class A
   }
}

B extends C {

}

C {
   abc() {
   }
}
 */
public class ThisSuperDemo {

    // ==================== 'this' KEYWORD ====================

    // 1. Using 'this' to differentiate instance variable from parameter
    static class Student {
        String name;
        int age;

        // Without 'this', the parameter shadows the instance variable
        Student(String name, int age) {
            //this.age = age;
            this.name = name;   // this.name = instance variable
            this.age = age;     // this.age = instance variable
        }

        // 2. Using 'this()' for constructor chaining
        Student() {
            this("any", 10);  // calls the 2-arg constructor above
            System.out.println("Default constructor called via this()");
        }

        Student(int age) {
            this("any", age);  // calls the 2-arg constructor above
            System.out.println("Default constructor called via this()");
        }

        void display() {
            String name = "";
            System.out.println("Student: " + this.name + ", Age: " + age);
        }
    }

    // ==================== 'super' KEYWORD ====================

    static class Animal {
        String name;
        String type = "Animal";

//        Animal() {
//
//        }
//        Animal() {
//        }

//        Animal() {
//            System.out.println("Inside animal constructor");
//        }

        Animal(String name, String type) {
            this.type = type;
            this.name = name;
            System.out.println("  Animal constructor called for: " + name);
        }

        void makeSound() {
            System.out.println(name + " makes a generic sound.");
        }
    }

    static class Dog extends Animal {
        String breed;
        String type = "Dog";  // shadows parent's 'type' field
        /*
        Dog
        _________ // breed
        ref-Animal -

        ---some other animal instance

         */
        // 1. super() to call parent constructor
        Dog(String name, String breed, String type, String type1) {
            //super();
            //super(name);  // MUST be the first line — calls Animal(String name)
            //super();
            // ----

            // ---
            super(name, type);
            //super();
            //super.type = this.type;
            //super();
            this.type = type1;
            System.out.println("Inside dog constructor");
            //super();
            this.breed = breed;

            //Animal animal = new Animal("");
            System.out.println("  Dog constructor called for: " + name);

        }

        // 2. super.method() to call parent's overridden method
        @Override
        void makeSound() {
            super.makeSound();  // calls Animal's makeSound()
            System.out.println(name + " says: Woof! Woof!");
        }

        // 3. super.variable to access parent's shadowed variable
        void showType() {
            String type = "";
            System.out.println("type = " + type);    // "Dog"

            System.out.println("this.type = " + this.type);    // "Dog"
            System.out.println("super.type = " + super.type);  // "Animal"
        }
    }

//    static class Dog1 extends Dog {
//
//        Dog1(String name, String breed) {
//            super(name, breed);
//        }
//
//        @Override
//        void makeSound() {
//            super.makeSound();
//        }
//
//    }

    // ==================== MAIN ====================
    public static void main(String[] args) {

        // --- 'this' keyword demos ---
       /* System.out.println("=== 'this' keyword ===");
        Student s1 = new Student("Rahul", 22);
        s1.display();

        System.out.println();
        Student s2 = new Student();  // uses this() to chain
        s2.display();

        // --- 'super' keyword demos ---
        System.out.println("\n=== 'super' keyword ===");
        System.out.println("Creating a Dog (watch constructor chain):");*/
        Animal dog = new Dog("Buddy", "Labrador", "type", "type1");
        dog.makeSound();

        /*System.out.println("\nCalling overridden method (super.makeSound inside):");
        dog.makeSound();

        System.out.println("\nAccessing shadowed variable:");
        dog.showType();*/
    }
}
