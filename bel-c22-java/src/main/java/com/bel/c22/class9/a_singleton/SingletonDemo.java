package com.bel.c22.class9.a_singleton;

import com.bel.c22.class9.b_builder.BuilderDemo;

/**
 * SINGLETON PATTERN
 *
 *
 * DB - 1
 *
 * Zomato Server (100) -> DB (1)
 * DBConnection class -> new DBConnection(loginID, password)
 * UserRepo ---> DB
 * OrderRepo ----> DB
 * RestaurantRepo ---> DB
 *
 * 1 microservice -> 100 servers
 *
 *
 * class UserService {
 *     List<Users> users
 * }
 *
 * obj1 -> u1, u2, u3, u4, u5
 * obj2 -> u6, u7
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 * orderService {
 *     List<Orders>
 *     OrderService() {}
 *
 * }
 *  o1 =  new OrderService();
 *  o2 = new OrderService();
 *
 * Intent: Ensure a class has only ONE instance and provide a global access point to it.
 *
 * When to use:
 *   - Shared resource with a single owner (DB connection pool, config, logger)
 *   - Controlling access to a shared resource
 *   - One coordinator object needed across the whole app
 *
 * Structure:
 *   1. Private constructor   — nobody can call `new`
 *   2. Private static field  — holds the one instance
 *   3. Public static method  — returns that instance (creates it lazily if needed)
 */
public class SingletonDemo {

    // ── VARIANT 1: Eager Initialization ─────────────────────────────────────
    // Instance created when class is loaded. Simple, but wastes memory if never used.
    static class EagerSingleton {
        private static final EagerSingleton INSTANCE = new EagerSingleton();

        private EagerSingleton() {}

        public static EagerSingleton getInstance() { return INSTANCE; }

        public void log(String msg) { System.out.println("  [EagerLogger] " + msg); }
    }

    // ── VARIANT 2: Lazy Initialization (NOT thread-safe) ────────────────────
    // Instance created on first call. Fine for single-threaded apps.
    static class LazySingleton {
        private static LazySingleton instance;   // null until first call

        private LazySingleton() {}

        public static LazySingleton getInstance() {
            // R1 -> T1, R2 -> T2
            if (instance == null) {
                // T1 // ← race condition in multi-threaded env
                // T2
                instance = new LazySingleton(); // Obj1, Obj2
            }
            return instance;
        }

        public void log(String msg) { System.out.println("  [LazyLogger] " + msg); }
    }

    // ── VARIANT 3: Thread-Safe (Double-Checked Locking) ─────────────────────
    // The production-grade lazy singleton. synchronized inner check.
    static class ThreadSafeSingleton {
        private static ThreadSafeSingleton instance;

        private ThreadSafeSingleton() {}

        public static ThreadSafeSingleton getInstance1() {
            // T1, T2
            // 5 mins
            // t3, t4
            // T1, T2
                synchronized (ThreadSafeSingleton.class) { // T2
                    // T2
                    if (instance == null) {
                        instance = new ThreadSafeSingleton();
                    }
                }
            return instance;
        }

        public static ThreadSafeSingleton getInstance() {
            // T1, T2

            // After 5 mins
            // T3, T4, T5
            if (instance == null) {
                // T1, T2, ... T10// 1st check — fast path (no lock)
                // T1, T2
                synchronized (ThreadSafeSingleton.class) {
                    if (instance == null)
                    {      // 2nd check — safe path (inside lock)
                        instance = new ThreadSafeSingleton();
                    }
                }
            }
            return instance;
        }

        public void log(String msg) { System.out.println("  [ThreadSafeLogger] " + msg); }
    }

    public static void main(String[] args) {
        System.out.println("=== SINGLETON PATTERN ===\n");

        // All calls return the SAME object — same hashCode proves it
        System.out.println("1. Eager Singleton:");
        EagerSingleton e1 = EagerSingleton.getInstance();
        EagerSingleton e2 = EagerSingleton.getInstance();
        e1.log("App started");
        System.out.println("  Same instance? " + (e1 == e2));

        System.out.println("\n2. Lazy Singleton:");
        LazySingleton l1 = LazySingleton.getInstance();
        LazySingleton l2 = LazySingleton.getInstance();
        l1.log("First call creates it");
        System.out.println("  Same instance? " + (l1 == l2));

        System.out.println("\n3. Thread-Safe (Double-Checked Locking):");
        ThreadSafeSingleton t1 = ThreadSafeSingleton.getInstance();
        ThreadSafeSingleton t2 = ThreadSafeSingleton.getInstance();
        t1.log("Safe for multi-threaded use");
        System.out.println("  Same instance? " + (t1 == t2));


        System.out.println("\n2. HTTP Request — fully configured:");
        BuilderDemo.HttpRequest.Builder postBuilder = new BuilderDemo.HttpRequest.Builder("POST", "https://api.example.com/users")
                //.body("{\"name\":\"Alice\"}")
                //.timeout(10)
                //.followRedirects(false)
                .authToken("Bearer abc123");

        postBuilder.body("body2");

        BuilderDemo.HttpRequest post = postBuilder.build();

        postBuilder.timeout(5);
        //post.body()
    }
}
