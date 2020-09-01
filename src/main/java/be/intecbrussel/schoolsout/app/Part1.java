package be.intecbrussel.schoolsout.app;

import be.intecbrussel.schoolsout.data.CourseService;
import be.intecbrussel.schoolsout.model.Course;
import be.intecbrussel.schoolsout.model.Exam;
import be.intecbrussel.schoolsout.model.Module;
import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Locale;

public class Part1 {

    private static final CourseService courseService = new CourseService();

    public static void main(String[] args) {
        Faker faker = new Faker(new Locale("en-US"));
        courseService.add(Course.builder()
                .code("MAT007")
                .name("ALGO007001")
                .description("Algorithms course")
                .imageUrl("https://moktok.be/algo01.png")
                .isActive(true)
                .modules(Arrays.asList(
                        Module.builder()
                                .description("Computing Algorithms")
                                .name("COMPALGO001")
                                .exams(Arrays.asList(
                                        Exam.builder()
                                                .description("Exam for computing algorithms session 01")
                                                .name("EXAM001COMPALGO001")
                                                .total(100)
                                                .weight(65)
                                                .date(LocalDate.now())
                                                .build()
                                ))
                                .build()
                ))
                .build());

    }

}
