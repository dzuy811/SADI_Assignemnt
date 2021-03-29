public class Student {
    private String id;
    private String name;
    private String DOB; // Date of Birth

    public Student(String id, String name, String DOB) {
        this.id = id;
        this.name = name;
        this.DOB = DOB;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
