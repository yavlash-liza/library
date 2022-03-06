package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.Author;
import by.library.yavlash.exception.RepositoryException;
import by.library.yavlash.repository.AuthorRepository;
import by.library.yavlash.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class AuthorRepositoryImpl implements AuthorRepository {
    private static final String ID_COLUMN = "id";
    private static final String FIRST_NAME_COLUMN = "firstName";
    private static final String LAST_NAME_COLUMN = "lastName";
    private static final String BIRTH_DATE_COLUMN = "birthDate";
    private static final String IMAGE_PATH_COLUMN = "imagePath";

    private static final String SELECT_ALL_QUERY = "from Author";
    private static final String UPDATE_QUERY = "update Author set firstName=:firstName, lastName=:lastName, " +
            "birthDate=:birthDate, imagePath=:imagePath where id=:id";

    @Override
    public Author findById(Long id) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Author.class, id);
        }
    }

    @Override
    public List<Author> findAll() throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(SELECT_ALL_QUERY, Author.class).list();
        }
    }

    @Override
    public boolean add(Author author) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.save(author);
            return true;
        }
    }

    @Override
    public boolean update(Author author) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                session.getTransaction().begin();
                Query query = session.createQuery(UPDATE_QUERY);
                constructQuery(query, author);
                query.executeUpdate();
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException("Author was not updated[" + ex.getMessage() + "]");
            }
        }
    }

    @Override
    public boolean delete(Long id) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                session.getTransaction().begin();
                Author author = session.get(Author.class, id);
                author.getBooks().forEach(book -> book.getAuthors().remove(author));
                session.delete(author);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException("Author was not deleted[" + ex.getMessage() + "]");
            }
        }
    }

    private void constructQuery(Query query, Author author) {
        query.setParameter(FIRST_NAME_COLUMN, author.getFirstName())
                .setParameter(LAST_NAME_COLUMN, author.getLastName())
                .setParameter(BIRTH_DATE_COLUMN, author.getBirthDate())
                .setParameter(IMAGE_PATH_COLUMN, author.getImagePath())
                .setParameter(ID_COLUMN, author.getId());
    }
}