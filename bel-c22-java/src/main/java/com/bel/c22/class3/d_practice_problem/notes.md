# Practice Problem: Student Report Card System

## Problem Statement
Design a `StudentReportCard` class that stores a student's information and grades securely
using encapsulation.

### Requirements:
1. The class should have the following **private** fields:
   - `name` (String) - student's name
   - `rollNumber` (int) - student's roll number
   - `mathScore` (double) - marks in Math (0-100)
   - `scienceScore` (double) - marks in Science (0-100)
   - `englishScore` (double) - marks in English (0-100)

2. Provide a **constructor** that takes `name` and `rollNumber`.

3. Provide **public getters** for all fields.

4. Provide **public setters** for the three scores with **validation**:
   - Score must be between 0 and 100 (inclusive).
   - If an invalid score is passed, print an error message and do NOT update the score.

5. Write a **public method** `getPercentage()` that returns the average of the three scores.

6. Write a **public method** `getGrade()` that returns:
   - "A" if percentage >= 80
   - "B" if percentage >= 60
   - "C" if percentage >= 40
   - "F" if percentage < 40

7. Write a **public method** `printReportCard()` that prints all details in this format:
   ```
   === Report Card ===
   Name: Rahul
   Roll Number: 101
   Math: 85.0
   Science: 72.0
   English: 90.0
   Percentage: 82.33%
   Grade: A
   ===================
   ```

### Test your class:
Write a `Main` class that:
1. Creates a student with name "Rahul" and roll number 101.
2. Sets math=85, science=72, english=90 and prints the report card.
3. Tries to set an invalid score (e.g., math = -10) and verifies it gets rejected.
4. Creates another student "Priya" with roll number 102, sets scores, and prints her report card.

## Solution
See the `solution/` folder after you have attempted the problem.
