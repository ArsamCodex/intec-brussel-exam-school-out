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

public class Sandbox {

    private static final CourseService courseService = new CourseService();
    private static final Faker faker = new Faker(new Locale("en-US"));

    public static void main(String[] args) {
        final Optional<Course> oCourse = courseService.addCourse(course());
        oCourse.ifPresentOrElse(course -> out.println("The course is registered."), () -> err.println("Error: course could NOT be saved.."));
    }

    private static Course course() {
        return Course.builder()
                .code(faker.code().isbn13())
                .name(faker.company().profession())
                .description(faker.lorem().sentence())
                .imageUrl(faker.file().fileName().concat(".jpg"))
                .isActive(true)
//                .modules(List.of(module(), module()))
                .build();
    }

    private static Module module() {
        return Module.builder()
                .description(faker.lorem().paragraph())
                .name(faker.company().buzzword())
                .exams(List.of(exam(), exam()))
                .build();
    }

    private static Module module(final Course course) {
        return Module.builder()
                .course(course)
                .description(faker.lorem().paragraph())
                .name(faker.company().buzzword())
                .exams(List.of(exam(), exam()))
                .build();
    }

    private static Exam exam() {
        return Exam.builder()
                .description(faker.lorem().paragraph())
                .name("Exam for ".concat(faker.company().buzzword()))
                .total(faker.number().numberBetween(70, 100))
                .weight(faker.number().numberBetween(25, 100))
                .date(LocalDate.now())
                .build();
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
