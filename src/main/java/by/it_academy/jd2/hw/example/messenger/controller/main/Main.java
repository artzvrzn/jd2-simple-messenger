package by.it_academy.jd2.hw.example.messenger.controller.main;

import by.it_academy.jd2.hw.example.messenger.model.Message;
import by.it_academy.jd2.hw.example.messenger.model.User;
import by.it_academy.jd2.hw.example.messenger.model.audit.AuditUser;
import by.it_academy.jd2.hw.example.messenger.storage.api.IAuditStorage;
import by.it_academy.jd2.hw.example.messenger.storage.api.IChatStorage;
import by.it_academy.jd2.hw.example.messenger.storage.api.IUserStorage;
import by.it_academy.jd2.hw.example.messenger.storage.hibernate.HibernateDBAuditStorage;
import by.it_academy.jd2.hw.example.messenger.storage.hibernate.HibernateDBChatStorage;
import by.it_academy.jd2.hw.example.messenger.storage.hibernate.HibernateDBUserStorage;
import by.it_academy.jd2.hw.example.messenger.storage.hibernate.api.entity.AuditUserEntity;
import by.it_academy.jd2.hw.example.messenger.storage.hibernate.api.entity.MessageEntity;
import by.it_academy.jd2.hw.example.messenger.storage.hibernate.api.entity.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        IUserStorage userStorage = HibernateDBUserStorage.getInstance();
        User user = new User();
        user.setLogin("user4");
        user.setFio("user4");
        user.setPassword("user4");
        user.setRegistration(LocalDateTime.now());
        user.setBirthday(LocalDate.now());
        userStorage.add(user);

//        IChatStorage chatStorage = HibernateDBChatStorage.getInstance();
//        Message message = new Message();
//        message.setFrom("power");
//        message.setTo("power");
//        message.setText("fsklflksklf");
//        message.setSendDate(LocalDateTime.now());
//        System.out.println(chatStorage.get("artem"));
//        System.out.println(chatStorage.get("power"));
//        chatStorage.addMessage(message);
//        IUserStorage storage = HibernateDBUserStorage.getInstance();
//        System.out.println(storage.getAll());
//        User user = new User();
//        user.setLogin("user3");
//        user.setFio("power");
//        user.setPassword("power");
//        user.setRegistration(LocalDateTime.now());
//        user.setBirthday(LocalDate.now());
//        storage.add(user);

//        System.out.println(storage.get("power"));

//
//        IChatStorage cs = HibernateDBChatStorage.getInstance();
//        System.out.println(cs.get("artem3"));
//        Message mes = new Message();
//        mes.setId(111L);
//        mes.setTo("artem");
//        mes.setFrom("artem3");
//        mes.setSendDate(LocalDateTime.now());
//        mes.setText("privet");
//        cs.addMessage(mes);
    }
}
