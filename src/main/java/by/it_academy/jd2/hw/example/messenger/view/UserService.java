package by.it_academy.jd2.hw.example.messenger.view;

import by.it_academy.jd2.hw.example.messenger.model.User;
import by.it_academy.jd2.hw.example.messenger.storage.Storage;
import by.it_academy.jd2.hw.example.messenger.storage.api.IUserStorage;
import by.it_academy.jd2.hw.example.messenger.view.api.IUserService;

import java.time.LocalDateTime;
import java.util.Collection;

public class UserService implements IUserService {

    private final static UserService instance = new UserService();

    private final IUserStorage userStorage;

    private UserService() {
        this.userStorage = Storage.getUserStorage();
    }

    @Override
    public User get(String login) {
        return this.userStorage.get(login);
    }

    @Override
    public void signUp(User user) {
        this.validationForSignUp(user);
        user.setRegistration(LocalDateTime.now());
        this.userStorage.add(user);
    }

    private void validationForSignUp(User user){
        String errorMessage = "";
        if(this.nullOrEmpty(user.getLogin())){
            errorMessage += "Логин обязателен для заполнения";
        }
        if(this.nullOrEmpty(user.getPassword())){
            if(!errorMessage.isEmpty()){
                errorMessage += "; ";
            }
            errorMessage += "Пароль обязателен для заполнения";
        }
        if(this.nullOrEmpty(user.getFio())){
            if(!errorMessage.isEmpty()){
                errorMessage += "; ";
            }
            errorMessage += "ФИО обязателен для заполнения";
        }

        if(!errorMessage.isEmpty()){
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private boolean nullOrEmpty(String val){
        return val == null || val.isEmpty();
    }

    @Override
    public Collection<User> getAll() {
        return this.userStorage.getAll();
    }

    @Override
    public long getCount() {
        return this.userStorage.getCount();
    }

    public static UserService getInstance() {
        return instance;
    }
}
