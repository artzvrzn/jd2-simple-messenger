package by.it_academy.jd2.hw.example.messenger.storage;

import by.it_academy.jd2.hw.example.messenger.storage.api.IAuditStorage;
import by.it_academy.jd2.hw.example.messenger.storage.api.IChatStorage;
import by.it_academy.jd2.hw.example.messenger.storage.api.IStorageFactory;
import by.it_academy.jd2.hw.example.messenger.storage.api.IUserStorage;
import by.it_academy.jd2.hw.example.messenger.storage.hibernate.api.HibernateStorageFactory;

public abstract class Storage {

    private static final IStorageFactory FACTORY = HibernateStorageFactory.getInstance();

    public static IChatStorage getChatStorage() {
        return FACTORY.getChatStorage();
    }

    public static IUserStorage getUserStorage() {
        return FACTORY.getUserStorage();
    }

    public static IAuditStorage getAuditStorage() {
        return FACTORY.getAuditStorage();
    }
}
