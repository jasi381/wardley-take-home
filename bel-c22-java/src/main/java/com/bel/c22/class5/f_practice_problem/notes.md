# Practice Problem: Airtribe Learner Management System (LMS)

## Problem Statement
You need to develop a Learner Management System (LMS) for Airtribe in Java,
emphasizing Object-Oriented Programming (OOP) principles such as classes,
encapsulation, abstraction, and inheritance.

LMS should cater to the following types of learners:
- **Node.js Learners**
- **Java Learners**

Airtribe also supports **Offline** and **Online** courses — offline courses are taught
physically and online courses are taught over online calls.

## Requirements

### 1. Course Management
- Create a course with attributes specifying:
  - Type: **online** or **offline**
  - Course specialization: **Node.js** or **Java**

### 2. Cohort Management
- Create a cohort with:
  - Learners enrolled in the cohort
  - An instructor assigned to the cohort

### 3. Instructor Management
- Create an instructor with a unique identifier and name.

### 4. Learner Management
- Create a Node.js learner or Java learner and assign them to the corresponding cohort.
- Each learner should have:
  - A unique identifier and name
  - Experience points (XP) specific to their course
  - Ability to calculate and display their XP

### 5. Functionality
- Calculate the **average XP** of all learners in a cohort.

### 6. Display Details
- Display details of courses, cohorts, instructors, and learners.

## OOP Concepts to Demonstrate
- **Abstraction**: Use an abstract `Learner` class with abstract method for XP calculation
- **Interfaces**: Consider using interfaces where appropriate
- **Encapsulation**: Private fields with getters/setters
- **Inheritance**: JavaLearner and NodeJSLearner extend Learner
- **Composition**: A Cohort OWNS its list of learners (created within the system)
- **Aggregation**: A Cohort HAS an Instructor (instructor exists independently)
- **Association**: Course and Cohort are associated

## Solution
See the `solution/` folder after you have attempted the problem.
