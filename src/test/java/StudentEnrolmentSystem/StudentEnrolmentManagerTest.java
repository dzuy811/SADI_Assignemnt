package StudentEnrolmentSystem;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;


public class StudentEnrolmentManagerTest {

    @Rule
    public final TextFromStandardInputStream systemInMock = TextFromStandardInputStream.emptyStandardInputStream();

    @Before
    public void setUp() {
        System.out.println("\nStart the testing method\n");
    }

    // Test for methods in StudentEnrolmentManager class
    @Test
    public void getAll() throws FileNotFoundException, ParseException {
        StudentManager sm = new StudentManager();
        CourseManager cm = new CourseManager();
        StudentEnrolmentManager sem = new StudentEnrolmentManager();

        sm.readStudentFile("students.csv");
        cm.readCourseFile("courses.csv");
        sem.readStudentEnrolmentFile(sm, cm, "studentEnrolments.csv");

        // Check if the array list contains courses or not
        assertTrue(sem.getAll().size() > 0);
    }

    @Test
    public void getOne() throws FileNotFoundException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        StudentManager sm = new StudentManager();
        CourseManager cm = new CourseManager();
        StudentEnrolmentManager sem = new StudentEnrolmentManager();

        sm.readStudentFile("students.csv");
        cm.readCourseFile("courses.csv");

        Student student = new Student("s3818831", "Vo Tran Truong Duy", sdf.parse("08/11/2001"));
        Course course = new Course("MATH2081", "Mathematics for Computing", 12, "2021B");

        StudentEnrolment studentEnrolment = new StudentEnrolment(student, course, "2021B");
        sem.getAll().add(studentEnrolment);

        assertEquals(studentEnrolment, sem.getOne("s3818831", "MATH2081", "2021B"));
    }

    @Test
    public void semMenu() throws IOException, ParseException {
        StudentManager sm = new StudentManager();
        CourseManager cm = new CourseManager();
        StudentEnrolmentManager sem = new StudentEnrolmentManager();

        sm.readStudentFile("students.csv");
        cm.readCourseFile("courses.csv");
        sem.readStudentEnrolmentFile(sm, cm, "studentEnrolments.csv");

        systemInMock.provideLines("1","1","1","7");
        assertTrue(sem.semMenu(sm, cm));
    }

    @Test
    public void readStudentEnrolmentFile() throws FileNotFoundException, ParseException {
        StudentManager sm = new StudentManager();
        CourseManager cm = new CourseManager();
        StudentEnrolmentManager sem = new StudentEnrolmentManager();

        sm.readStudentFile("students.csv");
        cm.readCourseFile("courses.csv");
        sem.readStudentEnrolmentFile(sm, cm, "studentEnrolments.csv");

        assertTrue(sem.readStudentEnrolmentFile(sm, cm, "studentEnrolments.csv"));
    }

    @Test
    public void getFileNameMenu() throws FileNotFoundException, ParseException {
        StudentManager sm = new StudentManager();
        CourseManager cm = new CourseManager();
        StudentEnrolmentManager sem = new StudentEnrolmentManager();

        sm.readStudentFile("students.csv");
        cm.readCourseFile("courses.csv");

        systemInMock.provideLines("1");
        assertTrue(sem.getFileNameMenu(sm, cm));
    }

    @Test
    public void addEnrolment() throws FileNotFoundException, ParseException {
        StudentManager sm = new StudentManager();
        CourseManager cm = new CourseManager();
        StudentEnrolmentManager sem = new StudentEnrolmentManager();

        sm.readStudentFile("students.csv");
        cm.readCourseFile("courses.csv");
        sem.readStudentEnrolmentFile(sm, cm, "studentEnrolments.csv");

        assertTrue(sem.addEnrolment("s3817852", "MATH2081", "2021B", sm, cm));
    }

    @Test
    public void add() throws FileNotFoundException, ParseException {
        StudentManager sm = new StudentManager();
        CourseManager cm = new CourseManager();
        StudentEnrolmentManager sem = new StudentEnrolmentManager();

        sm.readStudentFile("students.csv");
        cm.readCourseFile("courses.csv");
        sem.readStudentEnrolmentFile(sm, cm, "studentEnrolments.csv");

        systemInMock.provideLines("s3754105","2021A","ISYS2101");
        assertTrue(sem.add(sm, cm));

        systemInMock.provideLines("s3818381","2021A","COSC2440");
        assertFalse(sem.add(sm, cm));
    }

    @Test
    public void delete() throws FileNotFoundException, ParseException {
        StudentManager sm = new StudentManager();
        CourseManager cm = new CourseManager();
        StudentEnrolmentManager sem = new StudentEnrolmentManager();

        sm.readStudentFile("students.csv");
        cm.readCourseFile("courses.csv");
        sem.readStudentEnrolmentFile(sm, cm, "studentEnrolments.csv");

        // Check if the enrolment exists before testing the delete method
        assertNotNull(sem.getOne("s3818381","COSC2440","2021A"));
        sem.delete("s3818381","COSC2440","2021A");

        // Check after deleting
        assertNull(sem.getOne("s3818381","COSC2440","2021A"));
    }

    @Test
    public void updateAndAdd() throws FileNotFoundException, ParseException {
        StudentManager sm = new StudentManager();
        CourseManager cm = new CourseManager();
        StudentEnrolmentManager sem = new StudentEnrolmentManager();

        sm.readStudentFile("students.csv");
        cm.readCourseFile("courses.csv");
        sem.readStudentEnrolmentFile(sm, cm, "studentEnrolments.csv");

        systemInMock.provideLines("1","s3818381","2021B","COSC2638");
        sem.update(sm, cm);
    }

    @Test
    public void updateAndDelete() throws FileNotFoundException, ParseException {
        StudentManager sm = new StudentManager();
        CourseManager cm = new CourseManager();
        StudentEnrolmentManager sem = new StudentEnrolmentManager();

        sm.readStudentFile("students.csv");
        cm.readCourseFile("courses.csv");
        sem.readStudentEnrolmentFile(sm, cm, "studentEnrolments.csv");

        systemInMock.provideLines("2","s3818381","2021C","COSC2130");
        sem.update(sm, cm);
    }

    @Test
    public void printCourseByStudent() throws IOException, ParseException {
        StudentManager sm = new StudentManager();
        CourseManager cm = new CourseManager();
        StudentEnrolmentManager sem = new StudentEnrolmentManager();

        sm.readStudentFile("students.csv");
        cm.readCourseFile("courses.csv");
        sem.readStudentEnrolmentFile(sm, cm, "studentEnrolments.csv");

        systemInMock.provideLines("s3818381","2021A","1");
        sem.printCourseByStudent(sm);

        // Check if the save file exists or not
        File f_1 = new File("s3818381_2021A.csv");
        assertTrue(f_1.exists());

        // No save file and check if the file exists
        systemInMock.provideLines("s3818381","2021B","2");
        sem.printCourseByStudent(sm);
        File f_2 = new File("s3818381_2021B.csv");
        assertFalse(f_2.exists());
    }

    @Test
    public void printStudentByCourse() throws IOException, ParseException {
        StudentManager sm = new StudentManager();
        CourseManager cm = new CourseManager();
        StudentEnrolmentManager sem = new StudentEnrolmentManager();

        sm.readStudentFile("students.csv");
        cm.readCourseFile("courses.csv");
        sem.readStudentEnrolmentFile(sm, cm, "studentEnrolments.csv");

        systemInMock.provideLines("COSC2652","2021A","1");
        sem.printStudentByCourse(cm);

        // Check if the save file exists or not
        File f_1 = new File("COSC2652_2021A.csv");
        assertTrue(f_1.exists());

        // No save file and check if the file exists
        systemInMock.provideLines("COSC2638","2021B","2");
        sem.printStudentByCourse(cm);
        File f_2 = new File("2021B.csv");
        assertFalse(f_2.exists());
    }

    @Test
    public void printCourseBySemester() throws IOException, ParseException {
        StudentManager sm = new StudentManager();
        CourseManager cm = new CourseManager();
        StudentEnrolmentManager sem = new StudentEnrolmentManager();

        sm.readStudentFile("students.csv");
        cm.readCourseFile("courses.csv");
        sem.readStudentEnrolmentFile(sm, cm, "studentEnrolments.csv");

        systemInMock.provideLines("2021A","1");
        sem.printCourseBySemester(cm);

        // Check if the save file exists or not
        File f_1 = new File("2021A.csv");
        assertTrue(f_1.exists());

        // No save file and check if the file exists
        systemInMock.provideLines("2021B","2");
        sem.printCourseBySemester(cm);
        File f_2 = new File("2021B.csv");
        assertFalse(f_2.exists());
    }

    @Test
    public void writeCourse() throws IOException {
        StudentEnrolmentManager sem = new StudentEnrolmentManager();
        Course course = new Course("COSC2440","Further Programming",12,"2021A");

        String fileName = "s3818381_2021A.csv";
        sem.writeCourse(course, fileName, false);

        // Check if the save file exists or not
        File f = new File(fileName);
        assertTrue(f.exists());
    }

    @Test
    public void writeStudent() throws IOException, ParseException {
        StudentEnrolmentManager sem = new StudentEnrolmentManager();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Student student = new Student("s3818381", "Vo Tran Truong Duy", sdf.parse("08/11/2001"));

        String fileName = "COSC2638_2021A.csv";
        sem.writeStudent(student, fileName, false);

        // Check if the save file exists or not
        File f = new File(fileName);
        assertTrue(f.exists());
    }

    @Test
    public void getCourseFromStudent() throws FileNotFoundException, ParseException {
        StudentManager sm = new StudentManager();
        CourseManager cm = new CourseManager();
        StudentEnrolmentManager sem = new StudentEnrolmentManager();

        sm.readStudentFile("students.csv");
        cm.readCourseFile("courses.csv");
        sem.readStudentEnrolmentFile(sm, cm, "studentEnrolments.csv");

        ArrayList<Course> course = sem.getCourseFromStudent("s3818381", "2021A");

        // Check if the array list contains courses or not
        assertTrue(course.size() > 0);
    }

    @Test
    public void getStudentFromCourse() throws FileNotFoundException, ParseException {
        StudentManager sm = new StudentManager();
        CourseManager cm = new CourseManager();
        StudentEnrolmentManager sem = new StudentEnrolmentManager();

        sm.readStudentFile("students.csv");
        cm.readCourseFile("courses.csv");
        sem.readStudentEnrolmentFile(sm, cm, "studentEnrolments.csv");

        ArrayList<Student> student = sem.getStudentFromCourse("COSC2652", "2021A");

        // Check if the array list contains courses or not
        assertTrue(student.size() > 0);
    }

    @Test
    public void getAllCourseBySemester() throws FileNotFoundException, ParseException {
        StudentManager sm = new StudentManager();
        CourseManager cm = new CourseManager();
        StudentEnrolmentManager sem = new StudentEnrolmentManager();

        sm.readStudentFile("students.csv");
        cm.readCourseFile("courses.csv");
        sem.readStudentEnrolmentFile(sm, cm, "studentEnrolments.csv");

        ArrayList<Course> course = sem.getAllCourseBySemester(cm, "2021A");

        // Check if the array list contains courses or not
        assertTrue(course.size() > 0);
    }

    @Test
    public void validateSidInput() throws FileNotFoundException, ParseException {
        StudentManager sm = new StudentManager();
        sm.readStudentFile("students.csv");
        StudentEnrolmentManager sem = new StudentEnrolmentManager();

        systemInMock.provideLines("s3818381");
        sem.validateSidInput(sm);

        systemInMock.provideLines("s312121","abczys","1234","s3818381");
        sem.validateSidInput(sm);
    }

    @Test
    public void validateCidInput() throws FileNotFoundException {
        CourseManager cm = new CourseManager();
        cm.readCourseFile("courses.csv");
        StudentEnrolmentManager sem = new StudentEnrolmentManager();

        systemInMock.provideLines("ISYS2101");
        sem.validateCidInput(cm);

        systemInMock.provideLines("121212","ASCZ","3213SS","COSE2311","ISYS2101");
        sem.validateCidInput(cm);
    }

    @Test
    public void validateSemesterInput() {
        StudentEnrolmentManager sem = new StudentEnrolmentManager();

        systemInMock.provideLines("2021B");
        sem.validateSemesterInput();

        systemInMock.provideLines("1112222","abcawd","2021D","2021B");
        sem.validateSemesterInput();
    }

    @Test
    public void writeFile() throws IOException {
        StudentEnrolmentManager sem = new StudentEnrolmentManager();
        String fileName = "writeFileTest.csv";
        String value = "This is the string in this file";

        sem.writeFile(fileName, value, false);

        // Check if the file exists or not
        File f = new File(fileName);
        assertTrue(f.exists());

        // Check the content inside this file
        Scanner fileRead = new Scanner(new File(fileName));
        String line = fileRead.nextLine();
        assertEquals(value, line);
    }

    @Test
    public void saveFile() {
        StudentEnrolmentManager sem = new StudentEnrolmentManager();

        systemInMock.provideLines("1");
        assertTrue(sem.saveFile());

        systemInMock.provideLines("2");
        assertFalse(sem.saveFile());

        systemInMock.provideLines("3","something","1");
        assertTrue(sem.saveFile());
    }

    @Test
    public void printAllEnrolment() throws FileNotFoundException, ParseException {
        StudentManager sm = new StudentManager();
        CourseManager cm = new CourseManager();
        StudentEnrolmentManager sem = new StudentEnrolmentManager();

        sm.readStudentFile("students.csv");
        cm.readCourseFile("courses.csv");
        sem.readStudentEnrolmentFile(sm, cm, "studentEnrolments.csv");

        sem.printAllEnrolment();
    }

    @Test
    public void printAllStudent() throws FileNotFoundException, ParseException {
        StudentManager sm = new StudentManager();
        StudentEnrolmentManager sem = new StudentEnrolmentManager();
        sm.readStudentFile("students.csv");

        ArrayList<Student> studentList = sm.getStudentList();

        sem.printAllStudent(studentList);
    }

    @Test
    public void printAllCourse() throws FileNotFoundException {
        CourseManager cm = new CourseManager();
        StudentEnrolmentManager sem = new StudentEnrolmentManager();

        cm.readCourseFile("courses.csv");

        ArrayList<Course> courseList = cm.getCourseList();

        sem.printAllCourse(courseList);
    }

    // Test for methods in Student class
    @Test
    public void getSid() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        String sid = "s3818381";
        Student student = new Student(sid, "Vo Tran Truong Duy", sdf.parse("08/11/2001"));

        assertEquals(sid, student.getSid());
    }

    @Test
    public void getNameStudent() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        String name = "Vo Tran Truong Duy";
        Student student = new Student("s3818381", name, sdf.parse("08/11/2001"));

        assertEquals(name, student.getName());
    }

    @Test
    public void getDOB() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Date date = sdf.parse("08/11/2001");
        Student student = new Student("s3818381", "Vo Tran Truong Duy", date);

        assertEquals(date, student.getDOB());
    }

    @Test
    public void setSid() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        String sid = "s3741258";
        Student student = new Student("s3818381", "Vo Tran Truong Duy", sdf.parse("08/11/2001"));

        student.setSid(sid);
        assertEquals(sid, student.getSid());
    }

    @Test
    public void setNameStudent() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        String name = "Ngo My Quynh";
        Student student = new Student("s3818381", "Vo Tran Truong Duy", sdf.parse("08/11/2001"));

        student.setName(name);
        assertEquals(name, student.getName());
    }

    @Test
    public void setDOB() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Date date = sdf.parse("05/11/2001");
        Student student = new Student("s3818381", "Vo Tran Truong Duy", sdf.parse("08/11/2001"));

        student.setDOB(date);
        assertEquals(date, student.getDOB());
    }

    // Test for methods in Course class
    @Test
    public void getCid() {
        String cid = "MATH2081";
        Course course = new Course(cid, "Mathematics for Computing", 12, "2021B");

        assertEquals(cid, course.getCid());
    }

    @Test
    public void getNameCourse() {
        String name = "Mathematics for Computing";
        Course course = new Course("MATH2081", name, 12, "2021B");

        assertEquals(name, course.getName());
    }

    @Test
    public void getNumberOfCredits() {
        int numberOfCredits = 12;
        Course course = new Course("MATH2081", "Mathematics for Computing", numberOfCredits, "2021B");

        assertEquals(numberOfCredits, course.getNumberOfCredits());
    }

    @Test
    public void getSemester() {
        String semester = "2021B";
        Course course = new Course("MATH2081", "Mathematics for Computing", 12, semester);

        assertEquals(semester, course.getSemester());
    }

    @Test
    public void setCid() {
        String cid = "ISYS2101";
        Course course = new Course("MATH2081", "Mathematics for Computing", 12, "2021B");

        course.setCid(cid);
        assertEquals(cid, course.getCid());
    }

    @Test
    public void setNameCourse() {
        String name = "Software Engineering Project Management";
        Course course = new Course("MATH2081", "Mathematics for Computing", 12, "2021B");

        course.setName(name);
        assertEquals(name, course.getName());
    }

    @Test
    public void setNumberOfCredits() {
        int numberOfCredits = 24;
        Course course = new Course("MATH2081", "Mathematics for Computing", 12, "2021B");

        course.setNumberOfCredits(numberOfCredits);
        assertEquals(numberOfCredits, course.getNumberOfCredits());
    }

    @Test
    public void setSemester() {
        String semester = "2021C";
        Course course = new Course("MATH2081", "Mathematics for Computing", 12, "2021B");

        course.setSemester(semester);
        assertEquals(semester, course.getSemester());
    }

    // Test for methods in Student Manager
    @Test
    public void getStudentList() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Student student = new Student("s3818831", "Vo Tran Truong Duy", sdf.parse("08/11/2001"));
        StudentManager sm = new StudentManager();

        sm.getStudentList().add(student);
        assertTrue(sm.getStudentList().size() > 0);
    }

    @Test
    public void readStudentFile() throws FileNotFoundException, ParseException {
        StudentManager sm = new StudentManager();

        sm.readStudentFile("students.csv");

        assertTrue(sm.getStudentList().size() > 0);
    }

    @Test
    public void getFileNameMenuStudentManager() throws FileNotFoundException, ParseException {
        StudentManager sm_1 = new StudentManager();

        systemInMock.provideLines("1");
        sm_1.getFileNameMenu();
        assertTrue(sm_1.getStudentList().size() > 0);

        StudentManager sm_2 = new StudentManager();

        systemInMock.provideLines("3","assad","2","students.csv");
        sm_2.getFileNameMenu();
        assertTrue(sm_2.getStudentList().size() > 0);
    }

    // Test for methods in Course Manager
    @Test
    public void getCourseList() throws ParseException {
        Course course = new Course("MATH2081", "Mathematics for Computing", 12, "2021B");
        CourseManager cm = new CourseManager();

        cm.getCourseList().add(course);
        assertTrue(cm.getCourseList().size() > 0);
    }

    @Test
    public void readCourseFile() throws FileNotFoundException, ParseException {
        CourseManager cm = new CourseManager();

        cm.readCourseFile("courses.csv");

        assertTrue(cm.getCourseList().size() > 0);
    }

    @Test
    public void getFileNameMenuCourseManager() throws FileNotFoundException, ParseException {
        CourseManager cm_1 = new CourseManager();

        systemInMock.provideLines("1");
        cm_1.getFileNameMenu();
        assertTrue(cm_1.getCourseList().size() > 0);

        CourseManager cm_2 = new CourseManager();

        systemInMock.provideLines("3","assad","2","courses.csv");
        cm_2.getFileNameMenu();
        assertTrue(cm_2.getCourseList().size() > 0);
    }

    // Test for methods in Student Enrolment
    @Test
    public void getStudent() throws FileNotFoundException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Student student = new Student("s3818831", "Vo Tran Truong Duy", sdf.parse("08/11/2001"));
        Course course = new Course("MATH2081", "Mathematics for Computing", 12, "2021B");

        StudentEnrolment se = new StudentEnrolment(student, course, "2021B");

        assertEquals(student, se.getStudent());
    }

    @Test
    public void getCourse() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Student student = new Student("s3818831", "Vo Tran Truong Duy", sdf.parse("08/11/2001"));
        Course course = new Course("MATH2081", "Mathematics for Computing", 12, "2021B");

        StudentEnrolment se = new StudentEnrolment(student, course, "2021B");

        assertEquals(course, se.getCourse());
    }

    @Test
    public void getSemesterStudentEnrolment() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Student student = new Student("s3818831", "Vo Tran Truong Duy", sdf.parse("08/11/2001"));
        Course course = new Course("MATH2081", "Mathematics for Computing", 12, "2021B");

        String semester = "2021B";
        StudentEnrolment se = new StudentEnrolment(student, course, semester);

        assertEquals(semester, se.getSemester());
    }

    @Test
    public void setStudent() throws FileNotFoundException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Student student_1 = new Student("s3818831", "Vo Tran Truong Duy", sdf.parse("08/11/2001"));
        Student student_2 = new Student("s3836322", "Ngo My Quynh", sdf.parse("08/03/2001"));

        Course course = new Course("MATH2081", "Mathematics for Computing", 12, "2021B");

        StudentEnrolment se = new StudentEnrolment(student_1, course, "2021B");
        se.setStudent(student_2);
        assertEquals(student_2, se.getStudent());
    }

    @Test
    public void setCourse() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Student student = new Student("s3818831", "Vo Tran Truong Duy", sdf.parse("08/11/2001"));
        Course course_1 = new Course("COSC2440", "Further Programming", 12, "2021A");
        Course course_2 = new Course("COSC2652", "User-centred Design", 12, "2021A");

        StudentEnrolment se = new StudentEnrolment(student, course_1, "2021A");
        se.setCourse(course_2);
        assertEquals(course_2, se.getCourse());
    }

    @Test
    public void setSemesterStudentEnrolment() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Student student = new Student("s3818831", "Vo Tran Truong Duy", sdf.parse("08/11/2001"));
        Course course = new Course("COSC2652", "User-centred Design", 12, "2021A");

        String semester_1 = "2021A";
        String semester_2 = "2021B";

        StudentEnrolment se = new StudentEnrolment(student, course, semester_1);
        se.setSemester(semester_2);
        assertEquals(semester_2, se.getSemester());
    }
}