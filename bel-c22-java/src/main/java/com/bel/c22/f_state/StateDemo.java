package com.bel.c22.f_state;

/**
 * STATE PATTERN
 *
 * Intent: Allow an object to alter its behavior when its internal state changes.
 *         The object will appear to change its class.
 *
 * Problem it solves — "Giant if-else on state" anti-pattern:
 *   if (state == DRAFT)     { doX(); }
 *   else if (state == REVIEW) { doY(); }
 *   Adding a state → edit every if-chain. Violates Open/Closed Principle.
 *
 * When to use:
 *   - An object's behavior depends on its state and changes at runtime
 *   - You have conditionals that switch on state in multiple methods
 *
 * Structure:
 *   1. State (interface)  — declares operations whose behavior varies by state
 *   2. Concrete States    — each implements behavior for one specific state
 *   3. Context            — holds current State, delegates requests to it
 */
public class StateDemo {

    // ── STATE interface ─────────────────────────────────────────────────────
    interface DocumentState {
        void edit(Document doc);
        void submit(Document doc);
        void approve(Document doc);
    }

    // ── CONCRETE STATE 1: Draft ─────────────────────────────────────────────
    static class DraftState implements DocumentState {
        public void edit(Document doc)    { System.out.println("  [Draft] Editing document..."); }
        public void submit(Document doc)  {
            System.out.println("  [Draft] Submitted for review.");
            doc.setState(new ReviewState());
        }
        public void approve(Document doc) { System.out.println("  [Draft] Can't approve — not submitted yet."); }
    }

    // ── CONCRETE STATE 2: Review ────────────────────────────────────────────
    static class ReviewState implements DocumentState {
        public void edit(Document doc)    { System.out.println("  [Review] Can't edit — under review."); }
        public void submit(Document doc)  { System.out.println("  [Review] Already submitted."); }
        public void approve(Document doc) {
            System.out.println("  [Review] Approved! Document is now published.");
            doc.setState(new PublishedState());
        }
    }

    // ── CONCRETE STATE 3: Published ─────────────────────────────────────────
    static class PublishedState implements DocumentState {
        public void edit(Document doc)    { System.out.println("  [Published] Can't edit — already published."); }
        public void submit(Document doc)  { System.out.println("  [Published] Already published."); }
        public void approve(Document doc) { System.out.println("  [Published] Already approved."); }
    }

    // ── CONTEXT ─────────────────────────────────────────────────────────────
    static class Document {
        private DocumentState state = new DraftState();   // starts as draft

        void setState(DocumentState state) { this.state = state; }

        // Public API — just delegates to current state
        void edit()    { state.edit(this); }
        void submit()  { state.submit(this); }
        void approve() { state.approve(this); }
    }

    // ── DEMO ────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        System.out.println("=== STATE PATTERN: Document Workflow ===\n");

        Document doc = new Document();

        // In Draft state
        doc.edit();       // OK
        doc.approve();    // Can't — not submitted
        doc.submit();     // Draft → Review

        // In Review state
        System.out.println();
        doc.edit();       // Can't — under review
        doc.submit();     // Already submitted
        doc.approve();    // Review → Published
/*
---
---
---

B1 B2 B3






abs action1()

X extends DocState {
  action1() {
}

Review extends X {
}
}

 */
        // In Published state
        System.out.println();
        doc.edit();       // Can't — published
        doc.approve();    // Already approved

        System.out.println("\n=== KEY TAKEAWAY ===");
        System.out.println("  Each state class handles ALL operations for that state.");
        System.out.println("  Context just delegates — no if/else chains.");
        System.out.println("  Adding a new state = new class, nothing else changes.");
    }
}
