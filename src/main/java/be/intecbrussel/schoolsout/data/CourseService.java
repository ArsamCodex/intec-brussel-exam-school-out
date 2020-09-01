package be.intecbrussel.schoolsout.data;

import be.intecbrussel.schoolsout.model.Course;
import be.intecbrussel.schoolsout.model.Exam;
import be.intecbrussel.schoolsout.model.Module;
import be.intecbrussel.schoolsout.util.EntityGenerator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Optional;

public class CourseService {
    private static final EntityManagerFactory emf = EntityGenerator.generate();

    public Optional<Course> addCourse(final Course course) {
        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(course);
        em.getTransaction().commit();
        return Optional.of(course);
    }

    public Optional<Module> addModule(final Module module){
        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(module);
        em.getTransaction().commit();
        return Optional.of(module);
    }

    public Optional<Exam> addExam(final Exam exam){
        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(exam);
        em.getTransaction().commit();
        return Optional.of(exam);
    }
}
