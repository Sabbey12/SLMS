import java.util.*;

public class CourseManager {

static ArrayList<Course> courses =
new ArrayList<>();

static Scanner input =
new Scanner(System.in);

public static void addCourse(){

System.out.println("Course Name:");
String name = input.nextLine();

System.out.println("Course Code:");
String code = input.nextLine();

System.out.println("Credit Hour:");
int credit = input.nextInt();
input.nextLine();

System.out.println("Summary:");
String summary = input.nextLine();

System.out.println("Teams Link:");
String link = input.nextLine();

Course newCourse =
new Course(name,code,credit,
summary,link);

courses.add(newCourse);

System.out.println("Course Added Successfully!");

}
}
