package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.BaseEntity;
import by.library.yavlash.exception.RepositoryException;
import by.library.yavlash.repository.BaseRepository;
import by.library.yavlash.util.HibernateUtil;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractRepositoryImpl<E extends BaseEntity> implements BaseRepository<E> {
    protected static final String ID_COLUMN = "id";
    private final Class<E> clazz;

    @Override
    public E findById(Long id) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(clazz, id);
        } catch (Exception ex) {
            throw new RepositoryException(String.format("%s was not found: {%s}", clazz.getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    public List<E> findAll() throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(defineSelectAllQuery(), clazz).list();
        } catch (Exception ex) {
            throw new RepositoryException(String.format("%ss were not found: {%s}", clazz.getSimpleName(), ex.getMessage()));
        }
    }

    protected abstract String defineSelectAllQuery();

    @Override
    public boolean add(E element) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                session.save(element);
                return true;
            } catch (Exception ex) {
                throw new RepositoryException(String.format("%s was not added: {%s}", clazz.getSimpleName(), ex.getMessage()));
            }
        }
    }

    @Override
    public boolean update(E element) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                session.getTransaction().begin();
                Query query = session.createQuery(defineUpdateQuery());
                constructQuery(query, element);
                query.executeUpdate();
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException(String.format("%s was not updated: {%s}", clazz.getSimpleName(), ex.getMessage()));
            }
        }
    }

    protected abstract String defineUpdateQuery();

    protected abstract void constructQuery(Query query, E element);

    @Override
    public boolean delete(Long id) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                session.getTransaction().begin();
                E element = session.get(clazz, id);
                deleteLinks(session, element);
                session.delete(element);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException(String.format("%s was not deleted: {%s}", clazz.getSimpleName(), ex.getMessage()));
            }
        }
    }

    protected void deleteLinks(Session session, E element) {
    }
}