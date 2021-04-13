package StudentEnrolmentSystem;

public class Course {
    private String cid;
    private String name;
    private int numberOfCredits;
    private String semester;

    public Course(String cid, String name, int numberOfCredits, String semester) {
        this.cid = cid;
        this.name = name;
        this.numberOfCredits = numberOfCredits;
        this.semester = semester;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfCredits() {
        return numberOfCredits;
    }

    public void setNumberOfCredits(int numberOfCredits) {
        this.numberOfCredits = numberOfCredits;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
