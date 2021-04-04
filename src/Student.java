import java.util.Date;

public class Student {
    private String sid;
    private String name;
    private Date DOB; // Date of Birth

    public Student(String id, String name, Date DOB) {
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

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    @Override
    public String toString() {
        return "Student{" +
                "sid='" + sid + '\'' +
                ", name='" + name + '\'' +
                ", DOB=" + DOB +
                '}';
    }
}
