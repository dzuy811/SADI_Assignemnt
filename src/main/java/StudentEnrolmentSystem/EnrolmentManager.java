package StudentEnrolmentSystem;

import java.io.IOException;
import java.text.ParseException;

public interface EnrolmentManager {

    boolean semMenu(StudentManager sm, CourseManager cm) throws IOException, ParseException;

    boolean add(StudentManager sm, CourseManager cm);

    boolean delete(String sid, String cid, String semester);

    boolean update(StudentManager sm, CourseManager cm);

    void printCourseByStudent(StudentManager sm) throws IOException;

    void printStudentByCourse(CourseManager cm) throws IOException;

    void printCourseBySemester(CourseManager cm) throws IOException;

    void printAllEnrolment();
}
