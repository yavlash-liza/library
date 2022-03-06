package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.BookCopy;
import by.library.yavlash.exception.RepositoryException;
import by.library.yavlash.repository.BookCopyRepository;
import by.library.yavlash.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class BookCopyRepositoryImpl implements BookCopyRepository {
    private static final String ID_COLUMN = "id";
    private static final String BOOK_COPY_STATUS_COLUMN = "status";
    private static final String REGISTRATION_DATE_COLUMN = "registrationDate";
    private static final String IMAGE_PATH_COLUMN = "imagePath";
    private static final String PRICE_PER_DAY_COLUMN = "pricePerDay";
    private static final String BOOK_COPY_ID_COLUMN = "bookCopyId";

    private static final String SELECT_ALL_QUERY = "from BookCopy";
    private static final String UPDATE_QUERY = " update BookCopy set status=:status, registrationDate=:registrationDate, " +
            " imagePath=:imagePath, pricePerDay=:pricePerDay where id=:id";

    private static final String DELETE_BOOK_DAMAGE_QUERY = "delete BookDamage bd where bd.bookCopy.id=:bookCopyId";

    @Override
    public BookCopy findById(Long id) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(BookCopy.class, id);
        }
    }

    @Override
    public List<BookCopy> findAll() throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(SELECT_ALL_QUERY, BookCopy.class).list();
        }
    }

    @Override
    public boolean add(BookCopy bookCopy) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.save(bookCopy);
            return true;
        }
    }

    @Override
    public boolean update(BookCopy bookCopy) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                session.getTransaction().begin();
                Query query = session.createQuery(UPDATE_QUERY);
                constructQuery(query, bookCopy);
                query.executeUpdate();
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException("Book copy was not updated[" + ex.getMessage() + "]");
            }
        }
    }

    @Override
    public boolean delete(Long id) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                session.getTransaction().begin();
                BookCopy bookCopy = session.get(BookCopy.class, id);
                bookCopy.getOrders().forEach(order -> order.getBookCopies().remove(bookCopy));
                deleteBookDamage(session, bookCopy);
                session.delete(bookCopy);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException("Book copy was not deleted[" + ex.getMessage() + "]");
            }
        }
    }

    private void deleteBookDamage(Session session, BookCopy bookCopy) {
        session.createQuery(DELETE_BOOK_DAMAGE_QUERY)
                .setParameter(BOOK_COPY_ID_COLUMN, bookCopy.getId())
                .executeUpdate();
    }

    private void constructQuery(Query query, BookCopy bookCopy) {
        query.setParameter(BOOK_COPY_STATUS_COLUMN, bookCopy.getStatus())
                .setParameter(REGISTRATION_DATE_COLUMN, bookCopy.getRegistrationDate())
                .setParameter(IMAGE_PATH_COLUMN, bookCopy.getImagePath())
                .setParameter(PRICE_PER_DAY_COLUMN, bookCopy.getPricePerDay())
                .setParameter(ID_COLUMN, bookCopy.getId());
    }
}