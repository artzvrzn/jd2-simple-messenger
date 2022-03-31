package by.it_academy.jd2.hw.example.messenger.storage.api;

public interface IStorageFactory {

    IChatStorage getChatStorage();

    IUserStorage getUserStorage();

    IAuditStorage getAuditStorage();

}
