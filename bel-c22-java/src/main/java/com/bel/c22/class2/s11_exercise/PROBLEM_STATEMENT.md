# Exercise — Learner Management System (Session 2)

## Problem Statement
Build a **Learner Management System (LMS)** for Airtribe that manages learner records.

## Requirements

### Core Features (Menu-Driven Interface)
1. **Add new learners** — name, age, XP score
2. **Display all learner details**
3. **Calculate and display the average XP** of all learners
4. **Exit** the program

### Validation Rules
- Use `if-else` to validate learner age (must be between 18 and 100)
- Use a `switch` statement to handle menu selection

### Loops
- Keep the menu active until the user chooses to exit
- Use loops to iterate through learners for display and average calculation

### Exception Handling (Session 2 Addition)
- Handle `InputMismatchException` for invalid menu/number input
- Handle `ArrayIndexOutOfBoundsException` if max capacity reached
- Use try-catch to prevent program crashes on bad input

### Compilation & Execution
Write the commands used to compile and run the program:
```bash
javac LearnerManagementSystem.java
java LearnerManagementSystem
```

## Sample Output
```
=== Airtribe Learner Management System ===
1. Add Learner
2. Display All Learners
3. Calculate Average XP
4. Exit
Choose an option: 1
Enter learner name: Alice
Enter learner age: 22
Enter learner XP: 85
Learner added successfully!
```

## Reference
GitHub: https://github.com/airtribe-projects/bel-c8-java
