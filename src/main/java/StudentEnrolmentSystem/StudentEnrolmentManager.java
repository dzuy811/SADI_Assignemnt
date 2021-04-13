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

    // Get all student enrolments
    public ArrayList<StudentEnrolment> getAll() {
        return this.studentEnrolmentList;
    }

    // Get one student enrolments based on sid, cid, and semester
    public StudentEnrolment getOne(String sid, String cid, String semester) {
        for(StudentEnrolment se: getAll()) {
            if(se.getStudent().getSid().equals(sid) &&
                se.getCourse().getCid().equals(cid) &&
                se.getSemester().equals(semester)) return se;
        }
        return null;
    }

    // The main menu for the program
    public boolean semMenu(StudentManager sm, CourseManager cm) throws IOException, ParseException {
        // Populate data
        sm.getFileNameMenu();
        cm.getFileNameMenu();
        getFileNameMenu(sm, cm);

        Scanner sc = new Scanner(System.in);

        label:
        while(true) {
            System.out.print("\nChoose these options:\n" +
                    "1. Add enrolment\n" +
                    "2. Update enrolment\n" +
                    "3. Print all courses for 1 student in 1 semester\n" +
                    "4. Print all students of 1 course in 1 semester\n" +
                    "5. Print all courses offered in 1 semester\n" +
                    "6. Print all enrolments\n" +
                    "7. Exit\n" +
                    "Your choice: ");
            String menuOption = sc.next();

            switch (menuOption) {
                case "1":
                    add(sm, cm);
                    break;
                case "2":
                    update(sm, cm);
                    break;
                case "3":
                    printCourseByStudent(sm);
                    break;
                case "4":
                    printStudentByCourse(cm);
                    break;
                case "5":
                    printCourseBySemester(cm);
                    break;
                case "6":
                    printAllEnrolment();
                    break;
                case "7":
                    break label;
                default:
                    // Announce the error
                    System.out.println("Invalid value. Type it again!");
                    break;
            }
        }
        return true;
    }

    // Take the content of csv file to save into ArrayList
    public boolean readStudentEnrolmentFile(StudentManager sm, CourseManager cm, String fileName) throws FileNotFoundException {
        Scanner fileRead = new Scanner(new File(fileName));
        while (fileRead.hasNext()) {
            // Split the line into each string
            String[] list = fileRead.nextLine().split(",", 3);

            for(Student student: sm.getStudentList()) {
                if(student.getSid().equals(list[0])) {
                    for (Course course : cm.getCourseList()) {
                        if (course.getCid().equals(list[1])) {
                            StudentEnrolment studentEnrolment = new StudentEnrolment(student, course, list[2]);
                            // Add each enrolment into the array list
                            this.studentEnrolmentList.add(studentEnrolment);
                            break;
                        }
                    }
                }
            }
        }
        // Close the file
        fileRead.close();
        return true;
    }

    // The menu to populate data from csv file
    public boolean getFileNameMenu(StudentManager sm, CourseManager cm) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.print("\nWhich file do you want to get the student enrolment data?\n" +
                    "1. Default file\n" +
                    "2. Custom file\n" +
                    "Your choice: ");
            String fileOption = sc.next();
            if(fileOption.equals("1")) {
                // Read the default file
                readStudentEnrolmentFile(sm, cm, "studentEnrolments.csv");
                break;
            }
            else if(fileOption.equals("2")){
                while(true) {
                    // Read the added file and check if this file exists or not
                    try {
                        System.out.print("Type the file name: ");
                        readStudentEnrolmentFile(sm, cm, sc.next());
                        break;
                    } catch(FileNotFoundException e) {
                        // If there is no file found, type again
                        System.out.println("No File Found. Type it again!\n");
                    }
                }
                break;
            } else System.out.println("Invalid value. Type it again!\n");
        }
        return true;
    }

    // Method to add the enrolment from input
    public boolean addEnrolment(String sid, String cid, String semester, StudentManager sm, CourseManager cm) {
        // Check if the enrolment exists
        if(getOne(sid, cid, semester) != null) {
            System.out.println("\nYou already enrolled to this course in this semester!");
            return false;
        }

        int countCheck = 0;
        for(StudentEnrolment studentEnrolment : this.studentEnrolmentList) {
            // Check the number of enrolment a student took in one semester
            if(studentEnrolment.getStudent().getSid().equals(sid) &
                studentEnrolment.getSemester().equals(semester))
                countCheck++;
        }
        // Allow to add if the number of enrolments in 1 semester is less than 5
        if(countCheck <= 4) {
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

    // Add menu
    public boolean add(StudentManager sm, CourseManager cm) {
        // Get input from the user and validate
        String sid = validateSidInput(sm);
        String semester = validateSemesterInput();

        ArrayList<Course> courseList = getCourseFromStudent(sid, semester);
        if(courseList.size() != 0) {
            System.out.println("All courses from student " + sid + " in semester " + semester + ":");
            printAllCourse(courseList);
        }

        String cid = validateCidInput(cm);

        // Check if that course belongs to that semester
        if(!cm.checkCourseBySemester(cid, semester)) {
            System.out.println("\nThis course is not belong to semester " + semester);
            return false;
        } else {
            // Call addEnrolment method
            if(addEnrolment(sid, cid, semester, sm, cm)) {
                System.out.println("Successfully added!");
                printAllEnrolment();
                return true;
            } else {
                System.out.println("Failed to add!");
                return false;
            }
        }
    }

    // Delete the enrolment
    public boolean delete(String sid, String cid, String semester) {
        // Find the enrolment and delete
        for (int i = 0; i < getAll().size(); i++) {
            if (this.studentEnrolmentList.get(i).getStudent().getSid().equals(sid) &
                this.studentEnrolmentList.get(i).getCourse().getCid().equals(cid) &
                this.studentEnrolmentList.get(i).getSemester().equals(semester)) {
                this.studentEnrolmentList.remove(i);
            }
        }
        return true;
    }

    // Update the enrolment (add/delete)
    public boolean update(StudentManager sm, CourseManager cm) {
        printAllEnrolment();
        Scanner sc = new Scanner(System.in);

        // Update menu
        while(true) {
            System.out.print("Delete or add new course from the list:\n" +
                            "1. Add\n" +
                            "2. Delete\n" +
                            "3. Exit\n" +
                            "Your choice: ");
            String option = sc.next();
            switch (option) {
                case "1":
                    return add(sm, cm);
                case "2":
                    String sid = validateSidInput(sm);
                    String semester = validateSemesterInput();
                    ArrayList<Course> courseList = getCourseFromStudent(sid, semester);
                    if(courseList.size() > 0) {
                        // Print all course for that student in one semester
                        System.out.println("All courses from student " + sid + " in semester " + semester + ":");
                        printAllCourse(courseList);
                        String cid = validateCidInput(cm);
                        for (Course course : courseList) {
                            if (course.getCid().equals(cid)) {
                                boolean check = delete(sid, cid, semester);
                                System.out.println("Deleted successfully!");
                                printAllEnrolment();
                                return check;
                            }
                        }
                        // If cannot find the course
                        System.out.println("This course is not belong to this semester!");
                        return false;
                    } else {
                        // If there is no course enrolled in this semester
                        System.out.println("There is no courses available to delete!");
                        return false;
                    }
                case "3":
                    return true;
                default:
                    System.out.print("Invalid input. Type it again!\n");
                    break;
            }
        }
    }

    public void printCourseByStudent(StudentManager sm) throws IOException {
        String sid = validateSidInput(sm);
        String semester = validateSemesterInput();

        // Print all courses
        System.out.println("All courses taken from student " + sid + " in semester " + semester + ":");
        ArrayList<Course> courseList = getCourseFromStudent(sid, semester);
        printAllCourse(courseList);

        // Ask to save the file
        if(saveFile()) {
            // Create file name
            String fileName = sid + "_" + semester + ".csv";

            // Create a file
            writeFile(fileName,"",false);

            // Loop through each element and save into a file
            for (int i = 0; i < courseList.size(); i++) {
                writeCourse(courseList.get(i), fileName, true);
                if (i != courseList.size() - 1)
                    writeFile(fileName, "\n", true);
            }
        }
    }

    public void printStudentByCourse(CourseManager cm) throws IOException {
        String cid = validateCidInput(cm);
        String semester = validateSemesterInput();

        // Print all students
        System.out.println("All students taken from course " + cid + " in semester " + semester + ":");
        ArrayList<Student> studentList = getStudentFromCourse(cid, semester);
        printAllStudent(studentList);

        // Ask to save the file
        if(saveFile()) {
            // Create file name
            String fileName = cid + "_" + semester + ".csv";

            // Create a file
            writeFile(fileName,"",false);

            // Loop through each element and save into a file
            for(int i = 0; i < studentList.size(); i++) {
                writeStudent(studentList.get(i), fileName, true);
                if(i != studentList.size() - 1)
                    writeFile(fileName,"\n",true);
            }
        }
    }

    public void printCourseBySemester(CourseManager cm) throws IOException {
        String semester = validateSemesterInput();
        System.out.println("All courses offered in 1 semester:");

        // Print all courses
        ArrayList<Course> courseList = getAllCourseBySemester(cm, semester);
        printAllCourse(courseList);

        // Ask to save the file
        if(saveFile()) {
            // Create file name
            String fileName = semester + ".csv";

            // Create a file
            writeFile(fileName,"",false);

            // Loop through each element and save into a file
            for(int i = 0; i < courseList.size(); i++) {
                writeCourse(courseList.get(i), fileName, true);
                if(i != courseList.size() - 1)
                    writeFile(fileName,"\n",true);
            }
        }
    }

    public void writeCourse(Course course, String fileName, boolean notOverwritten) throws IOException {
        String content = course.getCid() + "," + course.getName() + "," + course.getSemester();
        writeFile(fileName, content, notOverwritten);
    }

    public void writeStudent(Student student, String fileName, boolean notOverwritten) throws IOException {
        Format formatter = new SimpleDateFormat("dd-MM-yyyy");
        String content = student.getSid() + "," + student.getName() + "," + formatter.format(student.getDOB());
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
            if (sid.length() == 8 & sid.substring(1).matches("[0-9]+") & sid.charAt(0) == 's') {
                for(Student student: studentManager.getStudentList()) {
                    if (student.getSid().equals(sid)) {
                        System.out.println();
                        return sid;
                    }
                }
                System.out.println("No student available!\n");
            } else System.out.println("Invalid input. Type it again!\n");
        }
    }

    public String validateCidInput(CourseManager courseManager) {
        printAllCourse(courseManager.getCourseList());
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.print("Course's id: ");
            String cid = sc.next();
            if (cid.length() == 8 &
                cid.substring(0,4).matches("[A-Z]+") &
                cid.substring(4).matches("[0-9]+")
            ){
                for(Course course : courseManager.getCourseList()) {
                    if(course.getCid().equals(cid)) {
                        System.out.println();
                        return cid;
                    }
                }
                System.out.println("No course available!\n");
            } else System.out.println("Invalid input. Type it again!\n");
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
            if (semester.length() == 5 &
                    semester.substring(0,4).matches("[0-9]+") &
                    semesterArray.contains(semester.charAt(4))) {
                System.out.println();
                return semester;
            } else System.out.println("Invalid input. Type it again!\n");
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
        Format formatter = new SimpleDateFormat("dd-MM-yyyy");
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