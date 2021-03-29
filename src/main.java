public class main {

    public static void main(String[] args) {
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

        StudentEnrolment se = new StudentEnrolment(s1, c1, "2021A");

        StudentEnrolmentManager sem = new StudentEnrolmentManager();
//        System.out.println(sem.add(sm, cm) ? "Successfully" : "Failed");
        System.out.println(sem.update(sm, cm) ? "Successfully" : "Failed");
        System.out.println(sem.getStudentEnrolmentList().toString());
    }
}
