package by.it_academy.jd2.hw.example.messenger.storage.hibernate;

import by.it_academy.jd2.hw.example.messenger.model.User;
import by.it_academy.jd2.hw.example.messenger.storage.api.IUserStorage;
import by.it_academy.jd2.hw.example.messenger.storage.hibernate.api.HibernateEntityFactoryInit;
import by.it_academy.jd2.hw.example.messenger.storage.hibernate.api.entity.UserEntity;
import org.modelmapper.ModelMapper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class HnateUserStorage implements IUserStorage, CommonQueries<UserEntity> {

    private static IUserStorage INSTANCE;
    private final EntityManagerFactory entityManagerFactory;
    private final ModelMapper mapper;

    HnateUserStorage(EntityManagerFactory factory, ModelMapper mapper) {
        this.entityManagerFactory = factory;
        this.mapper = mapper;
    }

    private HnateUserStorage() {
        this.entityManagerFactory = HibernateEntityFactoryInit.getEntityManagerFactory();
        this.mapper = new ModelMapper();
    }

    @Override
    public User get(String login) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        manager.getTransaction().begin();
        UserEntity entity = selectSingleEntity(manager, login);
        manager.getTransaction().commit();
        manager.close();
        return entity != null ? mapper.map(entity, User.class) : null;
    }

    private UserEntity selectSingleEntity(EntityManager manager, String login) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = cb.createQuery(UserEntity.class);
        Root<UserEntity> root = query.from(UserEntity.class);
        query.where(cb.equal(root.get("login"), login));
        UserEntity entity;
        try {
            entity = manager.createQuery(query).getSingleResult();
        } catch (NoResultException exc) {
            entity = null;
        }
        return entity;
    }

    @Override
    public void add(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Trying to add null user");
        }
        EntityManager manager = entityManagerFactory.createEntityManager();
        try {
            manager.getTransaction().begin();
            insertRecord(manager, mapper.map(user, UserEntity.class));
            manager.getTransaction().commit();
        } catch (Exception exc) {
            manager.getTransaction().rollback();
        } finally {
            manager.close();
        }
    }

    @Override
    public Collection<User> getAll() {
        EntityManager manager = entityManagerFactory.createEntityManager();
        manager.getTransaction().begin();
        List<UserEntity> entities = selectAllRecords(manager, UserEntity.class);
        manager.getTransaction().commit();
        manager.close();
        return entities.stream().map(e -> mapper.map(e, User.class)).collect(Collectors.toList());
    }

    @Override
    public long getCount() {
        EntityManager manager = entityManagerFactory.createEntityManager();
        manager.getTransaction().begin();
        long count = countRecords(manager, UserEntity.class);
        manager.getTransaction().commit();
        manager.close();
        return count;
    }

    public static IUserStorage getInstance() {
        if (INSTANCE == null) {
            synchronized (HnateUserStorage.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HnateUserStorage();
                }
            }
        }
        return INSTANCE;
    }

}
