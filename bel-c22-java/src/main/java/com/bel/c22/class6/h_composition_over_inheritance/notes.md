# Composition over Inheritance

## Definition
> "Prefer **has-a** relationships (composition) over **is-a** relationships (inheritance) where appropriate."

- **Inheritance**: A mechanism to acquire behavior from a base class (IS-A)
  - Example: A `Car` inherits from a `Vehicle` class
- **Composition**: A design principle where behavior is achieved by containing instances of other classes (HAS-A)
  - Example: A `Car` has an `Engine` object

## Why Prefer Composition?

| Benefit | Explanation |
|---------|-------------|
| **Flexibility** | Classes can change behavior dynamically at runtime |
| **Reduced Coupling** | Avoids tight coupling between parent and child |
| **Reusability** | Components can be reused in different contexts |
| **Improved Testability** | Smaller, independent components are easier to test |
| **Avoids Fragile Base Class Problem** | Changes in base class won't ripple through hierarchy |

## Before (Inheritance - Rigid)
```java
class Animal {
    private int age;
    private String name;
}

class Lion extends Animal {
    private String color;    // Duplicated across Lion, Tiger, etc.
    private String habitat;
    private String diet;
}

class Tiger extends Animal {
    private String color;    // Same fields repeated!
    private String habitat;
    private String diet;
}
```

## After (Composition - Flexible)
```java
class AnimalProperties {
    private int age;
    private String name;
}

class UniqueAnimalProperties {
    private String color;
    private String habitat;
    private String diet;
}

class Lion {
    private AnimalProperties properties;        // HAS-A
    private UniqueAnimalProperties unique;       // HAS-A
}
```

## When to Use Which?

| Use Inheritance When... | Use Composition When... |
|------------------------|------------------------|
| True IS-A relationship exists | Behavior needs to change at runtime |
| Child is genuinely a specialized parent | You want to reuse code across unrelated classes |
| Hierarchy is shallow (1-2 levels) | Multiple behaviors need to be combined |
| All parent methods make sense for child | Inheritance hierarchy is getting deep or fragile |

## The Duck Example (Strategy Pattern)
Instead of a deep duck hierarchy:
```
Duck → MallardDuck, RubberDuck, DecoyDuck, RobotDuck...
```

Use composition:
```java
class Duck {
    private SwimBehavior swim;    // HAS-A
    private SoundBehavior sound;  // HAS-A
    private FlyBehavior fly;      // HAS-A
}
// Mix and match behaviors for ANY type of duck!
```

## Real-World Analogy
> A car IS NOT an engine. A car HAS an engine. You can swap the engine (electric vs petrol) without redesigning the entire car. That's composition!
