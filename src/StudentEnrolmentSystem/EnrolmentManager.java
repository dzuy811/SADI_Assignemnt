package StudentEnrolmentSystem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public interface EnrolmentManager {

    void semMenu(StudentManager sm, CourseManager cm) throws IOException, ParseException;

    void add(StudentManager sm, CourseManager cm);

    void delete(String sid, String cid, String semester);

    void update(StudentManager sm, CourseManager cm);

    ArrayList<Course> getCourseFromStudent(String sid, String semester);

    ArrayList<Student> getStudentFromCourse(String cid, String semester);

    ArrayList<Course> getAllCourseBySemester(CourseManager cm, String semester);
}
