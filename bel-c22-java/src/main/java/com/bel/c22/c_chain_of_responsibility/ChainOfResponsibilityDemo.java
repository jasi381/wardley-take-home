package com.bel.c22.c_chain_of_responsibility;

/**
 * CHAIN OF RESPONSIBILITY PATTERN
 *
 * Intent: Pass a request along a chain of handlers. Each handler decides either
 *         to process the request or to pass it to the next handler in the chain.
 *
 * Problem it solves — "God object" anti-pattern:
 *   One class with a giant if-else to decide who handles a request.
 *   Adding a new handler → modify that class. Violates SRP and OCP.
 *
 * When to use:
 *   - Multiple objects can handle a request, but the handler isn't known upfront
 *   - You want to decouple the sender from the receiver
 *   - You want to build a processing pipeline (logging, auth, validation)
 *
 * Structure:
 *   1. Handler (abstract)    — defines handle() + holds reference to next handler
 *   2. Concrete Handlers     — process request or pass it along
 *   3. Client                — sends request to the first handler in the chain
 */
public class ChainOfResponsibilityDemo {

    // ── REQUEST object ──────────────────────────────────────────────────────
    static class SupportTicket {
        private final String issue;
        private final int severity; // 1 = low, 2 = medium, 3 = high

        SupportTicket(String issue, int severity) {
            this.issue = issue;
            this.severity = severity;
        }

        public String getIssue()   { return issue; }
        public int getSeverity()   { return severity; }
        public String toString()   { return "[Severity " + severity + "] " + issue; }
    }

    // ── HANDLER (abstract base) ─────────────────────────────────────────────
    static abstract class SupportHandler {
        private SupportHandler next;   // link to next handler

        // Set the next handler in the chain and return it (for fluent chaining)
        public SupportHandler setNext(SupportHandler next) {
            this.next = next;
            return next;   // allows: a.setNext(b).setNext(c)
        }

        // Template: try to handle, or pass to next
        public void handle(SupportTicket ticket) {
            if (canHandle(ticket)) {
                process(ticket);
            } else if (next != null) {
                next.handle(ticket);   // pass it along
            } else {
                System.out.println("  [END OF CHAIN] No handler for: " + ticket);
            }
        }

        protected abstract boolean canHandle(SupportTicket ticket);
        protected abstract void process(SupportTicket ticket);
    }

    // ── CONCRETE HANDLERS ───────────────────────────────────────────────────

    // Level 1: FAQ Bot handles low-severity issues
    static class FaqBot extends SupportHandler {
        protected boolean canHandle(SupportTicket ticket) {
            System.out.println("  [Faq Bot] Checking if it can handle the issue : " + ticket.getIssue());
            return ticket.getSeverity() == 1;
        }

        protected void process(SupportTicket ticket) {
            System.out.println("  [FAQ Bot] Auto-resolved: " + ticket.getIssue());
        }
    }

    // Level 2: Junior Agent handles medium-severity issues
    static class JuniorAgent extends SupportHandler {
        protected boolean canHandle(SupportTicket ticket) {
            System.out.println("  [Junior Agent] Checking if it can handle the issue : " + ticket.getIssue());

            return ticket.getSeverity() == 2;
        }

        protected void process(SupportTicket ticket) {
            System.out.println("  [Junior Agent] Handling: " + ticket.getIssue());
        }
    }

    // Level 3: Senior Engineer handles high-severity issues
    static class SeniorEngineer extends SupportHandler {
        protected boolean canHandle(SupportTicket ticket) {
            System.out.println("  [Senior Engineer] Checking if it can handle the issue : " + ticket.getIssue());

            return ticket.getSeverity() == 3;
        }

        protected void process(SupportTicket ticket) {
            System.out.println("  [Senior Engineer] Escalated & handling: " + ticket.getIssue());
        }
    }

    // ── DEMO ────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        System.out.println("=== CHAIN OF RESPONSIBILITY: Support Ticket System ===\n");

        // 1. Build the chain: FaqBot → JuniorAgent → SeniorEngineer
        SupportHandler faqBot = new FaqBot();
        SupportHandler junior = new JuniorAgent();
        SupportHandler senior = new SeniorEngineer();

        faqBot.setNext(junior).setNext(senior);

        // 2. Send tickets — each finds its own handler
        SupportTicket[] tickets = {
            new SupportTicket("How do I reset my password?", 1),
            new SupportTicket("Payment failed on checkout", 2),
            new SupportTicket("Database is corrupted!", 3),
            new SupportTicket("Unknown alien issue", 5)   // nobody handles this
        };

        for (SupportTicket ticket : tickets) {
            System.out.println("  Submitting: " + ticket);
            faqBot.handle(ticket);   // always start at the top of the chain
            System.out.println();
        }

        System.out.println("=== KEY TAKEAWAY ===");
        System.out.println("  Sender doesn't know WHO handles the request.");
        System.out.println("  Handlers are decoupled — add/remove without changing others.");
    }
}
