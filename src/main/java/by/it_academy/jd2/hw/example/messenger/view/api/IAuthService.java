package by.it_academy.jd2.hw.example.messenger.view.api;

import by.it_academy.jd2.hw.example.messenger.model.User;

public interface IAuthService {
    User authentication(String login, String password);
}
