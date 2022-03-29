package by.it_academy.jd2.hw.example.messenger.storage.api;

public interface IFactoryStorage {

    IChatStorage getChatStorage();

    IUserStorage getUserStorage();

}
