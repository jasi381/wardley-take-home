# BEL C22 Java Course Repository

This is a Maven-ized copy of the **BEL C22 code repository** — a Java
training/course repo. It was rebuilt from a complete, authoritative
source zip provided directly by the user (179 files, 64 topics),
replacing an earlier partial import from Google Drive that only had
9 topics (8 of which had no `.java` source at all — see
[Superseded fabricated placeholders](#superseded-fabricated-placeholders)
below).

The original repo tree was organized as `classN/<topic>/...` (plus a
handful of design-pattern topics with no class number at all — see
below). That layout has been mirrored into a standard Maven
`src/main/java` package structure under `com.bel.c22`.

## Building

```
mvn -q -f pom.xml compile
```

Java 17, no external dependencies (nothing in the source tree required
any — every non-JDK import is `java.*`/`javax.*`).

`.md` notes files (`notes.md`, `NOTES.md`, `PROBLEM_STATEMENT.md`,
`SOLUTION_GUIDE.md`, etc.) live alongside their package's `.java`
files (Maven ignores non-`.java` sources under `src/main/java`),
keeping each topic's documentation next to its code — same convention
as the earlier import.

## Topics

### class1 — Java Fundamentals

| Package | Topic |
|---|---|
| `class1.s1_introduction` | Introduction to Java (`HelloWorld`) |
| `class1.s2_datatypes` | Data Types (`PrimitiveTypes`, `NonPrimitiveTypes`) |
| `class1.s3_variables_scope` | Variables and Scope (`VariableScopeDemo`) |
| `class1.s4_typecasting` | Type Casting (`TypeCastingDemo`) |
| `class1.s5_operators` | Operators (`OperatorsDemo`) |
| `class1.s6_control_flow` | Control Flow — if/else/switch (`IfElseDemo`, `SwitchDemo`) |
| `class1.s7_loops` | Loops and Jump Statements (`LoopsDemo`, `JumpStatementsDemo`) |
| `class1.s8_methods` | Methods and Constructors (`MethodsDemo`, `ConstructorDemo`, `Car`) |
| `class1.s9_oop_intro` | Introduction to OOP (`OopIntroDemo`) |
| `class1.s10_exercise` | Exercise — Airtribe Learner Manager (`LearnerManagementSystem`) |

### class2 — The JVM

| Package | Topic |
|---|---|
| `class2.s1_jdk_jre_jvm` | JDK, JRE, and JVM (`JdkJreJvmDemo`) |
| `class2.s2_compilation_bytecode` | Compilation & Bytecode (`CompilationDemo`) |
| `class2.s3_jvm_architecture` | JVM Architecture (`JvmArchitectureDemo`) |
| `class2.s4_class_loading` | Class Loading (`ClassLoadingDemo`) |
| `class2.s5_linking_phase` | Linking Phase (`LinkingPhaseDemo`) |
| `class2.s6_initialization` | Initialization Phase (`InitializationDemo`) |
| `class2.s7_runtime_memory` | Runtime Memory Areas (`RuntimeMemoryDemo`) |
| `class2.s8_jit_vs_interpreter` | JIT Compiler vs Interpreter (`JitVsInterpreterDemo`) |
| `class2.s9_exception_handling` | Exception Handling (`ExceptionHandlingDemo`, `CheckedExceptionDemo`) |
| `class2.s10_jvm_tools` | JVM Visualization Tools (`JvmToolsDemo`) |
| `class2.s11_exercise` | Exercise — Learner Management System (`LearnerManagementSystem`) |

### class3 — Packages, Encapsulation, Access Modifiers

| Package | Topic |
|---|---|
| `class3.a_packages` (+ `.school.math`, `.school.science`) | Packages — two `Teacher` classes disambiguated by package (`PackageDemo`, `Test`) |
| `class3.b_encapsulation` | Encapsulation (`BankAccount`, `BankAccountDemo`) |
| `class3.c_access_modifiers` (+ `.differentpackage`, `.samepackage`) | Access Modifiers across packages (`DifferentPackageDemo`, `Person`, `SamePackageDemo`) |
| `class3.d_practice_problem.solution` | Practice Problem — Student Report Card System (`Main`, `StudentReportCard`) |

### class4 — Inheritance & Polymorphism

| Package | Topic |
|---|---|
| `class4.a_inheritance` | Inheritance (`InheritanceDemo`) |
| `class4.b_protected_access` (+ `.parent`, `.child`) | Protected access across packages via inheritance (`Animal`, `SamePackageTest`, `Dog`) |
| `class4.c_this_and_super` | `this` and `super` keywords (`ThisSuperDemo`) |
| `class4.d_polymorphism` | Polymorphism (`PolymorphismDemo`, `OverloadingDemo`, `Sample`) |
| `class4.e_practice_problem.solution` | Practice Problem — Employee Payroll System (`Main`, `Employee`, `FullTimeEmployee`, `PartTimeEmployee`) |

### class5 — Abstraction & Object Relationships

| Package | Topic |
|---|---|
| `class5.a_abstraction` | Abstraction (`AbstractionDemo`) |
| `class5.b_interfaces` | Interfaces (`InterfaceDemo`) |
| `class5.c_association` | Association (`AssociationDemo`) |
| `class5.d_aggregation` | Aggregation (`AggregationDemo`) |
| `class5.e_composition` | Composition (`CompositionDemo`) |
| `class5.f_practice_problem.solution` | Practice Problem — Airtribe LMS (`Main`, `Cohort`, `Course`, `Instructor`, `Learner`, `JavaLearner`, `NodeJSLearner`) |

### class6 — SOLID & Design Principles

| Package | Topic |
|---|---|
| `class6.a_intro` | What are Design Principles? (`DesignPrinciplesIntro`) |
| `class6.b_dry_kiss_yagni` | DRY / KISS / YAGNI (`DryDemo`, `KissDemo`, `YagniDemo`) |
| `class6.c_srp` | Single Responsibility Principle (`SrpDemo`) |
| `class6.d_ocp` | Open/Closed Principle (`OcpDemo`) |
| `class6.e_lsp` | Liskov Substitution Principle (`LspDemo`) |
| `class6.f_isp` | Interface Segregation Principle (`IspDemo`) |
| `class6.g_dip` | Dependency Inversion Principle (`DipDemo`) |
| `class6.h_composition_over_inheritance` | Composition over Inheritance (`CompositionOverInheritanceDemo`) |

### class8 — Composition/Inheritance Deep Dive

| Package | Topic |
|---|---|
| `class8.a_composition_over_inheritance` | `CompositionSolutionDemo`, `InheritanceProblemDemo`, `WhenInheritanceWinsDemo` |
| `class8.b_association_aggregation_composition` | `AssociationDemo`, `AggregationDemo`, `CompositionDemo` |
| `class8.c_practice_problem` (+ `.solution`) | Online Food Delivery System (`ProblemStatement`, `SolutionGuide`, and a full order/payment domain model under `solution`) |

### class9 — Creational Design Patterns

| Package | Topic |
|---|---|
| `class9.a_singleton` | Singleton Pattern (`SingletonDemo`) |
| `class9.b_builder` | Builder Pattern (`BuilderDemo`) |
| `class9.c_factory` | Factory Method Pattern (`FactoryDemo`) |
| `class9.d_abstract_factory` | Abstract Factory Pattern (`AbstractFactoryDemo`) |
| `class9.e_practice_problem` (+ `.solution`) | Practice Problem — Creational Patterns (`ProblemStatement`, `Solution`) |
| `class9.warmup_session` | Intro to Design Patterns warm-up (`Solution`) |

### Unclassed pattern topics (no class number in the source repo)

These five behavioral/structural design-pattern topics sit at the top
level of the source repository with no `classN` wrapper, so they are
imported the same way here — directly under `com.bel.c22.<topic>`
rather than under any `classN` package:

| Package | Topic |
|---|---|
| `a_observer` | Observer Pattern (`ObserverDemo`) |
| `b_strategy` | Strategy Pattern (`StrategyDemo`) |
| `c_chain_of_responsibility` | Chain of Responsibility Pattern (`ChainOfResponsibilityDemo`) |
| `d_decorator` | Decorator Pattern (`DecoratorDemo`) |
| `f_state` | State Pattern (`StateDemo`) |

## What was excluded, and why

- **Duplicate top-level folders skipped.** The source zip also contained
  top-level `a_abstraction/`, `a_packages/`, `b_encapsulation/`, and
  `c_access_modifiers/` folders that are byte-for-byte identical
  (verified with `diff -rq`) to `class5/a_abstraction`,
  `class3/a_packages`, `class3/b_encapsulation`, and
  `class3/c_access_modifiers` respectively. This is a Google Drive
  multi-parent artifact (the same folder shared/linked into two
  locations in Drive), so these four top-level copies were not
  re-imported — only their `classN` counterparts are present here.
- **`class3/.claude/` and `class3/.mcp.json`** — Claude Code tooling
  configuration accidentally left in the course folder, not course
  material. Excluded.
- **`class2/s2_compilation_bytecode/CompilationDemo.class`** — a
  compiled binary artifact sitting next to its own source. Only the
  `.java` source was imported; the `.class` file was not.

## Package/import wiring

Every `.java` file's `package` declaration (and any imports/fully
qualified references to other topics within this repo) was rewritten
to match its new Maven location under `com.bel.c22`; no class was
renamed and no logic was changed. The two-level subpackage demos
(`class3.a_packages.school.{math,science}`, `class3.c_access_modifiers.
{differentpackage,samepackage}`, `class4.b_protected_access.
{parent,child}`) were preserved as real subpackages, matching how the
source repo itself demonstrates package-based visibility rules.

One genuine gap was found in the source itself:
`d_decorator/DecoratorDemo.java` had a stray `import class12.Milk;`
that referenced a package/class that does not exist anywhere in the
source zip and was never used anywhere in the file body (the file
defines its own unrelated `MilkDecorator` nested class). This dangling
import was removed so the file compiles; no other change was made to
that file.

## Superseded fabricated placeholders

An earlier import of this repository was done from a partial Google
Drive copy that only contained 9 topics, 8 of which had no `.java`
source on Drive at all (only `notes.md`). At the user's request,
placeholder `*Demo.java` files were written from scratch to match
those notes, clearly documented as fabricated. That entire caveat is
now obsolete: this rebuild replaces the partial Drive copy with the
real, complete course repository (64 topics, 115 real `.java` files)
provided directly by the user as a zip, so every file in this project
is now genuine source material — nothing here is fabricated.
