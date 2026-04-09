/**
 * File: CourseManager.java
 * Author: Team SLMS (Group 4)
 * Description: Main program to manage course profiles.
 * Features: Add, Search, Edit, Delete, View All courses.
 */

package com.mycompany.courseprofilesystem;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class: CourseManager
 *
 * This class acts as the main controller for the Student Learning Management System (SLMS).
 *
 * It manages:
 * - Course data (add, edit, delete, search, view)
 * - Student data (add, edit, delete, search, view)
 * - Course–Student relationships (enrollment system)
 *
 * It also includes:
 * - Input validation
 * - Error handling for invalid operations
 * - Simple caching system for search history
 * - Auto-suggestion feature based on course data
 *
 * Data Structures Used:
 * - ArrayList<Course> → stores all courses
 * - ArrayList<Student> → stores all students
 * - boolean[][] enrollment → stores relationship between students and courses
 */


class CourseManager {
    // Stores last searched student ID (for history feature)
    private String lastSearchedStudent = "";
    // Stores last searched course code (for history feature)
    private String lastSearchedCourse = "";
    
    // ArrayList to store all courses. ArrayList allows dynamic resizing.
    private ArrayList<Course> courses = new ArrayList<>();

    // ArrayList to store all students. Dynamic insertion and deletion possible.
    private ArrayList<Student> students = new ArrayList<>();
    
    // Stores search history for API caching feature
    private ArrayList<String> searchCache = new ArrayList<>();
    
    /// Relationship matrix:
// enrollment[studentIndex][courseIndex] = true if student is enrolled
private boolean[][] enrollment = new boolean[100][100];

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
 * Main menu loop of the system.
 *
 * This method:
 * - Displays menu options
 * - Takes user input
 * - Calls corresponding functions using switch-case
 * - Repeats until user selects exit (0)
 *
 * It also includes input validation to prevent invalid menu selection.
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
             System.out.println("11. Assign Course to Student");
            System.out.println("12. View Student Courses");
            System.out.println("13. View Course Students");
            System.out.println("14. Suggest Last Search");
            System.out.println("15. Auto Suggest Search");
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
                case 11: assignCourseToStudent(); break;
                case 12: listCoursesByStudent(); break;
                case 13: listStudentsByCourse(); break;
                case 14: suggestLastSearch(); break;
                case 15:
                System.out.print("Enter keyword: ");
                String input = sc.nextLine();
                autoSuggest(input);
                break;
                case 0: System.out.println("Exiting program."); break;
                default: System.out.println("Invalid choice!");
            }

        } while (choice != 0); // Loop until user enters 0
    }

    /**
     * Method: addCourse()
    /**
 * Adds a new course into the system.
 *
 * Steps:
 * - Get course details from user
 * - Validate input (no empty fields)
 * - Ensure course code is unique
 * - Store Course object in ArrayList
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
        if (name.isBlank() || code.isBlank() || summary.isBlank() || link.isBlank()) {
            System.out.println("Course not added! All fields must be filled.");
        } else {
            // Only add if all fields are valid
            courses.add(new Course(name, code, credit, summary, link));
            System.out.println("Course added successfully!");
        }
    }

    /**
     * Method: searchCourse()
    /**
 * Searches a course using course code.
 *
 * Features:
 * - Stores search history (cache)
 * - Updates last searched course
 * - Displays course details if found
 * - Shows error if not found
 */
    private void searchCourse() {
        System.out.print("Enter Course Code to search: ");
        String code = sc.nextLine();
        lastSearchedCourse = code;
        searchCache.add("Course: " + code);

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
 * Edits existing course information (except course code).
 *
 * Steps:
 * - Find course by code
 * - Update course name, credit hour, summary, and link
 * - Validate numeric input for credit hour
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
     * Deletes a course from the system.
 *
 * Steps:
 * - Find course by code
 * - Confirm deletion from user
 * - Remove course from ArrayList
 * - Clear all student-course relationships
 */
    
    private void deleteCourse() {
    System.out.print("Enter Course Code to delete: ");
    String code = sc.nextLine();
    int index = findCourseIndex(code);

    if (index != -1) {

        System.out.println("Course found:");
        courses.get(index).displayCourse();

        System.out.print("Confirm deletion? (Y/N): ");
        String confirm = sc.nextLine();
        
        // remove enrollment relationships first
for (int i = 0; i < students.size(); i++) {
    enrollment[i][index] = false;
}

        if (confirm.equalsIgnoreCase("Y")) {

            // remove course
            courses.remove(index);

            // clear relationships (simple safe version)
            for (int i = 0; i < students.size(); i++) {
                for (int j = 0; j < courses.size(); j++) {
                    enrollment[i][j] = false;
                }
            }

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
 *
 * If no courses exist, display appropriate message.
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
      * Finds the index of a course in the ArrayList.
 *
 * Returns:
 * - Index if found
 * - -1 if not found
 *
 * Used for search, edit, delete operations.
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
     * Adds a new student to the system.
 *
 * Steps:
 * - Get student details from user
 * - Check for duplicate student ID
 * - Validate input fields
 * - Store Student object in ArrayList
 */
    
    private void addStudent() {
    // Ask for all inputs first
    System.out.print("Enter Student ID: ");
    String id = sc.nextLine();
    
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
     * Searches a student using student ID.
 *
 * Features:
 * - Updates search history cache
 * - Stores last searched student
 * - Displays student details if found
 */
    private void searchStudent() {
        System.out.print("Enter Student ID to search: ");
        String id = sc.nextLine();
        lastSearchedStudent = id;
        searchCache.add("Student: " + id);
        
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
    * Deletes a student from the system.
 *
 * Steps:
 * - Find student by ID
 * - Confirm deletion
 * - Remove student from ArrayList
 * - Remove all course relationships
 * - Shift enrollment matrix to maintain consistency
 */
    
    private void deleteStudent() {
    System.out.print("Enter Student ID to delete: ");
    String id = sc.nextLine();

    int index = findStudentIndex(id);

    if (index != -1) {
        Student s = students.get(index);
        System.out.println("Student found:");
        s.displayStudent();

        System.out.print("Confirm deletion? (Y/N): ");
        String confirm = sc.nextLine();

        if (confirm.equalsIgnoreCase("Y")) {

            // Clear relationships first
            for (int j = 0; j < courses.size(); j++) {
                enrollment[index][j] = false;
            }

            students.remove(index);

            // Shift enrollment data UP
            for (int i = index; i < students.size(); i++) {
                for (int j = 0; j < courses.size(); j++) {
                    enrollment[i][j] = enrollment[i + 1][j];
                }
            }

            System.out.println("Student deleted successfully!");
            viewAllStudents();

        } else {
            System.out.println("Deletion cancelled.");
        }

    } else {
        System.out.println("Student not found.");
    }
}
     /**
     * Method: viewAllStudents()
    /**
 * Displays all students in the system.
 *
 * If no students exist, show message.
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
      * Finds a student index using student ID.
 *
 * Returns:
 * - Index if found
 * - -1 if not found
 *
 * Used for all student-related operations.
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
    
    /**
     * Assigns a course to a student.
 *
 * Validation:
 * - Student must exist
 * - Course must exist
 * - Student must not already be enrolled
 *
 * Updates enrollment matrix.
 */
   private void assignCourseToStudent() {
    System.out.print("Enter Student ID: ");
    String studentId = sc.nextLine();
    int sIndex = findStudentIndex(studentId);

    if (sIndex == -1) {
        System.out.println("Error: Student not found.");
        return;
    }

    System.out.print("Enter Course Code: ");
    String courseCode = sc.nextLine();
    int cIndex = findCourseIndex(courseCode);

    if (cIndex == -1) {
        System.out.println("Error: Course not found.");
        return;
    }

    if (enrollment[sIndex][cIndex]) {
        System.out.println("Error: Student already enrolled in this course.");
        return;
    }

    // if NOT enrolled yet → assign
    enrollment[sIndex][cIndex] = true;
    System.out.println("Success: Course assigned to student.");
}
   
   /**
 * Displays all courses assigned to a specific student.
 *
 * If student has no courses, display message.
 */
    private void listCoursesByStudent() {
    System.out.print("Enter Student ID: ");
    String id = sc.nextLine();
    int sIndex = findStudentIndex(id);

    if (sIndex == -1) {
        System.out.println("Error: Student not found.");
        return;
    }

    boolean found = false;

    for (int j = 0; j < courses.size(); j++) {
        if (enrollment[sIndex][j]) {
            courses.get(j).displayCourse();
            found = true;
        }
    }

    if (!found) {
        System.out.println("This student has no courses.");
    }
}
    
    /**
 * Displays all students enrolled in a specific course.
 *
 * If no students are enrolled, display message.
 */
    
    private void listStudentsByCourse() {
    System.out.print("Enter Course Code: ");
    String code = sc.nextLine();
    int cIndex = findCourseIndex(code);

    if (cIndex == -1) {
        System.out.println("Error: Course not found.");
        return;
    }

    boolean found = false;

    for (int i = 0; i < students.size(); i++) {
        if (enrollment[i][cIndex]) {
            students.get(i).displayStudent();
            found = true;
        }
    }

    if (!found) {
        System.out.println("No students enrolled in this course.");
    }
    }
    
    /**
 * Displays last searched student and course.
 *
 * Acts as a simple search history feature.
 */
    
   private void suggestLastSearch() {
    if (lastSearchedStudent.isEmpty() && lastSearchedCourse.isEmpty()) {
        System.out.println("No search history yet.");
    } else {
        System.out.println("Last searched student: " + lastSearchedStudent);
        System.out.println("Last searched course: " + lastSearchedCourse);
    }
}
   
   /**
 * Auto-suggestion feature based on course data.
 *
 * Function:
 * - Searches course list based on input keyword
 * - Matches course code and course name
 * - Case-insensitive search
 *
 * Used to improve user experience (API feature).
 */
   
  private void autoSuggest(String input) {
    System.out.println("Suggestions:");

    boolean found = false;

    for (Course c : courses) {
        if (c.getCourseCode().toLowerCase().contains(input.toLowerCase()) ||
            c.getCourseName().toLowerCase().contains(input.toLowerCase())) {

            System.out.println("- Course: " + c.getCourseCode());
            found = true;
        }
    }

    if (!found) {
        System.out.println("No suggestions found.");
    }
}
}
