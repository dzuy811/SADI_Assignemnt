package StudentEnrolmentSystem;

import java.io.IOException;
import java.text.ParseException;

public class main {

    public static void main(String[] args) throws IOException, ParseException {
        StudentManager sm = new StudentManager();
        CourseManager cm = new CourseManager();
        StudentEnrolmentManager sem = new StudentEnrolmentManager();

        sem.semMenu(sm, cm);
    }
}
