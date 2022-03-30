package by.it_academy.jd2.hw.example.messenger.storage.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

interface CommonQueries<T> {

    default void insertRecord(EntityManager manager, T entity) {
        manager.persist(entity);
    }

    default List<T> selectAllRecords(EntityManager manager, Class<T> entityClass) {
        return manager.createQuery("from " + entityClass.getSimpleName(), entityClass).getResultList();
    }

    default Long countRecords(EntityManager manager, Class<T> entityClass) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<T> root = query.from(entityClass);
        query.select(cb.count(root));
        return manager.createQuery(query).getSingleResult();
    }
}
