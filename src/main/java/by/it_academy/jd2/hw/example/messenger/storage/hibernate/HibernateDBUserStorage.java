package by.it_academy.jd2.hw.example.messenger.storage.hibernate;

import by.it_academy.jd2.hw.example.messenger.model.User;
import by.it_academy.jd2.hw.example.messenger.storage.api.IUserStorage;
import by.it_academy.jd2.hw.example.messenger.storage.hibernate.api.entity.UserEntity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class HibernateDBUserStorage extends AbstractHibernateDBStorage implements IUserStorage {

    private static final IUserStorage INSTANCE = new HibernateDBUserStorage();

    private HibernateDBUserStorage(){}

    @Override
    public User get(String login) {
        UserEntity entity = selectEqualEntity(UserEntity.class, login);
        return entity != null ? modelMapper.map(entity, User.class) : null;
    }

    @Override
    public void add(User user) {
        insertRecord(user, UserEntity.class);
    }

    @Override
    public Collection<User> getAll() {
        List<UserEntity> entities = selectAllRecords(UserEntity.class);
        return entities.stream().map(e -> modelMapper.map(e, User.class)).collect(Collectors.toList());
    }

    @Override
    public long getCount() {
        return countRecords(UserEntity.class);
    }

    public static IUserStorage getInstance() {
        return INSTANCE;
    }
}
