public class main {
    public static void main(String args[]) {
        System.out.println("Test creating the project");
        System.out.println(1+1);
        Student s1 = new Student("s3818381", "Vo Tran Truong Duy", "08/11/2001");
        System.out.println(s1.getDOB());
        System.out.println(s1.getId());
        Course c1 = new Course("COSC2440", "Further Programming", 12);
        System.out.println(c1.getCid());
        System.out.println(c1.getNumberOfCredits());
        StudentEnrolment SE = new StudentEnrolment();
        SE.getStudentList().add(s1);
        System.out.println(SE.getStudentList().get(0).getId());
    }
}
