package be.intecbrussel.schoolsout.data;

import be.intecbrussel.schoolsout.model.Course;
import be.intecbrussel.schoolsout.model.Exam;
import be.intecbrussel.schoolsout.model.Module;
import be.intecbrussel.schoolsout.model.Page;
import be.intecbrussel.schoolsout.util.EntityGenerator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class CourseRepository {

    private static final EntityManagerFactory emf = EntityGenerator.generate(Connections.MySQL_Moktok_Remote);
    private static final String SELECT_FROM_COURSE = "SELECT c FROM Course c";

    public List<Course> getCourses() {
        final EntityManager em = emf.createEntityManager();
        return em.createQuery(SELECT_FROM_COURSE, Course.class)
                .getResultList();
    }

    public List<Course> getCourses(final Integer pageNo, final Integer resultsPerPage) {
        return Page.of(getCourses(), pageNo, resultsPerPage);
    }

    public List<Course> getCourses(final Course exampleCourse) {
        final EntityManager em = emf.createEntityManager();
        return em.createQuery(SELECT_FROM_COURSE +
                        " WHERE " +
                        "c.code = " + exampleCourse.getCode() +
                        " OR " +
                        "c.name LIKE " + '%' + exampleCourse.getName() + '%'
                , Course.class).getResultList();
    }

    public Optional<Course> getCourseById(final Long id) {
        final EntityManager em = emf.createEntityManager();
        final Course foundCourse = em.find(Course.class, id);
        return Optional.of(foundCourse);
    }

    public Optional<Course> getCourseByNameOrCode(final String name, final String code) {
        final EntityManager em = emf.createEntityManager();
        return em.createQuery(
                SELECT_FROM_COURSE +
                        " WHERE c.name = :name OR" +
                        " c.code = :code", Course.class)
                .setParameter("code", name)
                .setParameter("name", code)
                .getResultList()
                .stream()
                .filter(c -> c.getIsActive().equals(Boolean.TRUE))
                .findFirst();
    }

    public Optional<Course> addCourse(final Course course) {
        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(course);
        em.getTransaction().commit();
        return Optional.of(course);
    }

    public Optional<Course> addCourse(final Course course, final List<Module> modules) {
        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        modules.forEach(course::addModule);
        em.persist(course);
        em.getTransaction().commit();
        return Optional.of(course);
    }

    public Optional<Course> editCourse(final Course course, final Long id) {
        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        final Course foundCourse = em.find(Course.class, id);
        final Course updatedCourse = foundCourse.toBuilder()
                .name(course.getName() != null || !course.getName().equalsIgnoreCase("") ? course.getName() : foundCourse.getName())
                .isActive(course.getIsActive() != null ? course.getIsActive() : foundCourse.getIsActive())
                .imageUrl(course.getImageUrl() != null || !course.getImageUrl().equalsIgnoreCase("") ? course.getImageUrl() : foundCourse.getImageUrl())
                .description(course.getDescription() != null || !course.getDescription().equalsIgnoreCase("") ? course.getDescription() : foundCourse.getDescription())
                .modules(course.getModules() != null && !course.getModules().isEmpty() ? course.getModules() : foundCourse.getModules())
                .code(course.getCode() != null || !course.getCode().equalsIgnoreCase("") ? course.getCode() : foundCourse.getCode())
                .build();
        em.merge(updatedCourse);
        em.getTransaction().commit();
        return Optional.of(updatedCourse);
    }

    public Optional<Course> removeCourse(final Course course) {
        final EntityManager em = emf.createEntityManager();
        final Optional<Course> oCourse = em.createQuery(
                "SELECT c FROM Course c " +
                        "WHERE c.name = :name AND" +
                        " c.code = :code", Course.class)
                .setParameter("code", course.getCode())
                .setParameter("name", course.getName())
                .getResultList()
                .stream()
                .filter(c -> c.getIsActive().equals(Boolean.TRUE))
                .findFirst();
        if (oCourse.isPresent()) {
            em.getTransaction().begin();
            em.remove(course);
            em.getTransaction().commit();
            return oCourse;
        }

        return Optional.empty();
    }

    public Optional<Course> removeCourse(final Long id) {
        final EntityManager em = emf.createEntityManager();
        final Optional<Course> oCourse = getCourseById(id);
        if (oCourse.isPresent()) {
            em.getTransaction().begin();
            em.remove(oCourse.get());
            em.getTransaction().commit();
            return oCourse;
        }

        return Optional.empty();
    }

    public Optional<Module> addModule(final Module module) {
        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(module);
        em.getTransaction().commit();
        return Optional.of(module);
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

    public List<Module> getModules(final Long courseId) {
        final EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT m FROM Module m WHERE m.course.id = :courseId", Module.class)
                .setParameter("courseId", courseId)
                .getResultList();
    }

    public List<Module> getModules(final Long courseId, final Integer pageNo, final Integer resultsPerPage) {
        return Page.of(getModules(courseId), pageNo, resultsPerPage);
    }

    public List<Module> getModules(final Integer pageNo, final Integer resultsPerPage) {
        return Page.of(getModules(), pageNo, resultsPerPage);
    }

    public List<Module> getModules() {
        final EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT m FROM Module m", Module.class)
                .getResultList();
    }

    public Optional<Exam> addExam(final Exam exam) {
        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(exam);
        em.getTransaction().commit();
        return Optional.of(exam);
    }

    public Optional<Exam> addExam(final Exam exam, final List<Exam> subExams) {
        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        subExams.forEach(exam::addSubExam);
        em.persist(exam);
        em.getTransaction().commit();
        return Optional.of(exam);
    }

    public List<Exam> getExams(final Long moduleId) {
        final EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT e FROM Exam e WHERE e.module.id = :moduleId", Exam.class)
                .setParameter("moduleId", moduleId)
                .getResultList();
    }

    public List<Exam> getExams(final Long moduleId, final Integer pageNo, final Integer resultsPerPage) {
        return Page.of(getExams(moduleId), pageNo, resultsPerPage);
    }

    public List<Exam> getExams() {
        final EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT e FROM Exam e", Exam.class)
                .getResultList();
    }

    public List<Exam> getExams(final Integer pageNo, final Integer resultsPerPage) {
        return Page.of(getExams(), pageNo, resultsPerPage);
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

    public Optional<Module> removeModule(final Long id) {
        final EntityManager em = emf.createEntityManager();
        final Optional<Module> oModule = getModuleById(id);
        if (oModule.isPresent()) {
            em.getTransaction().begin();
            em.remove(oModule.get());
            em.getTransaction().commit();
            return oModule;
        }
        return Optional.empty();
    }

    public Optional<Module> removeModule(final Module module) {
        final EntityManager em = emf.createEntityManager();
        final Optional<Module> oModule = em.createQuery("" +
                "SELECT m FROM Module m " +
                "WHERE m.name = :name AND m.course.name = :course", Module.class)
                .setParameter("name", module.getName())
                .setParameter("course", module.getCourse().getName())
                .getResultList()
                .stream()
                .findFirst();
        if (oModule.isPresent()) {
            em.getTransaction().begin();
            em.remove(module);
            em.getTransaction().commit();
            return oModule;
        }
        return Optional.empty();
    }

    public Optional<Exam> removeExam(final Exam exam) {
        final EntityManager em = emf.createEntityManager();
        final Optional<Exam> oExam = em.createQuery("" +
                "SELECT e FROM Exam e " +
                "WHERE e.name = :name " +
                "AND e.module.name = :module", Exam.class)
                .setParameter("name", exam.getName())
                .setParameter("module", exam.getModule().getName())
                .getResultList()
                .stream()
                .findFirst();
        if (oExam.isPresent()) {
            em.getTransaction().begin();
            em.remove(oExam.get());
            em.getTransaction().commit();
            return oExam;
        }

        return Optional.empty();
    }

    public Optional<Exam> removeExam(final Long id) {
        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(Module.builder().id(id).build());
        em.getTransaction().commit();
        return getExamById(id);
    }
}







