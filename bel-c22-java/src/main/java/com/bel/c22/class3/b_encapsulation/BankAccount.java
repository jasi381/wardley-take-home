package com.bel.c22.class3.b_encapsulation;

import java.awt.desktop.SystemEventListener;

/*
BankAccount b = new BankAccount("Rajath", 100);
b.balance = 1000;
 */
public class BankAccount {

    // Private fields - cannot be accessed directly from outside
    private String accountHolder;
    private double balance;
    private int age;

    // Constructor
    public BankAccount(String accountHolder, double initialBalance) {
        this.accountHolder = accountHolder;
        if (initialBalance >= 0) {
            this.balance = initialBalance;
        } else {
            this.balance = 0;
            System.out.println("Initial balance cannot be negative. Setting to 0.");
        }
    }

    public static BankAccount getInstance() {
        return new BankAccount("ABC", 199);
    }

    // Getter for accountHolder
    public String getAccountHolder() {
        return accountHolder;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        if (age > 0 && age < 100) {
            this.age = age;
        }
    }

    // Getter for balance
    public double getBalance() {
        return balance;
    }

    // Setter for accountHolder (no validation needed here)
    public void setAccountHolder(String accountHolder) {
        if (accountHolder.isBlank()) {
            System.out.println("Not a valid name");
        } else {
            this.accountHolder = accountHolder;
        }
    }

    // No direct setter for balance! Instead, we provide controlled methods:

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount + " | New Balance: " + balance);
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    public void withdraw(double amount) {
        // amount - 50, balance - 100
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: " + amount + " | New Balance: " + balance);
        } else if (amount > balance) {
            System.out.println("Insufficient balance! Current balance: " + balance);
        } else {
            System.out.println("Withdrawal amount must be positive.");
        }
    }
}
