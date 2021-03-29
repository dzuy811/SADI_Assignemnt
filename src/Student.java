public class Student {
    private String sid;
    private String name;
    private String DOB; // Date of Birth

    public Student(String id, String name, String DOB) {
        this.sid = id;
        this.name = name;
        this.DOB = DOB;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String id) {
        this.sid = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    @Override
    public String toString() {
        return "Student{" +
                "sid='" + sid + '\'' +
                ", name='" + name + '\'' +
                ", DOB='" + DOB + '\'' +
                '}';
    }
}
