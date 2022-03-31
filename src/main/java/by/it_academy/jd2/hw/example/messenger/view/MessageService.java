package by.it_academy.jd2.hw.example.messenger.view;

import by.it_academy.jd2.hw.example.messenger.model.Message;
import by.it_academy.jd2.hw.example.messenger.model.User;
import by.it_academy.jd2.hw.example.messenger.storage.Storage;
import by.it_academy.jd2.hw.example.messenger.storage.db.DBChatStorage;
import by.it_academy.jd2.hw.example.messenger.storage.api.IChatStorage;
import by.it_academy.jd2.hw.example.messenger.storage.hibernate.api.HibernateStorageFactory;
import by.it_academy.jd2.hw.example.messenger.view.api.IMessageService;

import java.util.List;

public class MessageService implements IMessageService {
    private final static MessageService instance = new MessageService();

    private final IChatStorage chatStorage;

    private MessageService() {
        this.chatStorage = Storage.getChatStorage();
    }

    @Override
    public List<Message> get(User currentUser) {
        return this.chatStorage.get(currentUser.getLogin());
    }

    @Override
    public void addMessage(Message message) {
        this.chatStorage.addMessage(message);
    }

    @Override
    public long getCount(User currentUser) {
        return this.chatStorage.get(currentUser.getLogin()).size();
    }

    @Override
    public long getCount() {
        return this.chatStorage.getCount();
    }

    public static MessageService getInstance() {
        return instance;
    }
}
