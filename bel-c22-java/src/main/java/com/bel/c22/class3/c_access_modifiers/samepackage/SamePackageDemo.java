package com.bel.c22.class3.c_access_modifiers.samepackage;

public class SamePackageDemo {
    public static void main(String[] args) {
        Person person = new Person();

        // PUBLIC - accessible (YES)
        System.out.println("Name: " + person.name);
        person.introduce();

        System.out.println();

        // DEFAULT - accessible from same package (YES)
        System.out.println("Nickname: " + person.nickname);
        person.greetCasually();

        System.out.println();

        // PRIVATE - NOT accessible (compile error if uncommented)
        // System.out.println(person.secret);  // ERROR: secret has private access
        // person.think();                      // ERROR: think() has private access
    }
}
