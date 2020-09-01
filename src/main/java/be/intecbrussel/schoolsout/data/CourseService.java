package be.intecbrussel.schoolsout.data;

import be.intecbrussel.schoolsout.model.Course;
import be.intecbrussel.schoolsout.util.EntityGenerator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Optional;

public class CourseService {
    private static final EntityManagerFactory emf = EntityGenerator.generate();

    public Optional<Course> add(final Course courseToAdd) {
        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(courseToAdd);
        em.getTransaction().commit();
        return Optional.of(courseToAdd);
    }
}
