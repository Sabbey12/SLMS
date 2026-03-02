/**
 * File: CourseManager.java
 * Author: Team SLMS (Group 3)
 * Description: Main program to manage course profiles.
 * Features: Add, Search, Edit, Delete, View All courses.
 */

import java.util.ArrayList;
import java.util.Scanner;

class CourseManager {

    // Store multiple courses
    private ArrayList<Course> courses = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);
/**
     * Main method to start the program.
     */
    public static void main(String[] args) {
        CourseManager manager = new CourseManager();
        manager.run();  // Start program
    }

  /**
     * Method: run
     * Description: Main menu loop to allow user to select course operations.
     */
    public void run() {
        int choice;
        do {
            System.out.println("\n--- COURSE PROFILE SYSTEM ---");
            System.out.println("1. Add Course");
            System.out.println("2. Search Course");
            System.out.println("3. Edit Course");
            System.out.println("4. Delete Course");
            System.out.println("5. View All Courses");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            while (!sc.hasNextInt()) {
                System.out.print("Invalid input! Enter a number: ");
                sc.next();
            }
            choice = sc.nextInt();
            sc.nextLine();  // consume newline

            switch (choice) {
                case 1: addCourse(); break;
                case 2: searchCourse(); break;
                case 3: editCourse(); break;
                case 4: deleteCourse(); break;
                case 5: viewAllCourses(); break;
                case 0: System.out.println("Exiting program."); break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

      /**
     * Method: addCourse
     * Description: Adds a new course to the system after validating input.
     */
    private void addCourse() {
        System.out.print("Enter Course Name: ");
        String name = sc.nextLine();

        String code;
        while (true) {
            System.out.print("Enter Course Code: ");
            code = sc.nextLine();
            if (findCourseIndex(code) != -1) {
                System.out.println("Course code already exists! Enter a unique code.");
            } else break;
        }

        int credit;
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

        courses.add(new Course(name, code, credit, summary, link));
        System.out.println("Course added successfully!");
    }

   /**
     * Method: searchCourse
     * Description: Searches for a course by its course code and displays details.
     */
    private void searchCourse() {
        System.out.print("Enter Course Code to search: ");
        String code = sc.nextLine();
        int index = findCourseIndex(code);

        if (index != -1) {
            System.out.println("Course found:");
            courses.get(index).displayCourse();
        } else {
            System.out.println("Course not found.");
        }
    }

    /**
     * Method: editCourse
     * Description: Edits course information except course code.
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
     * Method: deleteCourse
     * Description: Deletes a course by its course code after confirmation.
     */
    private void deleteCourse() {
        System.out.print("Enter Course Code to delete: ");
        String code = sc.nextLine();
        int index = findCourseIndex(code);

        if (index != -1) {
            Course c = courses.get(index);
            System.out.println("Course found:");
            c.displayCourse();

            System.out.print("Confirm deletion? (Y/N): ");
            String confirm = sc.nextLine();
            if (confirm.equalsIgnoreCase("Y")) {
               courses.remove(index);
            System.out.println("Course deleted successfully!");

            // Display all courses after deletion
            viewAllCourses();
            } else {
                System.out.println("Deletion cancelled.");
            }
        } else {
            System.out.println("Course not found.");
        }
    }
 /**
     * Method: viewAllCourses
     * Description: Displays all courses stored in the system.
     */
    private void viewAllCourses() {
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
     * Method: findCourseIndex
     * Description: Finds the index of a course by its code.
     * @param code Course code to search
     * @return index of course or -1 if not found
     */
    private int findCourseIndex(String code) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getCourseCode().equalsIgnoreCase(code)) {
                return i;
            }
        }
        return -1;
    }
}