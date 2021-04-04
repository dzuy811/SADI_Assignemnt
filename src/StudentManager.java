import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class StudentManager {
    private final ArrayList<Student> studentList;

    public StudentManager() {
        this.studentList = new ArrayList<Student>();
    }

    public ArrayList<Student> getStudentList() {
        return studentList;
    }

    // Take the content of csv file to save into ArrayList
    public void readStudentFile() throws FileNotFoundException, ParseException {
        Scanner fileRead = new Scanner(new File("students.csv"));
        while (fileRead.hasNext()) {
            String[] list = fileRead.nextLine().split(",", 3);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = sdf.parse(list[2]);
            Student student = new Student(list[0], list[1], date);
            studentList.add(student);
        }
        fileRead.close();
    }
}
