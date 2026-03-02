import java.util.*;

public class CourseManager {

    static ArrayList<Course> courses =
            new ArrayList<>();

    static Scanner input =
            new Scanner(System.in);

    // ADD COURSE
    public static void addCourse(){

        if(courses.size() >= 100){

            System.out.println(
            "Maximum course reached");

            return;
        }

        System.out.print("Name: ");
        String name = input.nextLine();

        System.out.print("Code: ");
        String code = input.nextLine();

        System.out.print("Credit Hour: ");
        int credit = input.nextInt();
        input.nextLine();

        System.out.print("Summary: ");
        String summary = input.nextLine();

        System.out.print("Teams Link: ");
        String link = input.nextLine();

        courses.add(
        new Course(name,code,
        credit,summary,link));

        System.out.println(
        "Course Added!");
    }

    // SEARCH COURSE (Linear Search)
    public static void searchCourse(){

        System.out.print(
        "Search Code: ");

        String code =
        input.nextLine();

        for(Course c:courses){

            if(c.getCourseCode()
            .equalsIgnoreCase(code)){

                displayCourse(c);
                return;

            }

        }

        System.out.println(
        "Course Not Found");
    }

    // EDIT COURSE
    public static void editCourse(){

        System.out.print(
        "Enter Code:");

        String code=
        input.nextLine();

        for(Course c:courses){

            if(c.getCourseCode()
            .equalsIgnoreCase(code)){

                System.out.print(
                "New Name:");

                c.setCourseName(
                input.nextLine());

                System.out.print(
                "New Credit:");

                c.setCreditHour(
                input.nextInt());

                input.nextLine();

                System.out.print(
                "New Summary:");

                c.setSummary(
                input.nextLine());

                displayCourse(c);

                return;
            }

        }

        System.out.println(
        "Course Not Found");
    }

    // DELETE COURSE
    public static void deleteCourse(){

        System.out.print(
        "Delete Code:");

        String code=
        input.nextLine();

        Iterator<Course> it =
        courses.iterator();

        while(it.hasNext()){

            Course c=it.next();

            if(c.getCourseCode()
            .equalsIgnoreCase(code)){

                it.remove();

                System.out.println(
                "Deleted!");

                viewAll();

                return;
            }

        }

        System.out.println(
        "Course Not Found");
    }

    // DISPLAY ONE COURSE
    public static void displayCourse(
    Course c){

        System.out.println(
        "Name:"+c.getCourseName());

        System.out.println(
        "Code:"+c.getCourseCode());

        System.out.println(
        "Credit:"+c.getCreditHour());

        System.out.println(
        "Summary:"+c.getSummary());

        System.out.println(
        "Teams:"+c.getTeamsLink());

        System.out.println(
        "-------------");
    }

    // VIEW ALL
    public static void viewAll(){

        for(Course c:courses){

            displayCourse(c);

        }

    }

    // MENU
    public static void main(String[] args){

        while(true){

            System.out.println(
            "\n1 Add");

            System.out.println(
            "2 Search");

            System.out.println(
            "3 Edit");

            System.out.println(
            "4 Delete");

            System.out.println(
            "5 View All");

            int choice=
            input.nextInt();

            input.nextLine();

            switch(choice){

                case 1:addCourse();break;

                case 2:searchCourse();break;

                case 3:editCourse();break;

                case 4:deleteCourse();break;

                case 5:viewAll();break;

            }

        }

    }

}
