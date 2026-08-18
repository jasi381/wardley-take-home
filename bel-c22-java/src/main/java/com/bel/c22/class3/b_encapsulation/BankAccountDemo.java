package com.bel.c22.class3.b_encapsulation;

public class BankAccountDemo {
    public static void main(String[] args) {
        //BankAccount account = BankAccount.getInstance();

        BankAccount account = new BankAccount("Rahul", 1000);
        // account.age = 10000;
        System.out.println(account.getAge());
        account.setAge(-100);
        //account.balance = 1000000000;
        //System.out.println(account.balance);
        //account.balance = -100000;
        //account.setAccountHolder("Rajat");
        //System.out.println(account.getAccountHolder() + " " + account.getBalance());
        /*BankAccount account = new BankAccount("Rahul", 1000);
        account.setAccountHolder("aa");

        // Reading data via getters (allowed)
        System.out.println("Account Holder: " + account.getAccountHolder());
        System.out.println("Balance: " + account.getBalance());

        // Modifying balance via controlled methods (allowed, with validation)
        account.deposit(500);
        account.withdraw(200);

        // Trying invalid operations (encapsulation protects us)
        account.deposit(-100);     // Rejected: negative deposit
        account.withdraw(5000);    // Rejected: insufficient balance

        // CANNOT do this because balance is private:
        // account.balance = 999999;   // Compile error!
        // account.balance = -1;       // Compile error!

        System.out.println("\nFinal Balance: " + account.getBalance());*/
    }
}
