package by.it_academy.jd2.hw.example.messenger.storage.hibernate;

import by.it_academy.jd2.hw.example.messenger.model.audit.AuditUser;
import by.it_academy.jd2.hw.example.messenger.storage.api.IAuditStorage;
import by.it_academy.jd2.hw.example.messenger.storage.hibernate.api.HibernateEntityFactoryInit;
import by.it_academy.jd2.hw.example.messenger.storage.hibernate.api.entity.AuditUserEntity;
import org.modelmapper.ModelMapper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.stream.Collectors;

public class HnateAuditUserStorage implements IAuditStorage, CommonQueries<AuditUserEntity> {

    private static IAuditStorage INSTANCE;
    private final EntityManagerFactory entityManagerFactory;
    private final ModelMapper mapper;

    private HnateAuditUserStorage() {
        this.entityManagerFactory = HibernateEntityFactoryInit.getEntityManagerFactory();
        this.mapper = new ModelMapper();
    }

    @Override
    public void create(AuditUser audit) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        manager.getTransaction().begin();
        insertRecord(manager, mapper.map(audit, AuditUserEntity.class));
        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public List<AuditUser> read() {
        EntityManager manager = entityManagerFactory.createEntityManager();
        manager.getTransaction().begin();
        List<AuditUserEntity> entities = selectAllRecords(manager, AuditUserEntity.class);
        manager.getTransaction().commit();
        manager.close();
        return entities.stream().map(e -> mapper.map(e, AuditUser.class)).collect(Collectors.toList());
    }

    public static IAuditStorage getInstance() {
        if (INSTANCE == null) {
            synchronized (HnateAuditUserStorage.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HnateAuditUserStorage();
                }
            }
        }
        return INSTANCE;
    }
}
