package com.bel.c22.class1.s1_introduction;

import com.bel.c22.class1.s3_variables_scope.VariableScopeDemo;

/**
 * HelloWorld — The very first Java program.
 *
 * Every Java program needs:
 *  1. A class (same name as the file)
 *  2. A main method — this is where execution starts
 */
/*
[___31___          _23_     __        ]
class Movie {
    string title = "Durandhar" / "Pa"
    int duration
    Person producer;
    Person director;
    List<String> genre
    int age;
}
class Person {
   string name;
   int age;
}
----------------[------]--------------------------
Person producer1 = new Person("p1", 40);
Person director1 = new Person("d1", 50);
Person producer2 = new Person("p2", 30);

Movie movie1 = new Movie("T1", 1000, null, null, ["Action", "Comedy"])
movie1.producer.age;
Movie movie2 = new Movie("T2", 1001, producer2, director1)
1200
1204


class Show {
   seatsBooked;
   startTime
   seatMap

   selectSeat(...) {

   }

}
----------------
----------------
----------------
----------------

1000 - 4 bytes (int)
[number,address] - "12345duration, 1200"
1200 -> "Durandhar"
1200 - 1230 / 1250
class Car {
   color
   engine
   mileage

   stop()
   start()
   break()
}

objects - instance of a class
-----------------
__  __    __



------------------
 */
public class HelloWorld {

    // main()     // main() is the entry point of every Java applicationis the entry point of every Java application
    public static void main(String[] args) {
        VariableScopeDemo s1 = new VariableScopeDemo("Alice", 120);
        // Movie movie1 = new Movie("Durandhar2", "url");
        // Movie movie2 = new Movie("Durandhar", "url2");
        // System.out.println() prints text to the console and adds a new line
        System.out.println("Hello, World!");

        // System.out.print() prints without adding a new line at the end
        System.out.print("Java is ");
        System.out.print("fun!");

        System.out.println(); // just prints a blank new line
        System.out.println("Welcome to Backend Engineering Launchpad!");
    }

    public static void main1() {

    }
}
