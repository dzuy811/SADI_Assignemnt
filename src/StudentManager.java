import java.util.ArrayList;

public class StudentManager {
    private final ArrayList<Student> studentList;

    public StudentManager() {
        this.studentList = new ArrayList<Student>();
    }

    public ArrayList<Student> getStudentList() {
        return studentList;
    }
}
