package by.it_academy.jd2.hw.example.messenger.storage.hibernate;

import by.it_academy.jd2.hw.example.messenger.model.audit.AuditUser;
import by.it_academy.jd2.hw.example.messenger.storage.api.IAuditStorage;
import by.it_academy.jd2.hw.example.messenger.storage.hibernate.api.entity.AuditUserEntity;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

public class HibernateDBAuditStorage extends AbstractHibernateDBStorage implements IAuditStorage {

    private static final IAuditStorage INSTANCE = new HibernateDBAuditStorage();

    private HibernateDBAuditStorage() {}

    @Override
    public void create(AuditUser audit) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        manager.getTransaction().begin();
        insertRecord(audit, AuditUserEntity.class);
        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public List<AuditUser> read() {
        List<AuditUserEntity> entities = selectAllRecords(AuditUserEntity.class);
        return entities.stream().map(e -> modelMapper.map(e, AuditUser.class)).collect(Collectors.toList());
    }

    public static IAuditStorage getInstance() {
        return INSTANCE;
    }
}
