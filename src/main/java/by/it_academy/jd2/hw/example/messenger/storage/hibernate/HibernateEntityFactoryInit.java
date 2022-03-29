package by.it_academy.jd2.hw.example.messenger.storage.hibernate;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateEntityFactoryInit {

    private static final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("HibernateExample");

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public static void closeEntityFactory() {
        entityManagerFactory.close();
    }
}
