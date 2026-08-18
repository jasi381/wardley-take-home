package com.bel.c22.a_observer;

import java.util.ArrayList;
import java.util.List;

/**
 * OBSERVER PATTERN
 *
 * Intent: Define a one-to-many dependency between objects so that when one object
 *         (the Subject) changes state, all its dependents (Observers) are notified
 *         and updated automatically.
 *
 * Problem it solves — "Polling" anti-pattern:
 *   Without Observer, dependents would need to keep checking (polling) for changes.
 *   That's wasteful and tightly couples everyone to the subject's internals.
 *
 * When to use:
 *   - One object changes and multiple others need to react
 *   - You want loose coupling between the source of change and the listeners
 *   - Event-driven systems, UI updates, notification systems
 *
 * Structure:
 *   1. Subject (Observable) — maintains list of observers, notifies them on change
 *   2. Observer             — interface with an update() method
 *   3. Concrete Observers   — react to the subject's state change
 */
public class ObserverDemo {

    // ── OBSERVER interface ──────────────────────────────────────────────────
    interface Observer {
        void update(String stockName, double price);
    }

    // ── SUBJECT (Observable) ────────────────────────────────────────────────
    // Maintains a list of observers and notifies them when price changes.
    static class Stock {
        private final String name;
        private double price;
        private final List<Observer> observers = new ArrayList<>();

        Stock(String name, double initialPrice) {
            this.name = name;
            this.price = initialPrice;
        }

        public void addObserver(Observer o)    { observers.add(o); }
        public void removeObserver(Observer o) { observers.remove(o); }

        // When price changes → notify all observers
        public void setPrice(double newPrice) {
            System.out.println("\n  [Stock] " + name + " price changed: $" + price + " → $" + newPrice);
            this.price = newPrice;
            notifyObservers();
        }

        private void notifyObservers() {
            for (Observer o : observers) {
                o.update(name, price);
            }
        }
    }

    // ── CONCRETE OBSERVERS ──────────────────────────────────────────────────

    // Observer 1: Displays price on a dashboard
    static class DashboardDisplay implements Observer {
        public void update(String stockName, double price) {
            System.out.println("  [Dashboard] " + stockName + " is now $" + price);
        }
    }

    // Observer 2: Sends alert if price drops below threshold
    static class PriceAlertService implements Observer {
        private final double threshold;

        PriceAlertService(double threshold) { this.threshold = threshold; }

        public void update(String stockName, double price) {
            if (price < threshold) {
                System.out.println("  [ALERT] " + stockName + " dropped below $" + threshold + "!");
            } else {
                System.out.println("  [Alert] " + stockName + " is above threshold. No alert.");
            }
        }
    }

    // Observer 3: Logs every price change to a file (simulated)
    static class PriceLogger implements Observer {
        public void update(String stockName, double price) {
            System.out.println("  [Logger] Logged: " + stockName + " = $" + price);
        }
    }

    // ── DEMO ────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        System.out.println("=== OBSERVER PATTERN: Stock Price Tracker ===\n");

        // 1. Create the subject
        Stock apple = new Stock("AAPL", 150.0);

        // 2. Create observers
        DashboardDisplay dashboard = new DashboardDisplay();
        PriceAlertService alert = new PriceAlertService(140.0);
        PriceLogger logger = new PriceLogger();

        // 3. Register observers
        apple.addObserver(dashboard);
        apple.addObserver(alert);
        apple.addObserver(logger);

        // 4. Price changes → all three observers get notified
        apple.setPrice(155.0);
        apple.setPrice(135.0);   // triggers alert!

        // 5. Unsubscribe logger, then change again
        System.out.println("\n  --- Logger unsubscribed ---");
        apple.removeObserver(logger);
        apple.setPrice(142.0);   // only dashboard + alert notified

        System.out.println("\n=== KEY TAKEAWAY ===");
        System.out.println("  Subject doesn't know WHO is observing — just calls update().");
        System.out.println("  Observers can be added/removed at runtime. Loose coupling!");
    }
}
