public class Course {

    private String courseName;
    private String courseCode;
    private int creditHour;
    private String summary;
    private String teamsLink;

    // Constructor
    public Course(String courseName, String courseCode,
                  int creditHour, String summary,
                  String teamsLink) {

        this.courseName = courseName;
        this.courseCode = courseCode;
        this.creditHour = creditHour;
        this.summary = summary;
        this.teamsLink = teamsLink;
    }

    // Getter
    public String getCourseCode(){
        return courseCode;
    }

    public String getCourseName(){
        return courseName;
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

    // Setter (course code cannot change)
    public void setCourseName(String name){
        this.courseName = name;
    }

    public void setCreditHour(int hour){
        this.creditHour = hour;
    }

    public void setSummary(String summary){
        this.summary = summary;
    }

    public void setTeamsLink(String link){
        this.teamsLink = link;
    }

}
