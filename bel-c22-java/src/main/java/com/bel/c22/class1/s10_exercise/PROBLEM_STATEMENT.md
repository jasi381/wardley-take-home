# Exercise — Airtribe Learner Manager

## Session #1 | Introduction to Java Basics

---

## Background
You are building a simple **Learner Management System (LMS)** for Airtribe.
The system lets an instructor manage learners from a console menu.

---

## Requirements

### Data to store per learner
Each learner has:
- **Name** (String)
- **Age** (int)
- **XP** (int — experience points / grade proxy)

### Menu Options
```
===== Airtribe Learner Manager =====
1. Add Learner
2. Display All Learners
3. Calculate Average XP
4. Exit
Select: _
```

---

## Functional Requirements

### 1. Add Learner
- Accept name, age, and XP from user input
- Validate age: must be between **18 and 100**
  - If invalid → print an error message and do NOT add the learner

### 2. Display All Learners
- Print all learner details in a formatted list
- If no learners added yet → print a friendly message

### 3. Calculate Average XP
- Compute and print the average XP of all learners
- If no learners → print a friendly message

### 4. Exit
- Print a goodbye message and stop the program

---

## Constraints
- Use a **single class** with a `main` method
- Use **arrays** to store learners (fixed max size of 10)
- Use `Scanner` for user input
- Use `switch` for menu selection
- Use `if-else` for age validation
- Use a `while` loop to keep the menu running until user exits

---

## Expected Output (sample)
```
===== Airtribe Learner Manager =====
1. Add Learner
2. Display All Learners
3. Calculate Average XP
4. Exit
Select: 1

Enter name: Alice
Enter age: 22
Enter XP: 120
✓ Learner added successfully!

Select: 1
Enter name: Bob
Enter age: 15
Enter XP: 80
✗ Invalid age. Age must be between 18 and 100.

Select: 2
--- Learner List ---
1. Alice | Age: 22 | XP: 120

Select: 3
Average XP: 120.00

Select: 4
Goodbye!
```

---

## Hints
- Declare parallel arrays: `String[] names`, `int[] ages`, `int[] xpValues`
- Keep a `count` variable to track how many learners have been added
- Use `scanner.nextLine()` for strings and `scanner.nextInt()` for numbers
- To calculate average: `sum / count` (cast to double for decimal result)

---

## Compile & Run
```bash
# Compile
javac LearnerManagementSystem.java

# Run
java LearnerManagementSystem
```

> The solution is in `LearnerManagementSystem.java` in this same package.
> Try solving it yourself first before looking at the solution!
