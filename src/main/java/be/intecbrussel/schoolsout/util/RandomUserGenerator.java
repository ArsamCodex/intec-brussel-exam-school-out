package be.intecbrussel.schoolsout.util;

import be.intecbrussel.schoolsout.model.*;
import com.github.javafaker.Faker;

import java.util.List;
import java.util.Locale;
import java.util.Random;

public class RandomUserGenerator {
    public static final Faker faker = new Faker(new Locale("en-US"));

    private static User user(final Person person) {
        return User.builder()
                .isActive(true)
                .login(faker.name().username())
                .passwordHash(faker.code().isbn13())
                .person(person)
                .build();
    }

    public static Person person(final Course course) {
        final Person person = Person.builder()
                .familyName(faker.name().lastName())
                .firstName(faker.name().firstName())
                .gender(new Random().nextInt(100) % 2 == 0 ? Gender.FEMALE : Gender.MALE)
                .build();

        person.setCourse(course);
        person.setUser(user(person));
        person.setGrades(grades(person));

        return person;
    }

    private static Person person() {
        return Person.builder()
                .familyName(RandomUserGenerator.faker.name().lastName())
                .firstName(RandomUserGenerator.faker.name().firstName())
                .gender(new Random().nextInt(100) % 2 == 0 ? Gender.FEMALE : Gender.MALE)
                .build();
    }

    private static List<Grade> grades(final Person person) {
        return List.of(
                Grade.builder()
                        .person(person)
                        .score(faker.number().randomDouble(2, 0, 100))
                        .build(),
                Grade.builder()
                        .person(person)
                        .score(faker.number().randomDouble(2, 0, 100))
                        .build()
        );
    }
}
