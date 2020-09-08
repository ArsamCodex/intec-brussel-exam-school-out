package be.intecbrussel.schoolsout.service;

import be.intecbrussel.schoolsout.data.CourseRepository;
import be.intecbrussel.schoolsout.model.Course;
import be.intecbrussel.schoolsout.util.KeyboardReader;
import be.intecbrussel.schoolsout.util.TablePrinter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.lang.System.err;
import static java.lang.System.out;

public class CourseService {

    private final CourseRepository courseRepo;

    public CourseService() {
        this.courseRepo = new CourseRepository();
    }

    public CourseService(CourseRepository courseRepo) {
        this.courseRepo = courseRepo;
    }

    public void addCourseToDB(final Course courseToAdd) {
        final Optional<Course> oCourse = courseRepo.addCourse(courseToAdd);
        oCourse.ifPresentOrElse(
                course -> out.println("The course is registered..!"),
                () -> err.println("Error: course could NOT be saved.."));
    }

    public void getAllCourse() {
        final List<Course> courseList = courseRepo.getCourses();
        out.println("Course list is below: ");
        TablePrinter.printCourseTable(courseList);
    }

    public void getAllCourseAsPages() {
        boolean exit = false;
        int pageNo;
        do {
            out.print("Page no: ");
            pageNo = KeyboardReader.keyboard.nextInt();
            if (pageNo <= 0) {
                exit = true;
            } else {
                final List<Course> courseList = courseRepo.getCourses(pageNo, 10);
                out.println("Course list for page " + pageNo + " is below: ");
                TablePrinter.printCourseTable(courseList);
            }
        }
        while (!exit);
    }

    public void addOneCourse() {
        final String courseName = KeyboardReader.nextStringForced("Course Name: ");
        final String courseDescription = KeyboardReader.nextStringForced("Course Description: ");
        final String courseCode = KeyboardReader.nextString("Course Code: ");
        final String courseImage = KeyboardReader.nextString("Course Image URL: ");

        Course c = Course.builder()
                .name(courseName)
                .description(courseDescription)
                .code(courseCode)
                .imageUrl(courseImage)
                .isActive(true)
                .build();

        addCourseToDB(c);
        out.println("The course is added with ID " + c.getId());
    }

    public void getCourseById() {
        final Long id = Long.parseLong(KeyboardReader.nextStringForced("Course ID: "));
        final Optional<Course> oCourse = courseRepo.getCourseById(id);
        oCourse.ifPresentOrElse(course -> TablePrinter.printCourseTable(Collections.singletonList(course)),
                () -> err.println("Error: The course is NOT found.."));
    }

    public void removeOneCourse() {
        final Long id = Long.parseLong(KeyboardReader.nextStringForced("Course ID: "));
        final Optional<Course> oCourse = courseRepo.removeCourse(id);
        oCourse.ifPresentOrElse(course -> err.println("Error: The course " + course.getName() + " is deleted.."),
                () -> out.println("The course with ID " + id + " is NOT found, therefore NOT deleted.."));
    }

    public void editOneCourse() {
        final Long id = Long.parseLong(KeyboardReader.nextStringForced("Course ID: "));
        final String name = KeyboardReader.nextStringForced("Name: ");
        final String description = KeyboardReader.nextString("Description: ");
        final String code = KeyboardReader.nextString("Code: ");
        final String imageUrl = KeyboardReader.nextString("Image URL: ");

        final Optional<Course> oUpdatedCourse = courseRepo.editCourse(Course.builder()
                .name(name)
                .description(description)
                .code(code)
                .isActive(true)
                .imageUrl(imageUrl)
                .build(), id);
        oUpdatedCourse.ifPresentOrElse(course -> TablePrinter.printCourseTable(Collections.singletonList(course)),
                () -> err.println("Error: The course could with ID " + id + " could NOT be updated.."));
    }


}
