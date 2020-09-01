package be.intecbrussel.schoolsout.data;

import be.intecbrussel.schoolsout.model.User;
import be.intecbrussel.schoolsout.util.EntityGenerator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Optional;

public class UserService {

    private static final EntityManagerFactory emf = EntityGenerator.generate();

    public Optional<User> add(final User userToAdd) {
        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(userToAdd);
        em.getTransaction().commit();
        return Optional.of(userToAdd);
    }

}
