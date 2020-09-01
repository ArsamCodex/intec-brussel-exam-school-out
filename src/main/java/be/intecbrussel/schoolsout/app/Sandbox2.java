package be.intecbrussel.schoolsout.app;

import be.intecbrussel.schoolsout.data.CourseService;
import be.intecbrussel.schoolsout.model.Course;
import be.intecbrussel.schoolsout.model.Exam;
import be.intecbrussel.schoolsout.model.Module;
import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static java.lang.System.err;
import static java.lang.System.out;

public class Sandbox2 {

    private static final CourseService courseService = new CourseService();
    private static final Faker faker = new Faker(new Locale("en-US"));

    public static void main(String[] args) {
        addCourseToDB(course());
    }

    private static void addCourseToDB(final Course courseToAdd) {
        final Optional<Course> oCourse = courseService.addCourse(courseToAdd);
        oCourse.ifPresentOrElse(
                course -> out.println("The course is registered."),
                () -> err.println("Error: course could NOT be saved.."));
    }

    private static Course course() {
        final Course course = Course.builder()
                .code(faker.code().isbn13())
                .name(faker.company().profession())
                .description(faker.lorem().sentence())
                .imageUrl(faker.file().fileName().concat(".jpg"))
                .isActive(true)
                .build();

        course.setModules(List.of(module(course), module(course)));
        return course;
    }

    private static Module module(final Course course) {
        final Module module = Module.builder()
                .course(course)
                .description(faker.lorem().paragraph())
                .name(faker.company().buzzword())
                .build();

        module.setExams(List.of(exam(module), exam(module), exam(module)));
        return module;
    }

    private static Exam exam(final Module module) {
        return Exam.builder()
                .module(module)
                .description(faker.lorem().paragraph())
                .name("Exam for ".concat(faker.company().buzzword()))
                .total(faker.number().numberBetween(70, 100))
                .weight(faker.number().numberBetween(25, 100))
                .date(LocalDate.now())
                .build();
    }

}
