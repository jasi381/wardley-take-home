package com.bel.c22.class3.c_access_modifiers.differentpackage;

//import com.bel.c22.class3.c_access_modifiers.samepackage.Person;

public class DifferentPackageDemo {
    public static void main(String[] args) {
        com.bel.c22.class3.c_access_modifiers.samepackage.Person person = new com.bel.c22.class3.c_access_modifiers.samepackage.Person();
        //person.name;

        // PUBLIC - accessible from different package (YES)
        //System.out.println("Name: " + person.nickname);
        person.introduce();

        System.out.println();

        // DEFAULT - NOT accessible from different package (compile error if uncommented)
        // System.out.println(person.nickname);  // ERROR: nickname is not public; cannot be accessed from outside package
        // person.greetCasually();                // ERROR: greetCasually() is not public; cannot be accessed from outside package

        // PRIVATE - NOT accessible (compile error if uncommented)
        // System.out.println(person.secret);     // ERROR: secret has private access
        // person.think();                         // ERROR: think() has private access
    }
}
