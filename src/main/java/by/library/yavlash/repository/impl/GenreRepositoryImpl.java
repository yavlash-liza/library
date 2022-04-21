package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.Book;
import by.library.yavlash.entity.Genre;
import by.library.yavlash.exception.RepositoryException;
import by.library.yavlash.repository.GenreRepository;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class GenreRepositoryImpl extends AbstractRepositoryImpl<Genre> implements GenreRepository {
    private static final String GENRE_NAME_COLUMN = "genreName";

    private static final String SELECT_ALL_QUERY = "from Genre";
    private static final String UPDATE_QUERY = "update Genre set genreName=:genreName where id=:id";

    public GenreRepositoryImpl(SessionFactory sessionFactory) {
        super(Genre.class, sessionFactory);
    }

    @Override
    public Set<Book> findBooksByGenreId(Long genreId) throws RepositoryException {
        try (Session session = getSessionFactory().openSession()) {
            Genre genre = session.get(Genre.class, genreId);
            Hibernate.initialize(genre.getBooks());
            return genre.getBooks();
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

    protected void deleteLinks(Session session, Genre genre) {
        deleteBookGenreLinks(genre, genre.getBooks());
    }

    private void deleteBookGenreLinks(Genre genre, Set<Book> books) {
        books.forEach(book -> book.getGenres().remove(genre));
    }

    @Override
    protected void constructQuery(Query query, Genre genre) {
        query.setParameter(GENRE_NAME_COLUMN, genre.getGenreName())
                .setParameter(ID_COLUMN, genre.getId());
    }
}