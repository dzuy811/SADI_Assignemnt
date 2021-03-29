import java.util.ArrayList;
import java.util.Scanner;

public class StudentEnrolmentManager {
    private final ArrayList<StudentEnrolment> studentEnrolmentList;

    public StudentEnrolmentManager() {
        this.studentEnrolmentList = new ArrayList<StudentEnrolment>();
    }

    public ArrayList<StudentEnrolment> getStudentEnrolmentList() {
        return studentEnrolmentList;
    }

    public boolean add(StudentManager sm, CourseManager cm) {
        // Get input from console
        Scanner scanner = new Scanner(System.in);

        System.out.print("Student's id: ");
        String sid = scanner.next();

        System.out.print("Course's id: ");
        String cid = scanner.next();

        System.out.print("Semester: ");
        String semester = scanner.next();

        int countCheck = 0;
        for(StudentEnrolment studentEnrolment : this.studentEnrolmentList) {
            if(studentEnrolment.getStudent().getSid().equals(sid))
                countCheck++;
        }
        if(countCheck <= 3) {
            for (Student student : sm.getStudentList()) {
                if (student.getSid().equals(sid)) {
                    for (Course course : cm.getCourseList()) {
                        if (course.getCid().equals(cid)) {
                            this.studentEnrolmentList.add(new StudentEnrolment(student, course, semester));
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void delete(String sid, String cid, String semester) {
        for (int i = 0; i < this.studentEnrolmentList.size(); i++) {
            if (this.studentEnrolmentList.get(i).getStudent().getSid().equals(sid) &
                    this.studentEnrolmentList.get(i).getCourse().getCid().equals(cid) &
                    this.studentEnrolmentList.get(i).getSemester().equals(semester)) {
                this.studentEnrolmentList.remove(i);
                break;
            }
        }
    }

    public boolean update(StudentManager sm, CourseManager cm) {
        // Get input from console
        Scanner scanner = new Scanner(System.in);

        System.out.print("Student's id: ");
        String sid = scanner.next();

        System.out.print("Semester: ");
        String semester = scanner.next();

        System.out.println("All courses from student " + sid + " in semester " + semester);
        for (StudentEnrolment se : this.studentEnrolmentList) {
            if (se.getStudent().getSid().equals(sid) & se.getSemester().equals(semester))
                System.out.println(se.getCourse().getName());
        }
        while(true) {
            System.out.println("Delete or add new course from the list");
            String option = scanner.next().toLowerCase();

            if(option.equals("delete")) {
                add(sm, cm);
                break;
            }
            if(option.equals("add")) {
                System.out.println("Course you want to delete: ");
                String cid = scanner.next();
                delete(sid, cid, semester);
                break;
            }
            else
                System.out.println("Invalid input. Type it again!\n");
        }

        return false;
    }
}
