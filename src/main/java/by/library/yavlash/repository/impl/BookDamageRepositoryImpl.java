package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.BookDamage;
import by.library.yavlash.exception.RepositoryException;
import by.library.yavlash.repository.BookDamageRepository;
import by.library.yavlash.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class BookDamageRepositoryImpl implements BookDamageRepository {
    private static final String ID_COLUMN = "id";
    private static final String IMAGE_PATH_COLUMN = "imagePath";
    private static final String DAMAGE_DESCRIPTION_COLUMN = "damageDescription";

    private static final String SELECT_ALL_QUERY = "from BookDamage";
    private static final String UPDATE_QUERY =
            "update BookDamage set imagePath=:imagePath, damageDescription=:damageDescription where id=:id";

    @Override
    public BookDamage findById(Long id) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(BookDamage.class, id);
        }
    }

    @Override
    public List<BookDamage> findAll() throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(SELECT_ALL_QUERY, BookDamage.class).list();
        }
    }

    @Override
    public boolean add(BookDamage bookDamage) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.save(bookDamage);
            return true;
        }
    }

    @Override
    public boolean update(BookDamage bookDamage) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                session.getTransaction().begin();
                Query query = session.createQuery(UPDATE_QUERY);
                constructQuery(query, bookDamage);
                query.executeUpdate();
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException("Book damage was not updated[" + ex.getMessage() + "]");
            }
        }
    }

    @Override
    public boolean delete(Long id) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                session.getTransaction().begin();
                BookDamage bookDamage = session.get(BookDamage.class, id);
                session.delete(bookDamage);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException("Book damage was not deleted[" + ex.getMessage() + "]");
            }
        }
    }

    protected void constructQuery(Query query, BookDamage bookDamage) {
        query.setParameter(IMAGE_PATH_COLUMN, bookDamage.getImagePath())
                .setParameter(DAMAGE_DESCRIPTION_COLUMN, bookDamage.getDamageDescription())
                .setParameter(ID_COLUMN, bookDamage.getId());
    }
}