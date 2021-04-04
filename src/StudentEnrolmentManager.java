import java.io.File;
import java.io.FileNotFoundException;
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

    // Take the content of csv file to save into ArrayList
    public void readStudentEnrolmentFile(StudentManager sm, CourseManager cm) throws FileNotFoundException {
        Scanner fileRead = new Scanner(new File("studentEnrolments.csv"));
        while (fileRead.hasNext()) {
            String[] list = fileRead.nextLine().split(",", 3);
            for(Student student: sm.getStudentList()) {
                if(student.getSid().equals(list[0])) {
                    for (Course course : cm.getCourseList()) {
                        if (course.getCid().equals(list[1])) {
                            StudentEnrolment studentEnrolment = new StudentEnrolment(student, course, list[2]);
                            studentEnrolmentList.add(studentEnrolment);
                            break;
                        }
                    }
                }
            }
        }
        fileRead.close();
    }

    public boolean add(StudentManager sm, CourseManager cm) {
        // Get input from the user and validate
        String sid = validateSidInput(sm);
        String cid = validateCidInput(cm);
        String semester = validateSemesterInput();

        int countCheck = 0;
        for(StudentEnrolment studentEnrolment : this.studentEnrolmentList) {
            // Check if the enrolment exist
            if(studentEnrolment.getStudent().getSid().equals(sid) &
                    studentEnrolment.getCourse().getCid().equals(cid) &
                    studentEnrolment.getSemester().equals(semester)) {
                System.out.println("You already enrolled to this course in this semester!");
                return false;
            }

            // Check the number of enrolment a student takes in one semester
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
        } else System.out.println("You already enrolled 4 courses in this semester!");
        return false;
    }

    public boolean delete(String sid, String semester, CourseManager cm) {
        String cid = validateCidInput(cm);
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
        String sid = validateSidInput(sm);
        String semester = validateSemesterInput();
        int countCourse = 0;

        System.out.println("All courses from student " + sid + " in semester " + semester);
        for (StudentEnrolment se : this.studentEnrolmentList) {
            if (se.getStudent().getSid().equals(sid) & se.getSemester().equals(semester)) {
                countCourse++;
                System.out.println(se.getCourse().getCid() + " " + se.getCourse().getName());
            }
        }

        while(true) {
            System.out.print("Delete or add new course from the list:\n" +
                            "1. Add\n" +
                            "2. Delete\n" +
                            "3. Exit\n" +
                            "Your choice: ");
            String option = sc.next();

            if(option.equals("1")) {
                return add(sm, cm);
            }
            if(option.equals("2")) {
                if(countCourse > 0) return delete(sid, semester, cm);
                else {
                    System.out.println("There is no courses available to delete!");
                    return false;
                }
            } if(option.equals("3")) return true;
            else System.out.print("Invalid input. Type it again!\n");
        }
    }

    public void getOneDisplay(StudentManager sm, CourseManager cm) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Get all: \n" +
                "1. Courses by student\n" +
                "2. Students by course\n" +
                "3. Exit" +
                "Your choice: ");
        String option = sc.next();
        while(true) {
            if (option.equals("1")) {
                String sid = validateSidInput(sm);
                System.out.println("All courses taken from student " + sid + ":");
                for(Course course : getOneFromStudent(sid))
                    System.out.println(course.getName());
                break;
            } else if (option.equals("2")) {
                String cid = validateCidInput(cm);
                System.out.println("All students taken from course " + cid + ":");
                for(Student student: getOneFromCourse(cid))
                    System.out.println(student.getName());
                break;
            } if(option.equals("3")) break;
            else System.out.println("Invalid input. Type it again!\n");
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

    public String validateSidInput(StudentManager studentManager) {
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.print("Student's id: ");
            String sid = sc.next();
            try {
                if (sid.length() == 8 &
                        sid.substring(1).matches("[0-9]+") &
                        sid.charAt(0) == 's') {
                    for(Student student: studentManager.getStudentList()) {
                        if (student.getSid().equals(sid))
                            return sid;
                    }
                    System.out.println("Invalid input. Type it again!\n");
                } else System.out.println("No student available!\n");
            } catch(Exception e) {
                System.out.println("Invalid input. Type it again!\n");
            }
        }
    }

    public String validateCidInput(CourseManager courseManager) {
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.print("Course's id: ");
            String cid = sc.next();
            try {
                if (cid.length() == 8 &
                        cid.substring(0,4).matches("[A-Z]+") &
                        cid.substring(4).matches("[0-9]+")) {
                    for(Course course : courseManager.getCourseList()) {
                        if(course.getCid().equals(cid)) {
                            return cid;
                        }
                    }
                    System.out.println("Invalid input. Type it again!\n");
                } else System.out.println("No course available!\n");
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