public class Course {
    private String cid;
    private String name;
    private int numberOfCredits;

    public Course(String cid, String name, int numberOfCredits) {
        this.cid = cid;
        this.name = name;
        this.numberOfCredits = numberOfCredits;
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

    @Override
    public String toString() {
        return "Course{" +
                "cid='" + cid + '\'' +
                ", name='" + name + '\'' +
                ", numberOfCredits=" + numberOfCredits +
                '}';
    }
}
