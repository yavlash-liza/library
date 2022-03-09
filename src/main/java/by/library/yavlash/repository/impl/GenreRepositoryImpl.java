package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.Book;
import by.library.yavlash.entity.Genre;
import by.library.yavlash.repository.GenreRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Set;

public class GenreRepositoryImpl extends AbstractRepositoryImpl<Genre> implements GenreRepository {
    private static final String GENRE_NAME_COLUMN = "genreName";

    private static final String SELECT_ALL_QUERY = "from Genre";
    private static final String UPDATE_QUERY = "update Genre set genreName=:genreName where id=:id";

    public GenreRepositoryImpl() {
        super(Genre.class);
    }

    @Override
    protected String defineSelectAllQuery() {
        return SELECT_ALL_QUERY;
    }

    @Override
    protected String defineUpdateQuery() {
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