package by.it_academy.jd2.hw.example.messenger.storage.db.api;

import by.it_academy.jd2.hw.example.messenger.storage.api.IAuditStorage;
import by.it_academy.jd2.hw.example.messenger.storage.api.IChatStorage;
import by.it_academy.jd2.hw.example.messenger.storage.api.IStorageFactory;
import by.it_academy.jd2.hw.example.messenger.storage.api.IUserStorage;
import by.it_academy.jd2.hw.example.messenger.storage.db.DBAuditStorage;
import by.it_academy.jd2.hw.example.messenger.storage.db.DBChatStorage;
import by.it_academy.jd2.hw.example.messenger.storage.db.DBUserStorage;

public class DBStorageFactory implements IStorageFactory {

    private static final IStorageFactory INSTANCE = new DBStorageFactory();

    private DBStorageFactory() {}

    @Override
    public IChatStorage getChatStorage() {
        return DBChatStorage.getInstance();
    }

    @Override
    public IUserStorage getUserStorage() {
        return DBUserStorage.getInstance();
    }

    @Override
    public IAuditStorage getAuditStorage() {
        return DBAuditStorage.getInstance();
    }

    public static IStorageFactory getInstance() {
        return INSTANCE;
    }
}
