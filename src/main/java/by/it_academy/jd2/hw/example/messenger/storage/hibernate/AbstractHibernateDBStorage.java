package by.it_academy.jd2.hw.example.messenger.storage.hibernate;

import by.it_academy.jd2.hw.example.messenger.storage.hibernate.api.entity.UserEntity;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

abstract class AbstractHibernateDBStorage {

    protected final ModelMapper modelMapper;
    protected final EntityManagerFactory entityManagerFactory;

    AbstractHibernateDBStorage() {
        this.modelMapper = new ModelMapper();
        this.entityManagerFactory = HibernateEntityFactoryInit.getEntityManagerFactory();
    }

    protected <T> T selectEqualEntity(Class<T> entityClass, String value) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(entityClass);
        Root<T> from = query.from(entityClass);
        Predicate condition = cb.equal(from.get(idAttribute(entityClass)), value);
        query.where(condition);
        try {
            T entity = entityManager.createQuery(query).getSingleResult();
            entityManager.getTransaction().commit();
            return entity;
        } catch (NoResultException exc) {
            return null;
        } finally {
            entityManager.close();
        }
    }

    private static String idAttribute(Class<?> clazz) {
        for (Method method: clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Id.class)) {
                return method.getName().substring(3).toLowerCase();
            }
        }
        throw new IllegalStateException("Cannot find id field: " + clazz.getName());
    }

    protected <T> List<T> selectEqualEntities(Class<T> entityClass, String value, String... fields) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(entityClass);
        Root<T> from = query.from(entityClass);
        Predicate[] predicates = Arrays
                .stream(fields)
                .map(f -> cb.equal(from.get(f), value))
                .toArray(Predicate[]::new);
        query.where(cb.or(predicates));
        List<T> entities = entityManager.createQuery(query).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return entities;
    }


    protected <T> List<T> selectAllRecords(Class<T> entityClass) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<T> entities = entityManager
                .createQuery("from " + entityClass.getSimpleName(), entityClass)
                .getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return entities;
    }

//    protected <T> void insertRecord(T object, Class<?> entityClass) {
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        entityManager.getTransaction().begin();
//        entityManager.persist(modelMapper.map(object, entityClass));
//        entityManager.getTransaction().commit();
//        entityManager.close();
//    }

    protected <T> void insertRecord(T object, Class<?> entityClass) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        manager.getTransaction().begin();
        insertRecord(object, UserEntity.class);
        manager.getTransaction().commit();
        manager.close();
    }

    protected Long countRecords(Class<?> entityClass) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        query.select(cb.count(query.from(entityClass)));
        long result = entityManager.createQuery(query).getSingleResult();
        entityManager.getTransaction().commit();
        entityManager.close();
        return result;
    }

}
