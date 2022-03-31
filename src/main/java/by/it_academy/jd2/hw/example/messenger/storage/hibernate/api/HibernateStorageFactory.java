package by.it_academy.jd2.hw.example.messenger.storage.hibernate.api;

import by.it_academy.jd2.hw.example.messenger.storage.api.IAuditStorage;
import by.it_academy.jd2.hw.example.messenger.storage.api.IChatStorage;
import by.it_academy.jd2.hw.example.messenger.storage.api.IStorageFactory;
import by.it_academy.jd2.hw.example.messenger.storage.api.IUserStorage;
import by.it_academy.jd2.hw.example.messenger.storage.hibernate.HnateAuditUserStorage;
import by.it_academy.jd2.hw.example.messenger.storage.hibernate.HnateAuditableUserStorage;
import by.it_academy.jd2.hw.example.messenger.storage.hibernate.HnateChatStorage;
import by.it_academy.jd2.hw.example.messenger.storage.hibernate.HnateUserStorage;

public class HibernateStorageFactory implements IStorageFactory {

    private static final IStorageFactory INSTANCE = new HibernateStorageFactory(true);
    private final boolean isAudited;

    private HibernateStorageFactory(boolean isAudited) {
        this.isAudited = isAudited;
    }

    @Override
    public IChatStorage getChatStorage() {
        return HnateChatStorage.getInstance();
    }

    @Override
    public IUserStorage getUserStorage() {
        return isAudited ? HnateAuditableUserStorage.getInstance() : HnateUserStorage.getInstance();
    }

    @Override
    public IAuditStorage getAuditStorage() {
        return HnateAuditUserStorage.getInstance();
    }

    public static IStorageFactory getInstance() {
        return INSTANCE;
    }
}
