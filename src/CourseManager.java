import java.util.ArrayList;

public class CourseManager {
    private final ArrayList<Course> courseList;

    public CourseManager() {
        this.courseList = new ArrayList<Course>();
    }

    public ArrayList<Course> getCourseList() {
        return courseList;
    }
}
