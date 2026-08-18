package com.bel.c22.class5.d_aggregation;

import java.util.ArrayList;
import java.util.List;

public class AggregationDemo {

    // ==================== AGGREGATION ("has-a" with weak ownership) ====================
    // Parent contains children, but children can exist independently.
    // Children are created OUTSIDE and passed INTO the parent.

    static class Book {
        String title;
        String author;

        Book(String title, String author) {
            this.title = title;
            this.author = author;
        }

        void displayInfo() {
            System.out.println("    \"" + title + "\" by " + author);
        }
    }

    static class Library {
        String name;
        List<Book> books;  // Aggregation — Library HAS books, but doesn't own them

        Library(String name) {
            this.name = name;
            this.books = new ArrayList<>();
        }

        // Books are added from outside — Library doesn't create them
        void addBook(Book book) {
            books.add(book);
        }

        void displayInfo() {
            System.out.println("Library: " + name);
            System.out.println("  Books:");
            for (Book book : books) {
                book.displayInfo();
            }
        }
    }

    // ==================== ANOTHER EXAMPLE: Team and Coach ====================

    static class Coach {
        String name;

        Coach(String name) {
            this.name = name;
        }
    }

    static class Team {
        String teamName;
        Coach coach;  // Aggregation — Team HAS a Coach, but Coach exists independently

        Team(String teamName, Coach coach) {
            this.teamName = teamName;
            this.coach = coach;  // coach created outside, passed in
        }

        void displayInfo() {
            System.out.println("Team: " + teamName + " | Coach: " + coach.name);
        }
    }

    // ==================== MAIN ====================
    public static void main(String[] args) {

        // --- Library and Books ---
        System.out.println("=== Aggregation: Library -> Books ===");
        Book b1 = new Book("Java Head First", "Kathy Sierra");
        Book b2 = new Book("Clean Code", "Robert Martin");
        Book b3 = new Book("Design Patterns", "Gang of Four");

        Library library = new Library("City Library");
        library.addBook(b1);
        library.addBook(b2);
        library.addBook(b3);
        library.displayInfo();

        // Key point: If library is destroyed, books still exist!
        library = null;  // library is gone
        System.out.println("\nLibrary destroyed, but books still exist:");
        b1.displayInfo();  // book is still alive!
        b2.displayInfo();

        // --- Team and Coach ---
        System.out.println("\n=== Aggregation: Team -> Coach ===");
        Coach coach = new Coach("Ravi Kumar");
        Team team1 = new Team("Mumbai Indians", coach);
        Team team2 = new Team("Chennai Super Kings", coach);  // same coach, different teams!
        team1.displayInfo();
        team2.displayInfo();
    }
}
