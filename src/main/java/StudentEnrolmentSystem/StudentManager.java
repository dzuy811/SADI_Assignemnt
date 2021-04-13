package StudentEnrolmentSystem;

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
        return this.studentList;
    }

    // Take the content of csv file to save into ArrayList
    public void readStudentFile(String fileName) throws FileNotFoundException, ParseException {
        Scanner fileRead = new Scanner(new File(fileName));
        while (fileRead.hasNext()) {
            // String to store the line
            String[] list = fileRead.nextLine().split(",", 3);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = sdf.parse(list[2]);

            // New student object
            Student student = new Student(list[0], list[1], date);

            // Add this object into the array list
            this.studentList.add(student);
        }
        fileRead.close();
    }

    // Get file name to read data
    public void getFileNameMenu() throws FileNotFoundException, ParseException {
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.print("Which file do you want to get the student data?\n" +
                    "1. Default file\n" +
                    "2. Custom file\n" +
                    "Your choice: ");
            String fileOption = sc.next();
            if(fileOption.equals("1")) {
                readStudentFile("students.csv");
                break;
            }
            else if(fileOption.equals("2")){
                while(true) {
                    try{
                        System.out.print("Type the file name: ");
                        readStudentFile(sc.next());
                        break;
                    } catch(FileNotFoundException e) {
                        System.out.println("No File Found. Type it again!\n");
                    }
                }
                break;
            } else System.out.println("Invalid value. Type it again\n");
        }
    }
}