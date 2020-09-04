package be.intecbrussel.schoolsout.util;

import be.intecbrussel.schoolsout.model.Course;
import be.intecbrussel.schoolsout.model.Module;
import com.github.javafaker.Faker;

import java.util.Locale;

public class RandomModuleGenerator {

    private static final Faker faker = new Faker(new Locale("en-US"));

    public static Module module(final Course course) {
        final Module module = Module.builder()
                .course(course)
                .description(faker.lorem().paragraph())
                .name(faker.company().buzzword())
                .build();
        return module;
    }
}
