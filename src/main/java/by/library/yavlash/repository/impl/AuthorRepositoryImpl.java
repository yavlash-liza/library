package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.Author;
import by.library.yavlash.entity.Book;
import by.library.yavlash.exception.RepositoryException;
import by.library.yavlash.repository.AuthorRepository;
import by.library.yavlash.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Set;

public class AuthorRepositoryImpl extends AbstractRepositoryImpl<Author> implements AuthorRepository {
    private static final String FIRST_NAME_COLUMN = "firstName";
    private static final String LAST_NAME_COLUMN = "lastName";
    private static final String BIRTH_DATE_COLUMN = "birthDate";
    private static final String IMAGE_PATH_COLUMN = "imagePath";

    private static final String SELECT_BY_ID = "from Author a left join fetch a.books bs where a.id=:id";
    private static final String SELECT_ALL_QUERY = "from Author";
    private static final String UPDATE_QUERY =
            " update Author set firstName=:firstName, lastName=:lastName, birthDate=:birthDate, imagePath=:imagePath " +
                    " where id=:id ";

    public AuthorRepositoryImpl() {
        super(Author.class);
    }

    @Override
    public Author findById(Long id) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(SELECT_BY_ID, Author.class)
                    .setParameter(ID_COLUMN, id)
                    .getSingleResult();
        } catch (Exception ex) {
            throw new RepositoryException(String.format("%s was not found: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    public Set<Book> findBooksByAuthorId(Long id) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Author author = session.get(Author.class, id);
            Hibernate.initialize(author.getBooks());
            return author.getBooks();
        } catch (Exception ex) {
            throw new RepositoryException(String.format("%s was not found: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    protected String obtainSelectAllQuery() {
        return SELECT_ALL_QUERY;
    }

    @Override
    protected String obtainUpdateQuery() {
        return UPDATE_QUERY;
    }

    protected void deleteLinks(Session session, Author author) {
        deleteBookLinks(author, author.getBooks());
    }

    private void deleteBookLinks(Author author, Set<Book> books) {
        books.forEach(book -> book.getAuthors().remove(author));
    }

    @Override
    protected void constructQuery(Query query, Author author) {
        query.setParameter(FIRST_NAME_COLUMN, author.getFirstName())
                .setParameter(LAST_NAME_COLUMN, author.getLastName())
                .setParameter(BIRTH_DATE_COLUMN, author.getBirthDate())
                .setParameter(IMAGE_PATH_COLUMN, author.getImagePath())
                .setParameter(ID_COLUMN, author.getId());
    }
}