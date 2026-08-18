# State Pattern

## Intent
Allow an object to **alter its behavior when its internal state changes**. The object will appear to change its class.

## The Core Roles
| Role | Responsibility |
|------|---------------|
| **State** (interface) | Declares operations that differ by state |
| **Concrete State** | Implements behavior for one state; triggers transitions via `context.setState(...)` |
| **Context** | Holds current State; delegates all requests to it |

## How It Works
```
Client calls: doc.submit()
                  ↓
           Document (Context)
                  ↓  delegates to
           currentState.submit(this)
                  ↓
       [DraftState / ReviewState / PublishedState]
       each handles it differently AND can switch the state
```

## State Transition Diagram
```
        submit()         approve()
  [Draft] ────────→ [Review] ────────→ [Published]
```

## Code Structure
```java
interface DocumentState {
    void edit(Document doc);
    void submit(Document doc);
    void approve(Document doc);
}

class Document {                      // Context
    private DocumentState state = new DraftState();
    void setState(DocumentState s) { state = s; }
    void submit() { state.submit(this); }   // delegates
}

class DraftState implements DocumentState {
    public void submit(Document doc) {
        System.out.println("Submitted for review.");
        doc.setState(new ReviewState());     // transition!
    }
    public void approve(Document doc) {
        System.out.println("Can't approve — not submitted yet.");
    }
}
```

## State vs. Strategy
| | State | Strategy |
|--|-------|----------|
| **Who switches?** | State switches itself (via Context) | Client switches explicitly |
| **States know each other?** | Yes — transitions reference next state | No — strategies are independent |
| **Purpose** | Model lifecycle / finite state machine | Swap algorithm at runtime |

## Real-World Uses
- **Order lifecycle**: PLACED → CONFIRMED → SHIPPED → DELIVERED
- **TCP connection**: LISTEN → ESTABLISHED → CLOSED
- **Game character**: IDLE → RUNNING → JUMPING → DEAD

## Quick Memory Aid
> "The context is the machine. Each state is a mode. Switching the mode changes everything the machine does."
