package by.it_academy.jd2.hw.example.messenger.storage.hibernate;

import by.it_academy.jd2.hw.example.messenger.storage.api.IUserStorage;
import by.it_academy.jd2.hw.example.messenger.storage.hibernate.api.entity.AuditUserEntity;
import by.it_academy.jd2.hw.example.messenger.storage.hibernate.api.entity.UserEntity;
import org.modelmapper.ModelMapper;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

public class HnateAuditableUserStorage extends HnateUserStorage {

    private static IUserStorage INSTANCE;

    private HnateAuditableUserStorage() {
        super(HibernateEntityFactoryInit.getEntityManagerFactory(), new ModelMapper());
    }

    @Override
    public void insertRecord(EntityManager manager, UserEntity entity) {
        manager.persist(registration(entity));
        super.insertRecord(manager, entity);
    }

    private AuditUserEntity registration(UserEntity userEntity) {
        String info = String.format("User %s has been signed up", userEntity.getLogin());
        return createAudit(null, userEntity, info);
    }

    private AuditUserEntity createAudit(UserEntity creator, UserEntity subject, String info) {
        AuditUserEntity audit = new AuditUserEntity();
        audit.setAuthor(creator);
        audit.setUser(subject);
        audit.setInfo(info);
        audit.setCreated(LocalDateTime.now());
        return audit;
    }

    public static IUserStorage getInstance() {
        if (INSTANCE == null) {
            synchronized (HnateAuditableUserStorage.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HnateAuditableUserStorage();
                }
            }
        }
        return INSTANCE;
    }
}
