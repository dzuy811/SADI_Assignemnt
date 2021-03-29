import java.util.ArrayList;

public class StudentEnrolment implements StudentEnrolmentManager {
    private ArrayList<Student> studentList;
    private ArrayList<Course> courseList;
    private String semester;

    public StudentEnrolment() {
        studentList = new ArrayList<Student>();
        courseList = new ArrayList<Course>();
    }

    public ArrayList<Student> getStudentList() {
        return studentList;
    }

    public ArrayList<Course> getCourseList() {
        return courseList;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public boolean add() {
        return false;
    };
    public boolean update() {
        return false;
    };
    public boolean detele() {
        return false;
    };
    public boolean getOne() {
        return false;
    };
    public boolean getAll() {
        return false;
    };
}
