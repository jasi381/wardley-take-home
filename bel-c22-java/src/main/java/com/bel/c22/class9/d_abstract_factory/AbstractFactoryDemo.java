package com.bel.c22.class9.d_abstract_factory;

/**
 * ABSTRACT FACTORY PATTERN
 *
 *
 * Burger
 *
 * Roll
 *
 * McD Burger
 * McD Roll
 *
 * BK Burger
 * BK Roll
 *
 * Burger -> McD, BK
 * Roll -> McD, BK
 *
 * FastFoodOutlet {
 *     Burger sell()
 *     Roll sell()
 * }
 * McDOutlet extends FastFoodOutlet {
 *    Burger sell() {
 *        McD Factory.createBurger();
 *    }
 *    Roll sell() {
 *
 *    }
 * }
 *
 * FastFoodFactory {
 *     createBurger();
 *     createRoll();
 * }
 *
 * McD Factory implements FastFoodFactory{
 *     createBurger() {
 *         new McDBurger();
 *     }
 * }
 *
 * BK Factory implements FastFoodFactory{
 *  *     createBurger() {
 *  *         new BkBurger();
 *  *     }
 *  * }
 *
 * Intent: Provide an interface for creating FAMILIES of related objects
 *         without specifying their concrete classes.
 *
 * Factory Method  → creates ONE product; you choose the type.
 * Abstract Factory → creates a SUITE of related products that belong together.
 *
 * Classic analogy: a UI toolkit.
 *   - macOS factory  → macOS Button + macOS Checkbox + macOS TextField (all native macOS)
 *   - Windows factory → Windows Button + Windows Checkbox + Windows TextField
 *   Switching theme = swap one factory, every component stays consistent.
 *
 * When to use:
 *   - The system must be independent of how its products are created
 *   - Products come in families and must be used together
 *   - You want to enforce consistency among related products
 *
 * Structure:
 *   1. Abstract Products  — interfaces for each product kind (Button, Checkbox…)
 *   2. Concrete Products  — one set per family (MacButton, WinButton…)
 *   3. Abstract Factory   — declares create methods for each product type
 *   4. Concrete Factories — one per family, implements the abstract factory
 *   5. Client             — uses only abstract factory + abstract product interfaces
 */
public class AbstractFactoryDemo {

    // ── ABSTRACT PRODUCTS ────────────────────────────────────────────────────

    interface Button {
        void render();
        void onClick();
    }

    interface Checkbox {
        void render();
        void toggle();
    }

    interface TextField {
        void render();
        void onInput(String text);
    }

    // ── CONCRETE PRODUCTS: macOS family ──────────────────────────────────────

    static class MacButton implements Button {
        public void render()  { System.out.println("  [macOS] Rendering rounded aqua button"); }
        public void onClick() { System.out.println("  [macOS] Button click — soft haptic"); }
    }

    static class MacCheckbox implements Checkbox {
        public void render()      { System.out.println("  [macOS] Rendering macOS checkbox"); }
        public void toggle()      { System.out.println("  [macOS] Checkbox toggled — smooth animation"); }
    }

    static class MacTextField implements TextField {
        public void render()           { System.out.println("  [macOS] Rendering macOS text field"); }
        public void onInput(String t)  { System.out.println("  [macOS] Text input: " + t); }
    }

    // ── CONCRETE PRODUCTS: Windows family ────────────────────────────────────

    static class WinButton implements Button {
        public void render()  { System.out.println("  [Win]   Rendering flat rectangular button"); }
        public void onClick() { System.out.println("  [Win]   Button click — ripple effect"); }
    }

    static class WinCheckbox implements Checkbox {
        public void render()      { System.out.println("  [Win]   Rendering Windows checkbox"); }
        public void toggle()      { System.out.println("  [Win]   Checkbox toggled — square tick"); }
    }

    static class WinTextField implements TextField {
        public void render()           { System.out.println("  [Win]   Rendering Windows text field"); }
        public void onInput(String t)  { System.out.println("  [Win]   Text input: " + t); }
    }

    // ── ABSTRACT FACTORY ─────────────────────────────────────────────────────

    interface UIFactory {
        Button   createButton();
        Checkbox createCheckbox();
        TextField createTextField();
    }

    // ── CONCRETE FACTORIES ───────────────────────────────────────────────────

    static class MacUIFactory implements UIFactory {
        public Button    createButton()    { return new MacButton(); }
        public Checkbox  createCheckbox()  { return new MacCheckbox(); }
        public TextField createTextField() { return new MacTextField(); }
    }

    static class WindowsUIFactory implements UIFactory {
        public Button    createButton()    { return new WinButton(); }
        public Checkbox  createCheckbox()  { return new WinCheckbox(); }
        public TextField createTextField() { return new WinTextField(); }
    }

    // ── CLIENT ───────────────────────────────────────────────────────────────
    // Depends ONLY on UIFactory and the abstract product interfaces.
    // It has NO idea whether it's working with Mac or Windows components.
    static class LoginForm {
        private final Button   submitButton;
        private final Checkbox rememberMe;
        private final TextField usernameField;

//        LoginForm(Button button, Checkbox checkbox, TextField textField) {             // factory injected — no `new` inside
//            this.submitButton  = button;
//            this.rememberMe    = checkbox;
//            this.usernameField = textField;
//        }

        LoginForm(UIFactory factory) {             // factory injected — no `new` inside
            this.submitButton  = factory.createButton();
            this.rememberMe    = factory.createCheckbox();
            this.usernameField = factory.createTextField();
        }

        void render() {
            System.out.println("  Rendering Login Form:");
            usernameField.render();
            rememberMe.render();
            submitButton.render();
        }

        void simulate() {
            usernameField.onInput("alice");
            rememberMe.toggle();
            submitButton.onClick();
        }
    }

    // ── FACTORY PROVIDER: chooses the factory based on OS ───────────────────
    static UIFactory getFactory(String os) {
        return switch (os.toLowerCase()) {
            case "mac"     -> new MacUIFactory();
            case "windows" -> new WindowsUIFactory();
            default        -> throw new IllegalArgumentException("Unsupported OS: " + os);
        };
    }

    public static void main(String[] args) {
        System.out.println("=== ABSTRACT FACTORY PATTERN ===\n");

        String[] platforms = {"mac", "windows"};

        for (String os : platforms) {
            System.out.println("Platform: " + os.toUpperCase());
            UIFactory factory = getFactory(os);          // ← one swap changes everything
            LoginForm form = new LoginForm(factory);
            form.render();
            System.out.println("  Simulating interaction:");
            form.simulate();
            System.out.println();
        }

        System.out.println("── Key Takeaways ──");
        System.out.println("  • Switching platform = swap one factory, all components stay consistent");
        System.out.println("  • Client (LoginForm) never imports MacButton or WinButton");
        System.out.println("  • Add LinuxFactory → zero changes to LoginForm");
        System.out.println("  • Abstract Factory = Factory of Factories (creates families)");
        System.out.println("  • Factory Method   = one product; Abstract Factory = a suite");
    }
}
