package by.it_academy.jd2.hw.example.messenger.view;

import by.it_academy.jd2.hw.example.messenger.model.User;
import by.it_academy.jd2.hw.example.messenger.view.api.IAuthService;
import by.it_academy.jd2.hw.example.messenger.view.api.IUserService;

import java.util.Objects;

public class AuthService implements IAuthService {
    private final static AuthService instance = new AuthService();

    private final IUserService userService;

    private AuthService() {
        this.userService = UserService.getInstance();
    }

    @Override
    public User authentication(String login, String password) {
        User user = this.userService.get(login);
        if(user == null){
            return null;
        }

        if(!Objects.equals(user.getPassword(), password)){
            return null;
        }

        return user;
    }

    public static AuthService getInstance() {
        return instance;
    }
}
