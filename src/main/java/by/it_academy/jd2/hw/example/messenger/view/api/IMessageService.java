package by.it_academy.jd2.hw.example.messenger.view.api;

import by.it_academy.jd2.hw.example.messenger.model.Message;
import by.it_academy.jd2.hw.example.messenger.model.User;

import java.util.List;

public interface IMessageService {
    List<Message> get(User currentUser);

    void addMessage(Message message);

    long getCount(User currentUser);
    long getCount();
}
