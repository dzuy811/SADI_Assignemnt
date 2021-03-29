import java.util.ArrayList;

public class StudentManager {
    private ArrayList<Student> studentList;

    public StudentManager(ArrayList<Student> studentList) {
        this.studentList = studentList;
    }

    public ArrayList<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(ArrayList<Student> studentList) {
        this.studentList = studentList;
    }
}
