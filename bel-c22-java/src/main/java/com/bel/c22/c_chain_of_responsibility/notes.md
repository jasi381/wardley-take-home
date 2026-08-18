# Chain of Responsibility Pattern

## Intent
Pass a request along a **chain of handlers**. Each handler either **processes** it or **passes** it to the next.

## The Core Roles
| Role | Responsibility |
|------|---------------|
| **Handler** (abstract) | Defines `handle()`, holds `next` reference |
| **Concrete Handler** | Decides if it can process, or passes along |
| **Client** | Sends request to the first handler in the chain |

## How It Works
```
Request → Handler A → Handler B → Handler C → (end)
            │              │            │
         can handle?   can handle?  can handle?
         no → pass     no → pass    yes → process
```
1. Client sends request to the **first** handler
2. Each handler checks: "Can I handle this?"
3. If yes → process it. If no → pass to `next`
4. If no handler can process → request falls off the chain

## Code Structure
```java
abstract class SupportHandler {
    private SupportHandler next;

    SupportHandler setNext(SupportHandler next) {
        this.next = next;
        return next;  // fluent chaining
    }

    void handle(Ticket t) {
        if (canHandle(t)) process(t);
        else if (next != null) next.handle(t);
    }

    abstract boolean canHandle(Ticket t);
    abstract void process(Ticket t);
}
```

## Real-World Uses
- **Servlet Filters**: `doFilter()` → next filter → servlet
- **Middleware**: Express.js `app.use()`, Spring `HandlerInterceptor`
- **Logging frameworks**: DEBUG → INFO → WARN → ERROR level handlers
- **Exception handling**: catch blocks in Java (`try { } catch A { } catch B { }`)
- **Approval workflows**: Employee → Manager → Director → VP

## Chain of Responsibility vs. If-Else
| Approach | Adding new handler | Problem |
|----------|-------------------|---------|
| If-else | Modify existing code | Violates OCP, one giant class |
| Chain | Add new handler class, link it in | Existing handlers untouched |

## Two Variants
| Variant | Behavior |
|---------|----------|
| **Pure** | Exactly one handler processes the request (others skip) |
| **Pipeline** | Every handler processes + passes along (e.g., filters, middleware) |

## Common Pitfalls
- **Unhandled requests**: If no handler matches, request is silently dropped. Always add a fallback at the end.
- **Order matters**: The chain order determines priority. Wrong order = wrong handler processes.
- **Performance**: Very long chains can be slow for every request.

## Quick Memory Aid
> "Pass the buck until someone handles it."
