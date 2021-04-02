import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        // Populate variable
        String sid;
        String cid;
        String semester;
        Scanner sc = new Scanner(System.in);

        // Populate students
        Student s1 = new Student("s3818381", "Vo Tran Truong Duy", "08/11/2001");
        Student s2 = new Student("s3804690", "Nguyen Ngoc Dang Hung", "20/08/2000");
        Student s3 = new Student("s3812649", "Nguyen Pham Quoc Minh", "24/08/2001");
        Student s4 = new Student("s3754105", "Nguyen Dang Lam Phuong", "25/07/2000");
        Student s5 = new Student("s3817852", "Ngo My Quynh", "08/03/2001");
        Student s6 = new Student("s3817852", "Nguyen Thanh Thien", "06/11/2000");
        Student s7 = new Student("s3836455", "Nguyen Thanh Tuan", "14/12/1995");
        Student s8 = new Student("s3748698", "Mai Thi Phuong Xuan", "19/04/1998");
        StudentManager sm = new StudentManager();
        sm.getStudentList().add(s1);
        sm.getStudentList().add(s2);
        sm.getStudentList().add(s3);
        sm.getStudentList().add(s4);
        sm.getStudentList().add(s5);
        sm.getStudentList().add(s6);
        sm.getStudentList().add(s7);
        sm.getStudentList().add(s8);

        // Populate courses
        Course c1 = new Course("COSC2440", "Further Programming", 12);
        Course c2 = new Course("COSC2652", "User-centred Design", 12);
        Course c3 = new Course("ISYS2101", "Software Engineering Project Management", 12);
        CourseManager cm = new CourseManager();
        cm.getCourseList().add(c1);
        cm.getCourseList().add(c2);
        cm.getCourseList().add(c3);

        StudentEnrolmentManager sem = new StudentEnrolmentManager();

        sem.getStudentEnrolmentList().add(new StudentEnrolment(s1, c1, "2021A"));
        sem.getStudentEnrolmentList().add(new StudentEnrolment(s2, c1, "2021A"));
        sem.getStudentEnrolmentList().add(new StudentEnrolment(s3, c1, "2021A"));
        sem.getStudentEnrolmentList().add(new StudentEnrolment(s4, c2, "2021A"));
        sem.getStudentEnrolmentList().add(new StudentEnrolment(s5, c2, "2021A"));
        sem.getStudentEnrolmentList().add(new StudentEnrolment(s5, c2, "2021A"));
        sem.getStudentEnrolmentList().add(new StudentEnrolment(s1, c3, "2021A"));
        sem.getStudentEnrolmentList().add(new StudentEnrolment(s2, c3, "2021A"));
        sem.getStudentEnrolmentList().add(new StudentEnrolment(s3, c3, "2021A"));
        sem.getStudentEnrolmentList().add(new StudentEnrolment(s4, c3, "2021A"));

        // Add enrolment

//        System.out.println(sem.add(sm, cm) ? "Successfully" : "Failed");
//        System.out.println(sem.update(sid, semester, sm, cm) ? "Successfully" : "Failed");
//        System.out.println(sem.getStudentEnrolmentList().toString());

        System.out.print("Get all: \n" +
                "1. Courses by student\n" +
                "2. Students by course\n" +
                "3. Semester\n" +
                "Your choice: ");
        String option = sc.next();
//        if(option.equals("1")) {
//            System.out.print("Type the student's id: ");
//            sid = sc.next();
//            sem.getOne(option, sid);
//        }
//        else if(option.equals("2")) {
//            System.out.print("Type the course's id: ");
//            cid = sc.next();
//            sem.getOne(option, cid);
//        }
//        else if(option.equals("3")) {
//            sem.getOne(option, "");
//        }
//        else
//            System.out.print("Invalid input. Type it again!\n");
        System.out.println(sem.validateSemesterInput());
    }
}
