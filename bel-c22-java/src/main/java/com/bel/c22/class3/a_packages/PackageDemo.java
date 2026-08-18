package com.bel.c22.class3.a_packages;

// Importing classes from sub-packages
//import com.bel.c22.class3.a_packages.school.math.Teacher;
//import com.bel.c22.class3.a_packages.school.science.Teacher;

// Since both classes are named "Teacher", we use fully qualified name for the second one

//import com.bel.c22.class3.a_packages.school.math.Teacher;

import com.bel.c22.class3.a_packages.school.math.Teacher;

//com.bel.c22.class3.a_packages.PackageDemo
public class PackageDemo {
    public static void main(String[] args) {
        // Using the imported math.Teacher directly
        Teacher mathTeacher = new Teacher("Mr. Sharma");
        mathTeacher.teach();

        // Using fully qualified name for science.Teacher (to avoid name conflict)
        com.bel.c22.class3.a_packages.school.science.Teacher scienceTeacher =
                new com.bel.c22.class3.a_packages.school.science.Teacher("Ms. Gupta");
        scienceTeacher.teach();

        // Key takeaway: Two classes with the SAME name "Teacher" can exist
        // in different packages without any conflict!
    }
}
