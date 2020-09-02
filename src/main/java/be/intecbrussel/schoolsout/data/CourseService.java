package be.intecbrussel.schoolsout.data;

import be.intecbrussel.schoolsout.model.Course;
import be.intecbrussel.schoolsout.model.Exam;
import be.intecbrussel.schoolsout.model.Module;
import be.intecbrussel.schoolsout.util.EntityGenerator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class CourseService {
    private static final EntityManagerFactory emf = EntityGenerator.generate(Connections.MySQL_Moktok_Remote);

    public Optional<Course> addCourse(final Course course) {
        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(course);
        em.getTransaction().commit();
        return Optional.of(course);
    }

    public Optional<Module> addModule(final Module module) {
        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(module);
        em.getTransaction().commit();
        return Optional.of(module);
    }

    public Optional<Exam> addExam(final Exam exam) {
        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(exam);
        em.getTransaction().commit();
        return Optional.of(exam);
    }

    public Optional<Course> getCourseById(final Long id) {
        final EntityManager em = emf.createEntityManager();
        final Course foundCourse = em.find(Course.class, id);
        return Optional.of(foundCourse);
    }

    public List<Course> getCourses(final Course exampleCourse) {
        final EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT c FROM Course c " +
                        "WHERE " +
                        "c.code = " + exampleCourse.getCode() +
                        " OR " +
                        "c.name LIKE " + '%' + exampleCourse.getName() + '%'
                , Course.class).getResultList();
    }

    public Optional<Module> getModuleById(final Long id) {
        final EntityManager em = emf.createEntityManager();
        final Module foundModule = em.find(Module.class, id);
        return Optional.of(foundModule);
    }

    public Optional<Exam> getExamById(final Long id) {
        final EntityManager em = emf.createEntityManager();
        final Exam foundExam = em.find(Exam.class, id);
        return Optional.of(foundExam);
    }

    public List<Course> getCourses() {
        final EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT c FROM Course c", Course.class)
                .getResultList();
    }

    public List<Module> getModules(final Long courseId) {
        final EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT m FROM Module m WHERE m.course_id = " + courseId, Module.class)
                .getResultList();
    }

    public List<Module> getModules() {
        final EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT m FROM Module m", Module.class)
                .getResultList();
    }

    public List<Exam> getExams(final Long moduleId) {
        final EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT e FROM Exam e WHERE e.module_id = " + moduleId, Exam.class)
                .getResultList();
    }

    public List<Exam> getExams() {
        final EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT e FROM Exam e", Exam.class)
                .getResultList();
    }

    public Optional<Course> editCourse(final Course course, final Long id) {
        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        final Course foundCourse = em.find(Course.class, id);
        final Course updatedCourse = foundCourse.toBuilder()
                .name(course.getName() != null ? course.getName() : foundCourse.getName())
                .isActive(course.getIsActive() != null ? course.getIsActive() : foundCourse.getIsActive())
                .imageUrl(course.getImageUrl() != null ? course.getImageUrl() : foundCourse.getImageUrl())
                .description(course.getDescription() != null ? course.getDescription() : foundCourse.getDescription())
                .modules(course.getModules() != null && !course.getModules().isEmpty() ? course.getModules() : foundCourse.getModules())
                .code(course.getCode() != null ? course.getCode() : foundCourse.getCode())
                .build();
        em.merge(updatedCourse);
        em.getTransaction().commit();
        return Optional.of(updatedCourse);
    }

    public Optional<Module> editModule(final Module module, final Long id) {
        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        final Module foundModule = em.find(Module.class, id);
        final Module updatedModule = foundModule.toBuilder()
                .name(module.getName() != null ? module.getName() : foundModule.getName())
                .course(module.getCourse() != null ? module.getCourse() : foundModule.getCourse())
                .description(module.getDescription() != null ? module.getDescription() : foundModule.getDescription())
                .id(id)
                .exams(!module.getExams().isEmpty() ? module.getExams() : foundModule.getExams())
                .build();
        em.merge(updatedModule);
        em.getTransaction().commit();
        return Optional.of(updatedModule);
    }

    public Optional<Exam> editExam(final Exam exam, final Long id) {
        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        final Exam foundExam = em.find(Exam.class, id);
        final Exam updatedExam = foundExam.toBuilder()
                .module(exam.getModule() != null ? exam.getModule() : foundExam.getModule())
                .date(exam.getDate() != null && exam.getDate().isAfter(LocalDate.now()) ? exam.getDate() : foundExam.getDate())
                .weight(exam.getWeight() != null ? exam.getWeight() : foundExam.getWeight())
                .total(exam.getTotal() != null ? exam.getTotal() : foundExam.getTotal())
                .name(exam.getName() != null ? exam.getName() : foundExam.getName())
                .description(exam.getDescription() != null ? exam.getDescription() : foundExam.getDescription())
                .id(id)
                .build();
        em.merge(updatedExam);
        em.getTransaction().commit();
        return Optional.of(updatedExam);
    }

    public Optional<Course> removeCourse(final Course course) {
        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(course);
        em.getTransaction().commit();
        return getCourseById(course.getId());
    }

    public Optional<Course> removeCourse(final Long id) {
        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(Course.builder().id(id).build());
        em.getTransaction().commit();
        return getCourseById(id);
    }
}
