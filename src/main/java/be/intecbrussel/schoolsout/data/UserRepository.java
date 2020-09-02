package be.intecbrussel.schoolsout.data;

import be.intecbrussel.schoolsout.model.Page;
import be.intecbrussel.schoolsout.model.Person;
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
}
