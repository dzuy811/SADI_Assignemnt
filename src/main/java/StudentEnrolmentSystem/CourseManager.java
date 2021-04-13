package StudentEnrolmentSystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CourseManager {
    private final ArrayList<Course> courseList;

    public CourseManager() {
        this.courseList = new ArrayList<Course>();
    }

    public ArrayList<Course> getCourseList() {
        return courseList;
    }

    // Take the content of csv file to save into ArrayList
    public void readCourseFile(String fileName) throws FileNotFoundException {
        Scanner fileRead = new Scanner(new File(fileName));
        while (fileRead.hasNext()) {
            // String to store the line
            String[] list = fileRead.nextLine().split(",", 4);

            // New course object
            Course course = new Course(list[0], list[1], Integer.parseInt(list[2]), list[3]);

            // Add this object into the array list
            this.courseList.add(course);
        }
        fileRead.close();
    }

    // Check if that course belongs to that semester
    public boolean checkCourseBySemester(String cid, String semester) {
        for(Course course: this.courseList) {
            if(course.getCid().equals(cid) && course.getSemester().equals(semester))
                return true;
        }
        return false;
    }

    // Get file name to read data
    public void getFileNameMenu() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.print("\nWhich file do you want to get the course data?\n" +
                    "1. Default file\n" +
                    "2. Custom file\n" +
                    "Your choice: ");
            String fileOption = sc.next();
            if(fileOption.equals("1")) {
                readCourseFile("courses.csv");
                break;
            }
            else if(fileOption.equals("2")){
                while(true) {
                    try {
                        System.out.print("Type the file name: ");
                        readCourseFile(sc.next());
                        break;
                    } catch(FileNotFoundException e) {
                        System.out.println("No File Found. Type it again!\n");
                    }
                }
                break;
            } else System.out.println("Invalid value. Type it again!\n");
        }
    }
}