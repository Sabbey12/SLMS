/**
 * File: Student.java
 * Author: Team SLMS (Group 4)
 * Description: Handles individual student data and provides methods to display it.
 * Date: 17 Mar 2026
 */

/**
 * Class: Student
 * Description:
 * This class represents a Student object in the SLMS system.
 * 
 * Each student has all these attributes:
 * - studentID: unique identifier (cannot be edited)
 * - firstName
 * - lastName
 * - email
 * - phone
 * 
 * This class includes:
 * - Constructor to create a Student object
 * - Getter methods to access attributes
 * - Setter methods to modify editable attributes
 * - displayStudent method to show student information in a clear format
 */
public class Student {

    // Student's attributes
    private String studentID;   // Unique student identifier, cannot be changed after creation
    private String firstName;   // Student's first name
    private String lastName;    // Student's last name
    private String email;       // Student's email address
    private String phone;       // Student's phone number


    /**
     * Constructor: Student
     * Purpose:
     * - Initialize a new Student object with all required attributes
     * - studentID is passed in constructor because it is unique and must not be changed later
     */
    public Student(String studentID, String firstName, String lastName, String email, String phone){
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    /**
     * Getter methods:
     * These allow other classes (e.g., CourseManager) to access student attributes.
     * We do not allow direct access to private attributes for data encapsulation.
     * 
     * Encapsulation ensures data safety and control over how attributes are accessed or modified.
     */

    public String getStudentID(){
        return studentID; // StudentID is unique and immutable, so only getter is provided
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getEmail(){
        return email;
    }

    public String getPhone(){
        return phone;
    }

    /**
     * Setter methods:
     * These allow modification of attributes except studentID.
     * Why no setter for studentID?
     * - studentID is the unique identifier.
     * - Changing it could cause duplicate conflicts or break search/delete operations.
     */

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    /**
     * Method: displayStudent
     * Purpose:
     * - To show all student attributes in a clear and organized format
     * - Used after search, edit, or delete operations in CourseManager
     * 
     * Why use a separate display method?
     * - To avoid repeating System.out.println code in multiple places
     * - Improves code reusability and readability
     */

    public void displayStudent(){
        System.out.println("Student ID   : " + studentID);
        System.out.println("Name         : " + firstName + " " + lastName);
        System.out.println("Email        : " + email);
        System.out.println("Phone        : " + phone);
        System.out.println("--------------------------------------");
    }
}
