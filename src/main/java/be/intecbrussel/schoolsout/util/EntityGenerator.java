package be.intecbrussel.schoolsout.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityGenerator {
    public static EntityManagerFactory generate(){
        return Persistence.createEntityManagerFactory("datasource");
    }
}
