package com.bel.c22.class3.c_access_modifiers.samepackage;

public class Person {

    // PRIVATE - only accessible within this class
    private String secret = "I am afraid of spiders";

    // DEFAULT (no modifier) - accessible within the same package only
    String nickname = "Rocky";

    // PUBLIC - accessible from anywhere
    public String name = "Rahul";

    // Private method
    private void think() {
        // Can access everything here (same class)
        System.out.println(name + " is thinking about: " + secret);
    }

    // Default method
    void greetCasually() {
        System.out.println("Hey! Call me " + nickname);
    }

    // Public method
    public void introduce() {
        System.out.println("Hello, my name is " + name);
        // Can call private method from within the same class
        think();
    }
}
