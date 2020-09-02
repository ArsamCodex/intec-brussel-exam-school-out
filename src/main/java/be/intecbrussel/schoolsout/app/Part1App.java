package be.intecbrussel.schoolsout.app;

import be.intecbrussel.schoolsout.data.CourseService;
import be.intecbrussel.schoolsout.data.UserService;
import be.intecbrussel.schoolsout.model.Course;
import be.intecbrussel.schoolsout.model.Person;
import be.intecbrussel.schoolsout.util.RandomCourseGenerator;
import be.intecbrussel.schoolsout.util.RandomUserGenerator;

import java.util.Optional;
import java.util.stream.IntStream;

import static java.lang.System.err;
import static java.lang.System.out;

public class Part1App {

    private static final CourseService courseService = new CourseService();
    private static final UserService userService = new UserService();

    public static void main(String[] args) {

        IntStream.range(0, 10)
                .forEach(value -> {
                    final Course course = RandomCourseGenerator.course();
                    final Person person = RandomUserGenerator.person(course);
                    addCourseToDB(course);
                    addPersonToDB(person);
                });

        final Course courseMath = Course.builder()
                .code("MATH007")
                .description("Mathematics course for starters.")
                .imageUrl("https://moktok.com/math/007/photo01.jpg")
                .isActive(true)
                .name("Introduction to Mathematics")
                .build();
        addCourseToDB(courseMath);

        final Long courseMathId = courseMath.getId();

        courseService.getCourses().forEach(out::println);
        final Optional<Course> oUpdatedCourse = courseService.editCourse(courseMath.toBuilder()
                .name("Math in Computer Sciences")
                .build(), courseMathId);
        oUpdatedCourse.ifPresentOrElse(out::println,
                () -> err.println("Error: The course could with ID " + courseMathId + " could NOT be updated.."));

    }

    private static void addCourseToDB(final Course courseToAdd) {
        final Optional<Course> oCourse = courseService.addCourse(courseToAdd);
        oCourse.ifPresentOrElse(
                course -> out.println("The course is registered..!"),
                () -> err.println("Error: course could NOT be saved.."));
    }

    private static void addPersonToDB(final Person personToAdd) {
        final Optional<Person> oPerson = userService.addPerson(personToAdd, personToAdd.getUser());
        oPerson.ifPresentOrElse(person -> {
            out.println("The person is registered..!");
        }, () -> err.println("Error: person could NOT be saved.."));
    }

}
