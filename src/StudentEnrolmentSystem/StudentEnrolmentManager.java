package StudentEnrolmentSystem;

import java.io.*;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentEnrolmentManager implements EnrolmentManager {
    private final ArrayList<StudentEnrolment> studentEnrolmentList;

    public StudentEnrolmentManager() {
        this.studentEnrolmentList = new ArrayList<StudentEnrolment>();
    }

    public ArrayList<StudentEnrolment> getAll() {
        return studentEnrolmentList;
    }

    public StudentEnrolment getOne(String sid, String cid, String semester) {
        for(StudentEnrolment se: getAll()) {
            if(se.getStudent().equals(sid) && se.getCourse().equals(cid) && se.getSemester().equals(semester))
                return se;
        }
        return null;
    }

    public void semMenu(StudentManager sm, CourseManager cm) throws IOException, ParseException {
        // Populate data
        sm.getFileNameMenu();
        cm.getFileNameMenu();
        getFileNameMenu(sm, cm);

        Scanner sc = new Scanner(System.in);

        while(true) {
            System.out.print("\nChoose these options:\n" +
                    "1. Add enrolment\n" +
                    "2. Update enrolment\n" +
                    "3. Print all courses for 1 student in 1 semester\n" +
                    "4. Print all students of 1 course in 1 semester\n" +
                    "5. Prints all courses offered in 1 semester\n" +
                    "6. Exit\n" +
                    "Your choice: ");
            String menuOption = sc.next();

            if(menuOption.equals("1")) add(sm, cm);
            else if(menuOption.equals("2")) update(sm, cm);
            else if(menuOption.equals("3")) printCourseByStudent(sm);
            else if(menuOption.equals("4")) printStudentByCourse(cm);
            else if(menuOption.equals("5")) printCourseBySemester(cm);
            else if(menuOption.equals("6")) break;
            else System.out.println("Invalid value. Type it again!");
        }
    }

    // Take the content of csv file to save into ArrayList
    public void readStudentEnrolmentFile(StudentManager sm, CourseManager cm, String fileName) throws FileNotFoundException {
        Scanner fileRead = new Scanner(new File(fileName));
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

    public void getFileNameMenu(StudentManager sm, CourseManager cm) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.print("\nWhich file do you want to get the student enrolment data?\n" +
                    "1. Default file\n" +
                    "2. Custom file\n" +
                    "Your choice: ");
            String fileOption = sc.next();
            if(fileOption.equals("1")) {
                readStudentEnrolmentFile(sm, cm, "studentEnrolments.csv");
                break;
            }
            else if(fileOption.equals("2")){
                System.out.print("Type the file name: ");
                readStudentEnrolmentFile(sm, cm, sc.next());
                break;
            } else System.out.println("Invalid value. Type it again!\n");
        }

    }

    public boolean addEnrolment(String sid, String cid, String semester, StudentManager sm, CourseManager cm) {
        int countCheck = 0;
        for(StudentEnrolment studentEnrolment : this.studentEnrolmentList) {
            // Check if the enrolment exist
            if(getOne(sid, cid, semester) != null) {
                System.out.println("\nYou already enrolled to this course in this semester!");
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
        } else System.out.println("\nYou already enrolled 4 courses in this semester!");
        return false;
    }

    public void add(StudentManager sm, CourseManager cm) {
        // Get input from the user and validate
        String sid = validateSidInput(sm);
        String semester = validateSemesterInput();
        String cid = validateCidInput(cm);

        // Check if that course belongs to that semester
        if(!cm.checkCourseBySemester(cid, semester)) {
            System.out.println("\nThis course is not belong to semester " + semester);
        } else {
            // Call addEnrolment method
            boolean check = addEnrolment(sid, cid, semester, sm, cm);
            System.out.println((check) ? "Successfully added!" : "Failed!");
            printAllEnrolment();
        }
    }

    public void delete(String sid, String cid, String semester) {
        for (int i = 0; i < getAll().size(); i++) {
            if (this.studentEnrolmentList.get(i).getStudent().getSid().equals(sid) &
                this.studentEnrolmentList.get(i).getCourse().getCid().equals(cid) &
                this.studentEnrolmentList.get(i).getSemester().equals(semester)) {
                this.studentEnrolmentList.remove(i);
            }
        }
    }

    public void update(StudentManager sm, CourseManager cm) {
        Scanner sc = new Scanner(System.in);

        String sid = validateSidInput(sm);
        String semester = validateSemesterInput();
        int countCourse = 0;

        System.out.println("All courses from student " + sid + " in semester " + semester);
        ArrayList<Course> courseList = getCourseFromStudent(sid, semester);
        printAllCourse(courseList);

        while(true) {
            System.out.print("Delete or add new course from the list:\n" +
                            "1. Add\n" +
                            "2. Delete\n" +
                            "3. Exit\n" +
                            "Your choice: ");
            String option = sc.next();

            if(option.equals("1")) {
                add(sm, cm);
                break;
            }
            if(option.equals("2")) {
                if(courseList.size() > 0) {
                    String cid = validateCidInput(cm);
                    int count = 0;
                    for(Course course: courseList) {
                        if(course.getCid().equals(cid)) {
                            count++;
                            delete(sid, cid, semester);
                            System.out.println("Delete successfully!");
                            break;
                        }
                    }
                    if(count == 0)
                        System.out.println("This course is not belong to this semester!");
                }
                else {
                    System.out.println("There is no courses available to delete!");
                    break;
                }
            } if(option.equals("3")) break;
            else System.out.print("Invalid input. Type it again!\n");
        }
    }

    public void printCourseByStudent(StudentManager sm) throws IOException {
        String sid = validateSidInput(sm);
        String semester = validateSemesterInput();
        System.out.println("All courses taken from student " + sid + " in semester " + semester + ":");
        ArrayList<Course> courseList = getCourseFromStudent(sid, semester);
        printAllCourse(courseList);
        String fileName = sid + "_" + semester + ".csv";
        writeFile(fileName,"",false);
        if(saveFile()) {
            for (int i = 0; i < courseList.size(); i++) {
                writeCourseByStudent(courseList.get(i), fileName, true);
                if (i != courseList.size() - 1)
                    writeFile(fileName, "\n", true);
            }
        }
    }

    public void printStudentByCourse(CourseManager cm) throws IOException {
        String cid = validateCidInput(cm);
        String semester = validateSemesterInput();
        System.out.println("All students taken from course " + cid + " in semester " + semester + ":");
        ArrayList<Student> studentList = getStudentFromCourse(cid, semester);
        printAllStudent(studentList);
        String fileName = cid + "_" + semester + ".csv";
        writeFile(fileName,"",false);
        if(saveFile()) {
            for(int i = 0; i < studentList.size(); i++) {
                writeStudentByCourse(studentList.get(i), fileName, true);
                if(i != studentList.size() - 1)
                    writeFile(fileName,"\n",true);
            }
        }
    }

    public void printCourseBySemester(CourseManager cm) throws IOException {
        String semester = validateSemesterInput();
        System.out.println("All courses offered in 1 semester:");
        ArrayList<Course> courseList = getAllCourseBySemester(cm, semester);
        printAllCourse(courseList);
        String fileName = semester + ".csv";
        writeFile(fileName,"",false);
        if(saveFile()) {
            for(int i = 0; i < courseList.size(); i++) {
                writeCourseBySemester(courseList.get(i), semester, true);
                if(i != courseList.size() - 1)
                    writeFile(fileName,"\n",true);
            }
        }
    }

    public void writeCourseByStudent(Course course, String fileName, boolean notOverwritten) throws IOException {
        String content = course.getCid() + "," + course.getName() + "," + course.getSemester();
        writeFile(fileName, content, notOverwritten);
    }

    public void writeStudentByCourse(Student student, String fileName, boolean notOverwritten) throws IOException {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String content = student.getSid() + "," + student.getName() + "," + formatter.format(student.getDOB());
        writeFile(fileName, content, notOverwritten);
    }

    public void writeCourseBySemester(Course course, String fileName, boolean notOverwritten) throws IOException {
        String content = course.getCid() + "," +course.getName() + "," + course.getSemester();
        writeFile(fileName, content, notOverwritten);
    }

    public ArrayList<Course> getCourseFromStudent(String sid, String semester) {
        ArrayList<Course> courseList = new ArrayList<>();
        for (StudentEnrolment se : getAll()) {
            if (se.getStudent().getSid().equals(sid) && se.getSemester().equals(semester))
                courseList.add(se.getCourse());
        }
        return courseList;
    }

    public ArrayList<Student> getStudentFromCourse(String cid, String semester) {
        ArrayList<Student> studentList = new ArrayList<>();
        for (StudentEnrolment se : getAll()) {
            if (se.getCourse().getCid().equals(cid) && se.getSemester().equals(semester))
                studentList.add(se.getStudent());
        }
        return studentList;
    }

    public ArrayList<Course> getAllCourseBySemester(CourseManager cm, String semester) {
        ArrayList<Course> courseList = new ArrayList<>();
        for(Course course: cm.getCourseList()){
            if(course.getSemester().equals(semester))
                courseList.add(course);
        }
        return courseList;
    }

    public String validateSidInput(StudentManager studentManager) {
        printAllStudent(studentManager.getStudentList());
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
        printAllCourse(courseManager.getCourseList());
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

    public void writeFile(String fileName, String value, boolean notOverwritten) throws IOException {
        PrintWriter output = new PrintWriter(new FileWriter(fileName, notOverwritten));
        output.print(value);
        output.close();
    }

    public boolean saveFile() {
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.print("Do you want to save the report?\n" +
                    "1. Yes\n" +
                    "2. No\n" +
                    "Your choice: ");
            String saveOption = sc.next();
            if(saveOption.equals("1")) {
                return true;
            } else if(saveOption.equals("2")) {
                return false;
            } else {
                System.out.println("Invalid input. Type it again!\n");
            }
        }
    }

    public void printAllEnrolment() {
        String leftAlignFormat = "| %-8s | %-25s | %-8s | %-40s |  %-7s | %n";

        System.out.format("+----------+---------------------------+----------+------------------------------------------+----------+%n");
        System.out.format("|   Sid    |       Student name        |   Cid    |               Course name                | Semester |%n");
        System.out.format("+----------+---------------------------+----------+------------------------------------------+----------+%n");
        for(StudentEnrolment se: this.studentEnrolmentList) {
            System.out.format(leftAlignFormat, se.getStudent().getSid(),
                    se.getStudent().getName(), se.getCourse().getCid(),
                    se.getCourse().getName(), se.getSemester());
        }

        System.out.format("+----------+---------------------------+----------+------------------------------------------+----------+%n");
    }

    public void printAllStudent(ArrayList<Student> studentList) {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String leftAlignFormat = "| %-8s | %-25s |  %-8s   | %n";

        System.out.format("+----------+---------------------------+---------------+%n");
        System.out.format("|   Sid    |       Student name        | Date of Birth |%n");
        System.out.format("+----------+---------------------------+---------------+%n");
        for(Student student: studentList) {
            System.out.format(leftAlignFormat, student.getSid(), student.getName(), formatter.format(student.getDOB()));
        }
        System.out.format("+----------+---------------------------+---------------+%n");
    }

    public void printAllCourse(ArrayList<Course> courseList) {
        String leftAlignFormat = "| %-8s | %-40s |   %-4s |  %-7s |%n";

        System.out.format("+----------+------------------------------------------+--------+----------+%n");
        System.out.format("|   Cid    |               Course name                | Credit | Semester |%n");
        System.out.format("+----------+------------------------------------------+--------+----------+%n");
        for(Course course: courseList) {
            System.out.format(leftAlignFormat, course.getCid(), course.getName(), course.getNumberOfCredits(), course.getSemester());
        }

        System.out.format("+----------+------------------------------------------+--------+----------+%n");
    }
}