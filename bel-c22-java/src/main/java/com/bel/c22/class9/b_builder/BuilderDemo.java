package com.bel.c22.class9.b_builder;

/**
 * BUILDER PATTERN
 *
 * Intent: Construct complex objects step-by-step, separating construction from representation.
 *
 * Problem it solves — "Telescoping Constructor" anti-pattern:
 *   new User("Alice", 25, "alice@email.com", "NY", true, false, "premium")
 *   ← which param is which? What are the defaults? Hard to read, hard to use.
 *
 * When to use:
 *   - Object has many optional parameters
 *   - Construction needs validation before the object is created
 *   - Object should be immutable after construction
 *
 * Structure:
 *   1. Product        — the object being built (immutable fields, private constructor)
 *   2. Builder        — static nested class, stores params, exposes fluent setters
 *   3. build()        — validates and creates the Product
 */
public class BuilderDemo {

    // ── PRODUCT: HTTP Request ────────────────────────────────────────────────
    // Immutable once built. Private constructor — only Builder can create it.
    public static class HttpRequest {
        // Required
        private final String method;
        private final String url;
        // Optional
        private String body;
        private int timeoutSeconds;
        private boolean followRedirects;
        private String authToken;

        private HttpRequest(Builder b) {
            this.method = b.method;
            this.url = b.url;
            this.body = b.body;
            this.timeoutSeconds = b.timeoutSeconds;
            this.followRedirects = b.followRedirects;
            this.authToken = b.authToken;
        }

        /*private HttpRequest(String method, String url) {
            this.method = method;
            this.url = url;
        }

        private HttpRequest(String method, String url, int timeoutSeconds) {
            this.method = method;
            this.url = url;
            this.timeoutSeconds = timeoutSeconds;
        }

        private HttpRequest(String method, String url, String body) {
            this.method = method;
            this.url = url;
            this.body = body;
        }

        private HttpRequest(String method, String url, String body, int timeoutSeconds) {
            this.method = method;
            this.url = url;
            this.body = body;
            this.timeoutSeconds = timeoutSeconds;
        }*/

        /*public void setBody(String body) {
            this.body = body;
        }*/

        public String toString() {
            return method + " " + url
                    + "\n    body=" + (body != null ? body : "(none)")
                    + ", timeout=" + timeoutSeconds + "s"
                    + ", redirects=" + followRedirects
                    + ", auth=" + (authToken != null ? "yes" : "no");
        }

        // ── BUILDER ─────────────────────────────────────────────────────────
        public static class Builder {
            // Required fields
            private final String method;
            private final String url;
            // Optional fields — defaults defined here
            private String body          = null;
            private int timeoutSeconds   = 30;
            private boolean followRedirects = true;
            private String authToken     = null;

            // Constructor takes only required params
            public Builder(String method, String url) {
                this.method = method;
                this.url = url;
            }

            // Fluent setters — each returns `this` so calls can be chained
            public Builder body(String body)                    { this.body = body;                       return this; }
            public Builder timeout(int seconds)                 { this.timeoutSeconds = seconds;          return this; }
            public Builder followRedirects(boolean follow)      { this.followRedirects = follow;          return this; }
            public Builder authToken(String token)              { this.authToken = token;                 return this; }

            // Validates then creates the immutable product
            public HttpRequest build() {
                if (method == null || method.isEmpty()) throw new IllegalStateException("method required");
                if (url == null || url.isEmpty())       throw new IllegalStateException("url required");
                return new HttpRequest(this);
            }
        }
    }

    // ── PRODUCT 2: User Profile ──────────────────────────────────────────────
    // Shows the "telescoping constructor" problem and how Builder fixes it.
    static class UserProfile {
        private final String username;   // required
        private final String email;      // required
        private final int age;
        private final String city;
        private final boolean newsletter;

        private UserProfile(Builder b) {
            this.username = b.username; this.email = b.email;
            this.age = b.age; this.city = b.city; this.newsletter = b.newsletter;
        }

        public String toString() {
            return username + " <" + email + ">"
                    + " age=" + age + " city=" + city + " newsletter=" + newsletter;
        }

        static class Builder {
            private final String username;
            private final String email;
            private int age          = 0;
            private String city      = "Unknown";
            private boolean newsletter = false;

            Builder(String username, String email) { this.username = username; this.email = email; }
            Builder age(int age)                   { this.age = age;           return this; }
            Builder city(String city)              { this.city = city;         return this; }
            Builder newsletter(boolean v)          { this.newsletter = v;      return this; }
            UserProfile build()                    { return new UserProfile(this); }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== BUILDER PATTERN ===\n");

       /* System.out.println("1. HTTP Request — minimal (required fields only):");
        HttpRequest get = new HttpRequest.Builder("GET", "https://api.example.com/users")
                .build();
        System.out.println("  " + get);*/

        //HttpRequest req1 = new HttpRequest("GET", "xyz");
        //req1.setBody("body1");
       // req1.setBody("body2");


        System.out.println("\n2. HTTP Request — fully configured:");
        HttpRequest.Builder postBuilder = new HttpRequest.Builder("POST", "https://api.example.com/users")
                .body("{\"name\":\"Alice\"}")
                .timeout(10)
                .followRedirects(false)
                .authToken("Bearer abc123");

        postBuilder.body("body2");

        HttpRequest post = postBuilder.build();
        System.out.println("  " + post);

        postBuilder.body("body3");

        System.out.println("  " + post);
        HttpRequest post1 = postBuilder.build();
        System.out.println("  " + post);

        System.out.println("  " + post1);



        /*System.out.println("\n3. User Profile — readable, self-documenting:");
        UserProfile user = new UserProfile.Builder("alice", "alice@email.com")
                .age(25)
                .city("New York")
                .newsletter(true)
                .build();
        System.out.println("  " + user);

        System.out.println("\n4. User Profile — only required fields, rest are defaults:");
        UserProfile minimal = new UserProfile.Builder("bob", "bob@email.com").build();
        System.out.println("  " + minimal);*/
    }
}
