package by.it_academy.jd2.hw.example.messenger.storage.hibernate;

import by.it_academy.jd2.hw.example.messenger.storage.api.IChatStorage;
import by.it_academy.jd2.hw.example.messenger.storage.api.IFactoryStorage;
import by.it_academy.jd2.hw.example.messenger.storage.api.IUserStorage;

public class HibernateFactoryStorage implements IFactoryStorage {

    @Override
    public IChatStorage getChatStorage() {
        return HibernateDBChatStorage.getInstance();
    }

    @Override
    public IUserStorage getUserStorage() {
        return HibernateDBUserStorage.getInstance();
    }
}
