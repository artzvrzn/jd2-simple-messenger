package by.it_academy.jd2.hw.example.messenger.storage.hibernate;

import by.it_academy.jd2.hw.example.messenger.model.Message;
import by.it_academy.jd2.hw.example.messenger.storage.api.IChatStorage;
import by.it_academy.jd2.hw.example.messenger.storage.hibernate.api.entity.MessageEntity;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

public class HibernateDBChatStorage extends AbstractHibernateDBStorage implements IChatStorage {

    private final static IChatStorage INSTANCE = new HibernateDBChatStorage();

    private HibernateDBChatStorage(){
    }

    @Override
    public List<Message> get(String login) {
        List<MessageEntity> entities = selectEqualEntities(MessageEntity.class, login, "from", "to");
        return entities
                .stream()
                .map(e -> modelMapper.map(e, Message.class))
                .collect(Collectors.toList());
    }

    @Override
    public void addMessage(Message message) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        manager.getTransaction().begin();
        insertRecord(message, MessageEntity.class);
        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public long getCount() {
        return countRecords(MessageEntity.class);
    }

    public static IChatStorage getInstance() {
        return INSTANCE;
    }
}
