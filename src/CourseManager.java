import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CourseManager {
    private final ArrayList<Course> courseList;

    public CourseManager() {
        this.courseList = new ArrayList<Course>();
    }

    public ArrayList<Course> getCourseList() {
        return courseList;
    }

    // Take the content of csv file to save into ArrayList
    public void readCourseFile() throws FileNotFoundException {
        Scanner fileRead = new Scanner(new File("courses.csv"));
        while (fileRead.hasNext()) {
            String[] list = fileRead.nextLine().split(",", 3);
            Course course = new Course(list[0], list[1], Integer.parseInt(list[2]));
            courseList.add(course);
        }
        fileRead.close();
    }
}
