package by.it_academy.jd2.hw.example.messenger.storage.hibernate;

import by.it_academy.jd2.hw.example.messenger.model.Message;
import by.it_academy.jd2.hw.example.messenger.storage.api.IChatStorage;
import by.it_academy.jd2.hw.example.messenger.storage.hibernate.api.HibernateEntityFactoryInit;
import by.it_academy.jd2.hw.example.messenger.storage.hibernate.api.entity.MessageEntity;
import org.modelmapper.ModelMapper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

public class HnateChatStorage implements IChatStorage, CommonQueries<MessageEntity> {

    private static IChatStorage INSTANCE;
    private final EntityManagerFactory managerFactory;
    private final ModelMapper mapper;

    private HnateChatStorage() {
        this.managerFactory = HibernateEntityFactoryInit.getEntityManagerFactory();
        this.mapper = new ModelMapper();
    }

    @Override
    public List<Message> get(String login) {
        EntityManager manager = managerFactory.createEntityManager();
        manager.getTransaction().begin();
        List<MessageEntity> entities = selectEntities(manager, login);
        manager.getTransaction().commit();
        manager.close();
        return entities.stream().map(e -> mapper.map(e, Message.class)).collect(Collectors.toList());
    }

    private List<MessageEntity> selectEntities(EntityManager manager, String login) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<MessageEntity> query = cb.createQuery(MessageEntity.class);
        Root<MessageEntity> root = query.from(MessageEntity.class);
        Predicate fromPredicate = cb.equal(root.get("from"), login);
        Predicate toPredicate = cb.equal(root.get("to"), login);
        query.where(cb.or(fromPredicate, toPredicate));
        return manager.createQuery(query).getResultList();
    }

    @Override
    public void addMessage(Message message) {
        if (message == null) {
            throw new IllegalArgumentException("Trying to add null message");
        }
        EntityManager manager = managerFactory.createEntityManager();
        manager.getTransaction().begin();
        insertRecord(manager, mapper.map(message, MessageEntity.class));
        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public long getCount() {
        EntityManager manager = managerFactory.createEntityManager();
        manager.getTransaction().begin();
        long count = countRecords(manager, MessageEntity.class);
        manager.getTransaction().commit();
        manager.close();
        return count;
    }

    public static IChatStorage getInstance() {
        if (INSTANCE == null) {
            synchronized (HnateAuditUserStorage.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HnateChatStorage();
                }
            }
        }
        return INSTANCE;
    }
}
