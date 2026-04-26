/**
 * File: Main.java
 * Author: Team SLMS (Group 4)
 * Description: Main program that integrates the system.
 * Features: Displays menu and integrates with the function in other classes.
 */

import java.util.Scanner;

/**
* Main method: program entry point
*/

public class Main {
    public static void main(String[] args) {

        CourseManager courseManager = new CourseManager();
        StudentManager studentManager = new StudentManager();
        Scanner sc = new Scanner(System.in);

        int choice;

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
        do {
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
                System.out.print("Invalid input! Enter number: ");
                sc.next();
            }

            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            // Switch case to execute chosen operation
            switch (choice) {
                case 1: courseManager.addCourse(); break;
                case 2: courseManager.searchCourse(); break;
                case 3: courseManager.editCourse(); break;
                case 4: courseManager.deleteCourse(); break;
                case 5: courseManager.viewAllCourses(); break;

                case 6: studentManager.addStudent(); break;

                case 7: 
                    String searchedId = studentManager.searchStudent();
                    courseManager.setLastSearchedStudent(searchedId);
                    break;

                case 8: studentManager.editStudent(); break;
                case 9: studentManager.deleteStudent(); break;
                case 10: studentManager.viewAllStudents(); break;

                case 11: courseManager.assignCourseToStudent(studentManager); break;
                case 12: courseManager.listCoursesByStudent(studentManager); break;
                case 13: courseManager.listStudentsByCourse(studentManager); break;

                case 14:
                    courseManager.suggestLastSearch();
                    break;

                /** 
                * Prompts user to enter keyword to be searched.
                * Display possible searches.
                */
                case 15:
                    System.out.print("Enter keyword: ");
                    String input = sc.nextLine();
                    courseManager.autoSuggest(input);
                    break;

                case 0: System.out.println("Exiting program."); break;
                default: System.out.println("Invalid choice!");
            }

        } while (choice != 0); // Loop until user enters 0
    }
}