package be.intecbrussel.schoolsout.data;

import be.intecbrussel.schoolsout.model.Person;
import be.intecbrussel.schoolsout.model.User;
import be.intecbrussel.schoolsout.util.EntityGenerator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Optional;

public class UserService {

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

}
