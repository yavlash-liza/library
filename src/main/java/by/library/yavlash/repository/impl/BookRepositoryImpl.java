package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.Author;
import by.library.yavlash.entity.Book;
import by.library.yavlash.entity.BookCopy;
import by.library.yavlash.entity.Genre;
import by.library.yavlash.repository.BookRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Set;
import java.util.stream.Collectors;

public class BookRepositoryImpl extends AbstractRepositoryImpl<Book> implements BookRepository {
    private static final String BOOK_COPY_ID_COLUMN = "bookCopyId";
    private static final String TITLE_COLUMN = "title";
    private static final String PAGES_COLUMN = "pagesNumber";
    private static final String IMAGE_PATH_COLUMN = "imagePath";
    private static final String BOOK_ID_COLUMN = "bookId";

    private static final String SELECT_ALL_QUERY = "from Book";
    private static final String UPDATE_QUERY =
            "update Book set title=:title, pagesNumber=:pagesNumber, imagePath=:imagePath " +
                    " where id=:id";

    private static final String DELETE_BOOK_COPY_QUERY = "delete BookCopy bc where bc.book.id=:bookId";
    private static final String DELETE_BOOK_DAMAGE_QUERY = "DELETE BookDamage bd WHERE bd.bookCopy.id=:bookCopyId";

    public BookRepositoryImpl() {
        super(Book.class);
    }

    @Override
    public Set<Author> findAuthorsByAuthorsId(Set<Long> authorsId) {
        return authorsId.stream()
                .map(authorId -> Author.builder().id(authorId).build())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Genre> findGenresByGenresId(Set<Long> genresId) {
        return genresId.stream()
                .map(genreId -> Genre.builder().id(genreId).build())
                .collect(Collectors.toSet());
    }

    @Override
    protected String defineSelectAllQuery() {
        return SELECT_ALL_QUERY;
    }

    @Override
    protected String defineUpdateQuery() {
        return UPDATE_QUERY;
    }

    protected void deleteLinks(Session session, Book book) {
        deleteGenreLinks(book, book.getGenres());
        deleteAuthorLinks(book, book.getAuthors());
        deleteBookCopyDamage(session, book.getBookCopies());
        deleteBookCopies(session, book);
    }

    private void deleteGenreLinks(Book book, Set<Genre> genres) {
        genres.forEach(genre -> genre.getBooks().remove(book));
    }

    private void deleteAuthorLinks(Book book, Set<Author> authors) {
        authors.forEach(author -> author.getBooks().remove(book));
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

    @Override
    protected void constructQuery(Query query, Book book) {
        query.setParameter(TITLE_COLUMN, book.getTitle())
                .setParameter(PAGES_COLUMN, book.getPagesNumber())
                .setParameter(IMAGE_PATH_COLUMN, book.getImagePath())
                .setParameter(ID_COLUMN, book.getId());
    }
}