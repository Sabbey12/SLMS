/**
 * File: StudentManager.java
 * Author: Team SLMS (Group 4)
 * Description: Main program to manage student profiles.
 * Features: Add, Search, Edit, Delete, View All students.
 */

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class: StudentManager
 *
 * This class acts as the controller for the Student attributes in Student Learning Management System (SLMS).
 *
 * It manages:
 * - Student data (add, edit, delete, search, view)
 * - Course–Student relationships (enrollment system)
 *
 * It also includes:
 * - Input validation
 * - Error handling for invalid operations
 *
 * Data Structures Used:
 * - ArrayList<Course> → stores all courses
 * - boolean[][] enrollment → stores relationship between students and courses
 */
public class StudentManager {

    // ArrayList to store all students. Dynamic insertion and deletion possible.
    private ArrayList<Student> students = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);

    /**
     * Method: addStudent()
     *
     * Adds a new student to the system.
     *
     * Steps:
     * - Get student details from user
     * - Check for duplicate student ID
     * - Validate input fields
     * - Store Student object in ArrayList
     */
    public void addStudent() {
        // Ask for all inputs first
        System.out.print("Enter Student ID: ");
        String id = sc.nextLine();

        // Checks if student id already exists
        if (findStudentIndex(id) != -1) {
            System.out.println("Error: Student ID already exists!");
            return;
        }

        System.out.print("Enter First Name: ");
        String fname = sc.nextLine();

        System.out.print("Enter Last Name: ");
        String lname = sc.nextLine();

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        System.out.print("Enter Phone: ");
        String phone = sc.nextLine();

        // Validate all inputs at once
        if (id.trim().isEmpty() || fname.trim().isEmpty() || lname.trim().isEmpty()
                || email.trim().isEmpty() || phone.trim().isEmpty()) {
            System.out.println("All fields must be filled!");
        } else {
            // Only add when all fields are valid
            students.add(new Student(id, fname, lname, email, phone));
            System.out.println("Student added successfully!");
        }
    }

    /**
     * Method: searchStudent()
     *
     * Searches a student using student ID.
     *
     * Features:
     * - Updates search history cache
     * - Stores last searched student
     * - Displays student details if found
     */
    public String searchStudent() {
        System.out.print("Enter Student ID: ");
        String id = sc.nextLine();

        int index = findStudentIndex(id); // search for student

        if (index != -1) {
            students.get(index).displayStudent(); // display student details
        } else {
            System.out.println("Student not found."); // student not found
        }

        return id;
    }

    /**
     * Method: editStudent()
     *
     * Updates student information.
     *
     * Allows user to modify:
     * - First name
     * - Last name
     * - Email
     * - Phone number
     *
     * Student ID cannot be changed.
     */
    public void editStudent() {
        System.out.print("Enter Student ID: ");
        String id = sc.nextLine();

        // Prevent blank ID input
        if (id.trim().isEmpty()) {
            System.out.println("Student ID cannot be empty!");
            return;
        }

        int index = findStudentIndex(id);

        //if id entered exists in database, proceed to update student details
        if (index != -1) {
            Student s = students.get(index);

            String fname;
            while (true) {
                System.out.print("New First Name: ");
                fname = sc.nextLine();
                if (!fname.trim().isEmpty()) break;
                System.out.println("First name cannot be empty!");
            }
            s.setFirstName(fname); // prevent blank input

            String lname;
            while (true) {
                System.out.print("New Last Name: ");
                lname = sc.nextLine();
                if (!lname.trim().isEmpty()) break;
                System.out.println("Last name cannot be empty!");
            }
            s.setLastName(lname); // prevent blank input

            String email;
            while (true) {
                System.out.print("New Email: ");
                email = sc.nextLine();
                if (!email.trim().isEmpty()) break;
                System.out.println("Email cannot be empty!");
            }
            s.setEmail(email); // prevent blank input

            String phone;
            while (true) {
                System.out.print("New Phone: ");
                phone = sc.nextLine();
                if (!phone.trim().isEmpty()) break;
                System.out.println("Phone cannot be empty!");
            }
            s.setPhone(phone); // prevent blank input

            System.out.println("Student updated successfully!");
            s.displayStudent();
            
        } else {
            //if id entered does not exists, display the error message
            System.out.println("Student not found.");
        }
    }

    /**
     * Method: deleteStudent()
     *
     * Deletes a student from the system.
     *
     * Steps:
     * - Find student by ID
     * - Confirm deletion
     * - Remove student from ArrayList
     * - Remove all course relationships
     * - Shift enrollment matrix to maintain consistency
     */
    public void deleteStudent() {
        System.out.print("Enter Student ID: ");
        String id = sc.nextLine();

        int index = findStudentIndex(id);

        if (index != -1) {
            // Clear relationships first
            students.remove(index);
            System.out.println("Student deleted successfully!");
        } else {
            System.out.println("Student not found.");
        }
    }

    /**
     * Method: viewAllStudents()
     *
     * Displays all students in the system.
     *
     * If no students exist, show message.
     */
    public void viewAllStudents() {
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
     *
     * Finds a student index using student ID.
     *
     * Returns:
     * - Index if found
     * - -1 if not found
     *
     * Used for all student-related operations.
     */
    public int findStudentIndex(String id) {
        for (int i = 0; i < students.size(); i++) {
            // equalsIgnoreCase: allows case-insensitive comparison
            if (students.get(i).getStudentID().equalsIgnoreCase(id)) {
                return i; // student found, display student's information
            }
        }
        return -1; // -1 indicates student not found
    }

    public ArrayList<Student> getStudents() {
        return students;
    }
}