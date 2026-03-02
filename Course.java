public class Course {

    private String courseName;
    private String courseCode;
    private int creditHour;
    private String summary;
    private String teamsLink;

    // Constructor
    public Course(String name,
    String code,
    int credit,
    String summary,
    String link){

        this.courseName=name;
        this.courseCode=code;
        this.creditHour=credit;
        this.summary=summary;
        this.teamsLink=link;

    }

    // Getter
    public String getCourseName(){
        return courseName;
    }

    public String getCourseCode(){
        return courseCode;
    }

    public int getCreditHour(){
        return creditHour;
    }

    public String getSummary(){
        return summary;
    }

    public String getTeamsLink(){
        return teamsLink;
    }

    // Setter
    public void setCourseName(
    String name){

        courseName=name;

    }

    public void setCreditHour(
    int credit){

        creditHour=credit;

    }

    public void setSummary(
    String summary){

        this.summary=summary;

    }

}
