import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Scanner;

public class main {

    public static void main(String[] args) throws FileNotFoundException, ParseException {
        // Populate variable
        String sid;
        String cid;
        String semester;
        Scanner sc = new Scanner(System.in);

        // Populate students
        StudentManager sm = new StudentManager();
        sm.readStudentFile();

        // Populate courses
        CourseManager cm = new CourseManager();
        cm.readCourseFile();


        // Populate student enrolments
        StudentEnrolmentManager sem = new StudentEnrolmentManager();
        sem.readStudentEnrolmentFile(sm, cm);

        // Add enrolment
        System.out.println(sem.add(sm, cm) ? "Successfully" : "Failed");
        System.out.println(sem.update(sm, cm) ? "Successfully" : "Failed");
    }
}
