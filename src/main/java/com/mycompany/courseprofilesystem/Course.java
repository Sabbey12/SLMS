/**
 * File: Course.java
 * Author: Team SLMS (Group 4)
 * Description: Handles individual course data and provides methods to display it.
 * Date: 02 Mar 2026
 */

/**
 * Class: Course
 * Description: Represents a course profile in the SLMS system.
 * Stores course information such as name, code, credit hour, summary,
 * and MS Teams link.
 */

public class Course {
    // Attributes available in the course
    private String courseName;
    private String courseCode; // cannot be edited after creation
    private int creditHour;
    private String summary;
    private String msTeamsLink;

    // Constructor for the course
    public Course(String courseName, String courseCode, int creditHour, String summary, String msTeamsLink) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.creditHour = creditHour;
        this.summary = summary;
        this.msTeamsLink = msTeamsLink;
    }

    // Getters and Setters for the required course's attributes
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getCourseCode() { return courseCode; }

    public int getCreditHour() { return creditHour; }
    public void setCreditHour(int creditHour) { this.creditHour = creditHour; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getMsTeamsLink() { return msTeamsLink; }
    public void setMsTeamsLink(String msTeamsLink) { this.msTeamsLink = msTeamsLink; }

    // Display all course's attributes
    public void displayCourse() {
        System.out.println("Course Name  : " + courseName);
        System.out.println("Course Code  : " + courseCode);
        System.out.println("Credit Hour  : " + creditHour);
        System.out.println("Summary      : " + summary);
        System.out.println("MS Teams Link: " + msTeamsLink);
        System.out.println("--------------------------------------");
    }
}
