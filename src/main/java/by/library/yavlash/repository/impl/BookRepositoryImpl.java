package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.Book;
import by.library.yavlash.entity.BookCopy;
import by.library.yavlash.exception.RepositoryException;
import by.library.yavlash.repository.BookRepository;
import by.library.yavlash.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Set;

public class BookRepositoryImpl implements BookRepository {
    private static final String ID_COLUMN = "id";
    private static final String BOOK_COPY_ID_COLUMN = "bookCopyId";
    private static final String TITLE_COLUMN = "title";
    private static final String PAGES_COLUMN = "pagesNumber";
    private static final String IMAGE_PATH_COLUMN = "imagePath";
    private static final String BOOK_ID_COLUMN = "bookId";

    private static final String SELECT_ALL_QUERY = "from Book";
    private static final String UPDATE_QUERY = "update Book set title=:title, pagesNumber=:pagesNumber, imagePath=:imagePath where id=:id";

    private static final String DELETE_BOOK_COPY_QUERY = "delete BookCopy bc where bc.book.id=:bookId";
    private static final String DELETE_BOOK_DAMAGE_QUERY = "DELETE BookDamage bd WHERE bd.bookCopy.id=:bookCopyId";

    @Override
    public Book findById(Long id) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Book.class, id);
        }
    }

    @Override
    public List<Book> findAll() throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(SELECT_ALL_QUERY, Book.class).list();
        }
    }

    @Override
    public boolean add(Book book) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.save(book);
            return true;
        }
    }

    @Override
    public boolean update(Book book) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                session.getTransaction().begin();
                Query query = session.createQuery(UPDATE_QUERY);
                constructQuery(query, book);
                query.executeUpdate();
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException("Book was not updated[" + ex.getMessage() + "]");
            }
        }
    }

    @Override
    public boolean delete(Long id) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                session.getTransaction().begin();
                Book book = session.get(Book.class, id);
                book.getGenres().forEach(genre -> genre.getBooks().remove(book));
                book.getAuthors().forEach(author -> author.getBooks().remove(book));
                deleteBookCopyDamage(session, book.getBookCopies());
                deleteBookCopies(session, book);
                session.delete(book);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException("Book was not deleted[" + ex.getMessage() + "]");
            }
        }
    }

    private void deleteBookCopies(Session session, Book book) {
        session.createQuery(DELETE_BOOK_COPY_QUERY)
                .setParameter(BOOK_ID_COLUMN, book.getId())
                .executeUpdate();
    }

    private void deleteBookCopyDamage(Session session, Set<BookCopy> bookCopies) {
        bookCopies.forEach(bookCopy ->
                session.createQuery(DELETE_BOOK_DAMAGE_QUERY)
                        .setParameter(BOOK_COPY_ID_COLUMN, bookCopy.getId())
                        .executeUpdate()
        );
    }

    private void constructQuery(Query query, Book book) {
        query.setParameter(TITLE_COLUMN, book.getTitle())
                .setParameter(PAGES_COLUMN, book.getPagesNumber())
                .setParameter(IMAGE_PATH_COLUMN, book.getImagePath())
                .setParameter(ID_COLUMN, book.getId());
    }
}