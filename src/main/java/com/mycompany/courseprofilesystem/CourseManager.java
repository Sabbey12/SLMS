/**
 * File: CourseManager.java
 * Author: Team SLMS (Group 4)
 * Description: Main program to manage course profiles.
 * Features: Add, Search, Edit, Delete, View All courses.
 */

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class: CourseManager
 *
 * This class acts as the controller for the Course attributes in Student Learning Management System (SLMS).
 *
 * It manages:
 * - Course data (add, edit, delete, search, view)
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
 * - boolean[][] enrollment → stores relationship between students and courses
 */

public class CourseManager {

    // ArrayList to store all courses. ArrayList allows dynamic resizing
    private ArrayList<Course> courses = new ArrayList<>();
    
    /// Relationship matrix:
    // enrollment[studentIndex][courseIndex] = true if student is enrolled
    private boolean[][] enrollment = new boolean[100][100];

    // Stores last searched student ID (for history feature)
    private String lastSearchedStudent = "";

    // Stores last searched course code (for history feature)
    private String lastSearchedCourse = "";

    private Scanner sc = new Scanner(System.in);

    public void setLastSearchedStudent(String id) {
        this.lastSearchedStudent = id;
    }

    /**
     * Method: addCourse()
     *
     * Adds a new course into the system.
     *
     * Steps:
     * - Get course details from user
     * - Validate input (no empty fields)
     * - Ensure course code is unique
     * - Store Course object in ArrayList
     */
    public void addCourse() {
        System.out.print("Enter Course Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Course Code: ");
        String code = sc.nextLine();

        // Ensure unique course code
        // findCourseIndex() searches if code already exists
        if (findCourseIndex(code) != -1) {
            System.out.println("Error: Course code already exists!");
            return;
        } // Exit loop when course code is unique

        System.out.print("Enter Credit: ");
        String creditInput = sc.nextLine();

        System.out.print("Enter Summary: ");
        String summary = sc.nextLine();

        System.out.print("Enter Link: ");
        String link = sc.nextLine();

        // Validation for ALL fields
        if (name.trim().isEmpty() || code.trim().isEmpty() || creditInput.trim().isEmpty()
                || summary.trim().isEmpty() || link.trim().isEmpty()) {
            System.out.println("All fields must be filled!");
            return;
        }

        // Validate credit is numeric
        if (!creditInput.matches("\\d+")) {
            System.out.println("Error: Credit must be a number!");
            return;
        }

        // Parse the value entered by user into integer value
        int credit = Integer.parseInt(creditInput);

        // Add when all fields are valid
        courses.add(new Course(name, code, credit, summary, link));
        System.out.println("Course added successfully!");
    }

    /**
     * Method: searchCourse()
     * * Searches a course using course code.
     *
     * Features:
     * - Stores search history (cache)
     * - Updates last searched course
     * - Displays course details if found
     * - Shows error if not found
     */
    public void searchCourse() {
        System.out.print("Enter Code: ");
        String code = sc.nextLine();
        lastSearchedCourse = code;

        int index = findCourseIndex(code);

        if (index != -1) {
            courses.get(index).displayCourse(); // Display course info
        } else {
            System.out.println("Course not found.");
        }
    }

    /**
     * Method: editCourse()
     *
     * Edits existing course information (except course code).
     *
     * Steps:
     * - Find course by code
     * - Update course name, credit hour, summary, and link
     * - Validate numeric input for credit hour
     */
    public void editCourse() {
        System.out.print("Enter Course Code to edit: ");
        String code = sc.nextLine();
        int index = findCourseIndex(code);

        if (index != -1) {
            Course c = courses.get(index);
            System.out.println("Editing Course: " + c.getCourseCode());

            String name;
            while (true) {
                System.out.print("New Course Name: ");
                name = sc.nextLine();
                if (!name.trim().isEmpty()) break;
                System.out.println("Course name cannot be empty!");
            }
            c.setCourseName(name);

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

            String summary;
            while (true) {
                System.out.print("New Summary: ");
                summary = sc.nextLine();
                if (!summary.trim().isEmpty()) break;
                System.out.println("Summary cannot be empty!");
            }
            c.setSummary(summary);

            String link;
            while (true) {
                System.out.print("New MS Teams Link: ");
                link = sc.nextLine();
                if (!link.trim().isEmpty()) break;
                System.out.println("Link cannot be empty!");
            }
            c.setMsTeamsLink(link);

            System.out.println("Course updated successfully!");
            c.displayCourse();
        } else {
            System.out.println("Course not found.");
        }
    }

    /**
     * Method: deleteCourse()
     *
     * Deletes a course from the system.
     *
     * Steps:
     * - Find course by code
     * - Confirm deletion from user
     * - Remove course from ArrayList
     * - Clear all student-course relationships
     */
    public void deleteCourse(StudentManager sm) {
        System.out.print("Enter Course Code to delete: ");
        String code = sc.nextLine();

        // Prevent blank input
        if (code.trim().isEmpty()) {
            System.out.println("Course code cannot be empty!");
            return;
        }

        int index = findCourseIndex(code);

        if (index != -1) {

            System.out.println("Course found:");
            courses.get(index).displayCourse();

            String confirm;
            while (true) {
                System.out.print("Confirm deletion? (Y/N): ");
                confirm = sc.nextLine();

                // Prevent blank input
                if (confirm.trim().isEmpty()) {
                    System.out.println("Confirmation cannot be empty!");
                } else if (confirm.equalsIgnoreCase("Y") || confirm.equalsIgnoreCase("N")) {
                    break;
                } else {
                    System.out.println("Invalid input! Please enter Y or N only.");
                }
            }

            if (confirm.equalsIgnoreCase("Y")) {
                // Remove enrollment relationships for this course first
                // Use sm parameter to access student list
                for (int i = 0; i < sm.getStudents().size(); i++) {
                    enrollment[i][index] = false;
                }

                // Remove course from list
                courses.remove(index);

                // Clear and reset enrollment matrix safely
                for (int i = 0; i < sm.getStudents().size(); i++) {
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
     *
     * Displays all courses in the system.
     * If no courses exist, display appropriate message.
     */
    public void viewAllCourses() {
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
     * * Finds the index of a course in the ArrayList.
     *
     * Returns:
     * - Index if found
     * - -1 if not found
     *
     * Used for search, edit, delete operations.
     */
    public int findCourseIndex(String code) {
        for (int i = 0; i < courses.size(); i++) {
            // equalsIgnoreCase: ignores uppercase/lowercase differences
            if (courses.get(i).getCourseCode().equalsIgnoreCase(code)) {
                return i; // Course found
            }
        }
        return -1; // -1 indicates course not found
    }

    // RELATIONSHIP METHODS
    /**
     * Method: assignCourseToStudent()
     *
     * Assigns a course to a student.
     *
     * Validation:
     * - Student must exist
     * - Course must exist
     * - Student must not already be enrolled
     *
     * Updates enrollment matrix.
     */
    public void assignCourseToStudent(StudentManager sm) {
        System.out.print("Student ID: ");
        int sIndex = sm.findStudentIndex(sc.nextLine());

        System.out.print("Course Code: ");
        int cIndex = findCourseIndex(sc.nextLine());

        if (sIndex == -1 || cIndex == -1) {
            System.out.println("Invalid student/course.");
            return;
        }

        if (enrollment[sIndex][cIndex]) {
            System.out.println("Already enrolled.");
            return;
        }

        // if NOT enrolled yet → assign
        enrollment[sIndex][cIndex] = true;
        System.out.println("Success: Course assigned to student.");
    }

    /**
     * Method: ListCoursesByStudent()
     *
     * Displays all courses assigned to a specific student.
     *
     * If student has no courses, display message.
     */
    public void listCoursesByStudent(StudentManager sm) {
        System.out.print("Student ID: ");
        int sIndex = sm.findStudentIndex(sc.nextLine());

        if (sIndex == -1) {
            System.out.println("Error : Student not found.");
            return;
        }

        boolean found = false;

        for (int j = 0; j < courses.size(); j++) {
            if (enrollment[sIndex][j]) {
                courses.get(j).displayCourse();
                found = true;
            }
        }

        if (!found) System.out.println("This student has no courses.");
    }

    /**
     * Method: ListStudentsByCourse()
     *
     * Displays all students enrolled in a specific course.
     * If no students are enrolled, display message.
     */
    public void listStudentsByCourse(StudentManager sm) {
        System.out.print("Course Code: ");
        int cIndex = findCourseIndex(sc.nextLine());

        if (cIndex == -1) {
            System.out.println("Error : Course not found.");
            return;
        }

        boolean found = false;

        for (int i = 0; i < sm.getStudents().size(); i++) {
            if (enrollment[i][cIndex]) {
                sm.getStudents().get(i).displayStudent();
                found = true;
            }
        }

        if (!found) System.out.println("No students enrolled in this course.");
    }

    /**
     * Method: SuggestLastSearch()
     *
     * Displays last searched student and course.
     * Acts as a simple search history feature.
     */
    public void suggestLastSearch() {
        if (lastSearchedStudent.isEmpty() && lastSearchedCourse.isEmpty()) {
            System.out.println("No search history yet.");
        } else {
            System.out.println("Last searched student: " + lastSearchedStudent);
            System.out.println("Last searched course: " + lastSearchedCourse);
        }
    }

    /**
     * Method: AutoSuggest()
     *
     * Auto-suggestion feature based on course data.
     *
     * Function:
     * - Searches course list based on input keyword
     * - Matches course code and course name
     * - Case-insensitive search
     *
     * Used to improve user experience (API feature).
     */
    public void autoSuggest(String input) {
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
