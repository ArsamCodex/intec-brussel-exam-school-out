package be.intecbrussel.schoolsout.util;

import be.intecbrussel.schoolsout.model.Exam;
import be.intecbrussel.schoolsout.model.Module;
import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.util.Locale;

public class RandomExamGenerator {

    private static final Faker faker = new Faker(new Locale("en-US"));

    public static Exam exam(final Module module, final Exam group) {
        return Exam.builder()
                .module(module)
                .description(faker.lorem().paragraph())
                .name("Exam for ".concat(faker.company().buzzword()))
                .total(faker.number().numberBetween(70, 100))
                .weight(faker.number().randomDouble(2, 25, 100))
                .date(LocalDate.now())
                .examGroup(group)
                .build();
    }

    public static Exam exam(final Module module) {
        return Exam.builder()
                .module(module)
                .description(faker.lorem().paragraph())
                .name("Exam for ".concat(faker.company().buzzword()))
                .total(faker.number().numberBetween(70, 100))
                .weight(faker.number().randomDouble(2, 25, 100))
                .date(LocalDate.now())
                .build();
    }
}
