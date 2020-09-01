package be.intecbrussel.schoolsout.util;

import be.intecbrussel.schoolsout.data.Connections;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityGenerator {

    public static EntityManagerFactory generate(final Connections conn) {
        return Persistence.createEntityManagerFactory(conn.getValue());
    }
}
