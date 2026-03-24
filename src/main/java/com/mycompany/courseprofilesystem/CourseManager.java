/**
 * File: CourseManager.java
 * Author: Team SLMS (Group 3)
 * Description: Main program to manage course profiles.
 * Features: Add, Search, Edit, Delete, View All courses.
 */

package com.mycompany.courseprofilesystem;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class: CourseManager
 * 
 * This class manages both Course Profile and Student Profile modules
 * in the SLMS (Student Learning Management System).
 * 
 * Users can:
 * - Add, search, edit, delete, and view courses
 * - Add, search, edit, delete, and view students
 * 
 * ArrayLists are used for dynamic storage of Course and Student objects.
 * Scanner is used for user input.
 */
class CourseManager {

    // ArrayList to store all courses. ArrayList allows dynamic resizing.
    private ArrayList<Course> courses = new ArrayList<>();

    // ArrayList to store all students. Dynamic insertion and deletion possible.
    private ArrayList<Student> students = new ArrayList<>();

    // Scanner to get user input
    private Scanner sc = new Scanner(System.in);

    /**
     * Main method: program entry point
     */
    public static void main(String[] args) {
        CourseManager manager = new CourseManager();
        manager.run();  // Start the interactive menu
    }

    /**
     * Method: run()
     * 
     * This is the main menu loop. It uses a do-while loop.
     * 
     * Why use do-while?
     * - Ensures menu displays at least once before checking exit.
     * - Loop continues until user chooses 0 (Exit).
     */
    public void run() {
        int choice;
        do {
            // Display menu options
            System.out.println("\n--- SLMS SYSTEM ---");
            System.out.println("1. Add Course");
            System.out.println("2. Search Course");
            System.out.println("3. Edit Course");
            System.out.println("4. Delete Course");
            System.out.println("5. View All Courses");
            System.out.println("6. Add Student");
            System.out.println("7. Search Student");
            System.out.println("8. Edit Student");
            System.out.println("9. Delete Student");
            System.out.println("10. View All Students");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            /**
             * Input validation using while loop.
             * - Checks if the user entered an integer.
             * - If input is invalid (like letters), the user need to prompt again.
             * - sc.next() clears the invalid input.
             */
            while (!sc.hasNextInt()) {
                System.out.print("Invalid input! Enter a number: ");
                sc.next();
            }
            choice = sc.nextInt();
            sc.nextLine();  // consume newline

            // Switch case to execute chosen operation
            switch (choice) {
                case 1: addCourse(); break;
                case 2: searchCourse(); break;
                case 3: editCourse(); break;
                case 4: deleteCourse(); break;
                case 5: viewAllCourses(); break;
                case 6: addStudent(); break;
                case 7: searchStudent(); break;
                case 8: editStudent(); break;
                case 9: deleteStudent(); break;
                case 10: viewAllStudents(); break;
                case 0: System.out.println("Exiting program."); break;
                default: System.out.println("Invalid choice!");
            }

        } while (choice != 0); // Loop until user enters 0
    }

    /**
     * Method: addCourse()
     * Adds a new course after valid inputs.
     */
    private void addCourse() {
        System.out.print("Enter Course Name: ");
        String name = sc.nextLine();

        String code;

        // Ensure unique course code
        while (true) {
            System.out.print("Enter Course Code: ");
            code = sc.nextLine();

            // findCourseIndex() searches if code already exists
            if (findCourseIndex(code) != -1) {
                System.out.println("Course code already exists! Enter another unique code.");
            } else break; // Exit loop when course code is unique
        }

        int credit;
        // Validate credit hour input: integer and > 0
        while (true) {
            System.out.print("Enter Credit Hour: ");
            if (sc.hasNextInt()) {
                credit = sc.nextInt();
                sc.nextLine();
                if (credit > 0) break;
                else System.out.println("Credit hour must be positive!");
            } else {
                System.out.println("Invalid input! Enter a number.");
                sc.next();
            }
        }

        System.out.print("Enter Course Summary: ");
        String summary = sc.nextLine();

        System.out.print("Enter MS Teams Link: ");
        String link = sc.nextLine();

        // Validate all inputs at once
        if (name.isBlank() || code.isBlank() || credit.isBlank() || summary.isBlank() || link.isBlank()) {
            System.out.println("Course not added! All fields must be filled.");
        } else {
            // Only add if all fields are valid
            courses.add(new Course(name, code, credit, summary, link));
            System.out.println("Course added successfully!");
        }
    }

    /**
     * Method: searchCourse()
     * Search for a course by course code.
     */
    private void searchCourse() {
        System.out.print("Enter Course Code to search: ");
        String code = sc.nextLine();

        int index = findCourseIndex(code);

        if (index != -1) {
            System.out.println("Course found:");
            courses.get(index).displayCourse(); // Display course info
        } else {
            System.out.println("Course not found.");
        }
    }

    /**
     * Method: editCourse()
     * Edit existing course details (except code).
     */
    private void editCourse() {
        System.out.print("Enter Course Code to edit: ");
        String code = sc.nextLine();
        int index = findCourseIndex(code);

        if (index != -1) {
            Course c = courses.get(index);
            System.out.println("Editing Course: " + c.getCourseCode());

            System.out.print("New Course Name: ");
            c.setCourseName(sc.nextLine());

            int credit;
            // Validate input to new credit hour: integer and > 0
            while (true) {
                System.out.print("New Credit Hour: ");
                if (sc.hasNextInt()) {
                    credit = sc.nextInt();
                    sc.nextLine();
                    if (credit > 0) break;
                    else System.out.println("Credit hour must be positive!");
                } else {
                    System.out.println("Invalid input! Enter a number.");
                    sc.next();
                }
            }
            c.setCreditHour(credit);

            System.out.print("New Summary: ");
            c.setSummary(sc.nextLine());

            System.out.print("New MS Teams Link: ");
            c.setMsTeamsLink(sc.nextLine());

            System.out.println("Course updated successfully!");
            c.displayCourse();
        } else {
            System.out.println("Course not found.");
        }
    }

    /**
     * Method: deleteCourse()
     * Deletes a course after confirmation by the user.
     */
    private void deleteCourse() {
        System.out.print("Enter Course Code to delete: ");
        String code = sc.nextLine();
        int index = findCourseIndex(code);

        //check if the course code entered exists or not.
        if (index != -1) {
            Course c = courses.get(index);
            System.out.println("Course found:");
            c.displayCourse();

            //confirmation to user to delete, must be (y/Y) to delete
            System.out.print("Confirm deletion? (Y/N): ");
            String confirm = sc.nextLine();
            if (confirm.equalsIgnoreCase("Y")) {
                courses.remove(index); // Remove from list
                System.out.println("Course deleted successfully!");
            } else {
                System.out.println("Deletion cancelled.");
            }
        } else {
            System.out.println("Course not found.");
        }
    }

    /**
     * Method: viewAllCourses()
     * Displays all courses in the system.
     */
    private void viewAllCourses() {
        /**check for all courses created and courses available in the database.
        * if empty/none available, display the following message
        * if courses exists, display all courses created.
        */
        if (courses.isEmpty()) {
            System.out.println("No courses available.");
        } else {
            System.out.println("\n--- All Courses ---");
            for (Course c : courses) {
                c.displayCourse();
            }
        }
    }

    /**
     * Method: findCourseIndex()
     * Searches course list for a code. Returns index or -1 if not found.
     */
    private int findCourseIndex(String code) {
        for (int i = 0; i < courses.size(); i++) {
            // equalsIgnoreCase: ignores uppercase/lowercase differences
            if (courses.get(i).getCourseCode().equalsIgnoreCase(code)) {
                return i; // Course found
            }
        }
        // -1 indicates course not found
        return -1;
    }

        // ---------------- STUDENT METHODS ---------------- //
    
     /**
     * Method: addStudent()
     * Description: Adds a new student to the system
     * - Input all details from user
     * - Stores Student object in ArrayList
     */
    private void addStudent() {
    // Ask for all inputs first
    System.out.print("Enter Student ID: ");
    String id = sc.nextLine();

    System.out.print("Enter First Name: ");
    String fname = sc.nextLine();

    System.out.print("Enter Last Name: ");
    String lname = sc.nextLine();

    System.out.print("Enter Email: ");
    String email = sc.nextLine();

    System.out.print("Enter Phone: ");
    String phone = sc.nextLine();

    // Validate all inputs at once
    if (id.isBlank() || fname.isBlank() || lname.isBlank() || email.isBlank() || phone.isBlank()) {
        System.out.println("Student not added! All fields must be filled.");
    } else {
        // Only add if all fields are valid
        students.add(new Student(id, fname, lname, email, phone));
        System.out.println("Student added successfully!");
    }
}

     /**
     * Method: searchStudent()
     * Description: Search student by ID
     * - Uses findStudentIndex()
     * - Display the student's information if found
     */
    private void searchStudent() {
        System.out.print("Enter Student ID to search: ");
        String id = sc.nextLine();

        int index = findStudentIndex(id); // search for student

        if (index != -1) {
            System.out.println("Student found:");
            students.get(index).displayStudent(); // display student details
        } else {
            System.out.println("Student not found."); // not found
        }
    }
  /**
     * Method: editStudent()
     * - Updates student information by ID
     * - Prompts user to enter new data
     * - Ensures correct student is updated using index
     */
    private void editStudent() {
        System.out.print("Enter Student ID to edit: ");
        String id = sc.nextLine();

        int index = findStudentIndex(id);

        //if id entered exists in database, proceed to update student details
        if (index != -1) {
            Student s = students.get(index);

            System.out.print("New First Name: ");
            s.setFirstName(sc.nextLine());

            System.out.print("New Last Name: ");
            s.setLastName(sc.nextLine());

            System.out.print("New Email: ");
            s.setEmail(sc.nextLine());

            System.out.print("New Phone: ");
            s.setPhone(sc.nextLine());

            System.out.println("Student updated successfully.");
            s.displayStudent();
        } else {
            //if id entered does not exists, display the error message
            System.out.println("Student not found.");
        }
    }

    /**
     * Method: deleteStudent()
     * - Find student index first
     * - Confirm deletion
     * - Remove from ArrayList
     * - Display all students after deletion
     */
    private void deleteStudent() {
        System.out.print("Enter Student ID to delete: ");
        String id = sc.nextLine();

        int index = findStudentIndex(id);

        //if id entered exists in database, proceed to update student details
        if (index != -1) {
            Student s = students.get(index);
            System.out.println("Student found:");
            s.displayStudent(); // display student's information before deletion

            //ask confirmation from user to delete, must be (y/Y)
            System.out.print("Confirm deletion? (Y/N): ");
            String confirm = sc.nextLine();

            if (confirm.equalsIgnoreCase("Y")) {
                students.remove(index); // delete student
                System.out.println("Student deleted successfully!");
                viewAllStudents(); // Show updated list
            } else {
                System.out.println("Deletion cancelled.");
            }
        } else {
            //if id entered does not exists, display the error message
            System.out.println("Student not found.");
        }
    }

     /**
     * Method: viewAllStudents()
     * - Loops through ArrayList and display each student
     * - Checks if list is empty first
     */
    private void viewAllStudents() {
        /**check for all students available in the database.
        * if empty/none available, display the following message
        * if students exists, display all students' information.
        */
        if (students.isEmpty()) {
            System.out.println("No students available.");
        } else {
            System.out.println("\n--- All Students ---");
            for (Student s : students) {
                s.displayStudent();
            }
        }
    }

    /**
     * Method: findStudentIndex()
     * - Returns index of student in ArrayList
     * - Linear search
     * - Returns -1 if not found
     * - equalsIgnoreCase() to ignore case sensitivity in ID
     */
    private int findStudentIndex(String id) {
        for (int i = 0; i < students.size(); i++) {
            // equalsIgnoreCase: allows case-insensitive comparison
            if (students.get(i).getStudentID().equalsIgnoreCase(id)) {
                return i; // student found, display student's information
            }
        }
        // -1 indicates student not found
        return -1;
    }
}
