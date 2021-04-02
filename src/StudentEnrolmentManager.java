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
        String sid = validateSidInput();
        String cid = validateCidInput();
        String semester = validateSemesterInput();
        // Check if the enrolment exist
        for(StudentEnrolment studentEnrolment : this.studentEnrolmentList) {
            if(studentEnrolment.getStudent().getSid().equals(sid) &
            studentEnrolment.getCourse().getCid().equals(cid) &
            studentEnrolment.getSemester().equals(semester))
                return false;
        }

        int countCheck = 0;

        // Check the number of enrolment a student takes in one semester
        for(StudentEnrolment studentEnrolment : this.studentEnrolmentList) {
            if(studentEnrolment.getStudent().getSid().equals(sid) &
            studentEnrolment.getSemester().equals(semester))
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

    public boolean delete(String sid, String semester) {
        String cid = validateCidInput();
        for (int i = 0; i < this.studentEnrolmentList.size(); i++) {
            if (this.studentEnrolmentList.get(i).getStudent().getSid().equals(sid) &
                this.studentEnrolmentList.get(i).getCourse().getCid().equals(cid) &
                this.studentEnrolmentList.get(i).getSemester().equals(semester)) {
                this.studentEnrolmentList.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean update(StudentManager sm, CourseManager cm) {
        Scanner sc = new Scanner(System.in);
        String sid = validateSidInput();
        String semester = validateSemesterInput();

        System.out.println("All courses from student " + sid + " in semester " + semester);
        for (StudentEnrolment se : this.studentEnrolmentList) {
            if (se.getStudent().getSid().equals(sid) & se.getSemester().equals(semester))
                System.out.println(se.getCourse().getName());
        }
        while(true) {
            System.out.print("Delete or add new course from the list:\n" +
                            "1. Add\n" +
                            "2. Delete\n" +
                            "Your choice: ");
            String option = sc.next();

            if(option.equals("1")) {
                return add(sm, cm);
            }
            if(option.equals("2")) {
                return delete(sid, semester);
            }
            else System.out.print("Invalid input. Type it again!\n");
        }
    }

    public void getOneDisplay() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Get all: \n" +
                "1. Courses by student\n" +
                "2. Students by course\n" +
                "Your choice: ");
        String option = sc.next();
        while(true) {
            if (option.equals("1")) {
                String sid = validateSidInput();
                System.out.println("All courses taken from student " + sid + ":");
                for(Course course : getOneFromStudent(sid))
                    System.out.println(course.getName());
                break;
            } else if (option.equals("2")) {
                String cid = validateCidInput();
                System.out.println("All students taken from course " + cid + ":");
                for(Student student: getOneFromCourse(cid))
                    System.out.println(student.getName());
                break;
            } else {
                System.out.println("Invalid input. Type it again!\n");
            }
        }
    }

    public ArrayList<Course> getOneFromStudent(String sid) {
        ArrayList<Course> courseList = new ArrayList<>();
        for (StudentEnrolment se : this.studentEnrolmentList) {
            if (se.getStudent().getSid().equals(sid))
                courseList.add(se.getCourse());
        }
        return courseList;
    }

    public ArrayList<Student> getOneFromCourse(String cid) {
        ArrayList<Student> studentList = new ArrayList<>();
        for (StudentEnrolment se : this.studentEnrolmentList) {
            if (se.getCourse().getCid().equals(cid))
                studentList.add(se.getStudent());
        }
        return studentList;
    }

    public String validateSidInput() {
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.print("Student's id: ");
            String sid = sc.next();
            try {
                if (sid.length() == 8 &
                        sid.substring(1).matches("[0-9]+") &
                        sid.charAt(0) == 's') {
                    return sid;
                } else System.out.println("Invalid input. Type it again!\n");
            } catch(Exception e) {
                System.out.println("Invalid input. Type it again!\n");
            }
        }
    }

    public String validateCidInput() {
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.print("Course's id: ");
            String cid = sc.next();
            try {
                if (cid.length() == 8 &
                        cid.substring(0,4).matches("[A-Z]+") &
                        cid.substring(4).matches("[0-9]+")) {
                    return cid;
                } else System.out.println("Invalid input. Type it again!\n");
            } catch(Exception e) {
                System.out.println("Invalid input. Type it again!\n");
            }
        }
    }

    public String validateSemesterInput() {
        ArrayList<Character> semesterArray = new ArrayList<>();
        semesterArray.add('A');
        semesterArray.add('B');
        semesterArray.add('C');

        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.print("Semester: ");
            String semester = sc.next();
            try {
                if (semester.length() == 5 &
                        semester.substring(0,4).matches("[0-9]+") &
                        semesterArray.contains(semester.charAt(4))) {
                    return semester;
                } else System.out.println("Invalid input. Type it again!\n");
            } catch(Exception e) {
                System.out.println("Invalid input. Type it again!\n");
            }
        }
    }
}