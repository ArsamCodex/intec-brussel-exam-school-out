package be.intecbrussel.schoolsout.data;

import be.intecbrussel.schoolsout.model.*;
import be.intecbrussel.schoolsout.model.User;
import be.intecbrussel.schoolsout.util.EntityGenerator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

public class UserRepository {

    private static final EntityManagerFactory emf = EntityGenerator.generate(Connections.MySQL_Moktok_Remote);

    public Optional<User> addUser(final User user) {
        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        return Optional.of(user);
    }

    public Optional<Person> addPerson(final Person person, final User user) {
        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(person);
        em.persist(user);
        em.getTransaction().commit();
        return Optional.of(person);
    }

    public List<User> getUsers() {
        final EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT u FROM User u", User.class)
                .getResultList();
    }

    public List<User> getUsers(final Integer pageNo, final Integer resultsPerPage) {
        return Page.of(getUsers(), pageNo, resultsPerPage);
    }

    public List<Person> getPeople() {
        final EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT p FROM Person p", Person.class)
                .getResultList();
    }

    public List<Person> getPeople(final Integer pageNo, final Integer resultsPerPage) {
        return Page.of(getPeople(), pageNo, resultsPerPage);
    }

    public Optional<User> getUserById(final String login) {
        final EntityManager em = emf.createEntityManager();
        final User foundPerson = em.find(User.class, login);
        return Optional.of(foundPerson);
    }

    public Optional<Person> getPersonById(final Long id) {
        final EntityManager em = emf.createEntityManager();
        final Person foundPerson = em.find(Person.class, id);
        return Optional.of(foundPerson);
    }

    public Optional<User> editUser(final User user, final String login) {
        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        final User foundUser = em.find(User.class, login);
        final User updatedUser = foundUser.toBuilder()
                .login(user.getLogin() != null ? user.getLogin() : foundUser.getLogin())
                .passwordHash(user.getPasswordHash() != null ? user.getPasswordHash() : foundUser.getPasswordHash())
                .isActive(true)
                .person(user.getPerson() != null ? user.getPerson() : foundUser.getPerson())
                .build();
        em.merge(updatedUser);
        em.getTransaction().commit();
        return Optional.of(updatedUser);
    }

    public Optional<Person> editUser(final Person person, final String login) {
        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        final Person foundPerson = em.find(Person.class, login);
        final Person updatedUser = foundPerson.toBuilder()
                .familyName(!person.getFamilyName().isEmpty() ? person.getFamilyName() : foundPerson.getFamilyName())
                .firstName(!person.getFirstName().isEmpty() ? person.getFirstName() : foundPerson.getFirstName())
                .gender(person.getGender() != null ? person.getGender() : foundPerson.getGender())
                .course(person.getCourse() != null ? person.getCourse() : foundPerson.getCourse())
                .grades(!person.getGrades().isEmpty() ? person.getGrades() : foundPerson.getGrades())
                .build();
        em.merge(updatedUser);
        em.getTransaction().commit();
        return Optional.of(updatedUser);
    }

    public Optional<User> removeUser(final User user) {
        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(user);
        em.getTransaction().commit();
        return getUserById(user.getLogin());
    }

    public Optional<User> removeUser(final String login) {
        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(User.builder().login(login).build());
        em.getTransaction().commit();
        return getUserById(login);
    }

    public Optional<Person> removePerson(final Person person) {
        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(person);
        em.getTransaction().commit();
        return getPersonById(person.getId());
    }

    public Optional<Person> removePerson(final Long id) {
        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(Person.builder().id(id).build());
        em.getTransaction().commit();
        return getPersonById(id);
    }
}
