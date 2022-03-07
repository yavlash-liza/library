package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.Author;
import by.library.yavlash.entity.Book;
import by.library.yavlash.repository.AuthorRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Set;

public class AuthorRepositoryImpl extends AbstractRepositoryImpl<Author> implements AuthorRepository {
    private static final String ID_COLUMN = "id";
    private static final String FIRST_NAME_COLUMN = "firstName";
    private static final String LAST_NAME_COLUMN = "lastName";
    private static final String BIRTH_DATE_COLUMN = "birthDate";
    private static final String IMAGE_PATH_COLUMN = "imagePath";

    private static final String SELECT_ALL_QUERY = "from Author";
    private static final String UPDATE_QUERY =
            " update Author set firstName=:firstName, lastName=:lastName, birthDate=:birthDate, imagePath=:imagePath " +
                    " where id=:id ";

    public AuthorRepositoryImpl() {
        super(Author.class);
    }

    @Override
    protected String defineSelectAllQuery() {
        return SELECT_ALL_QUERY;
    }

    @Override
    protected String defineUpdateQuery() {
        return UPDATE_QUERY;
    }

    protected void deleteLinks(Session session, Author author) {
        deleteBookLinks(author, author.getBooks());
    }

    private void deleteBookLinks(Author author, Set<Book> books) {
        books.forEach(book -> book.getAuthors().remove(author));
    }

    @Override
    protected void constructQuery(Query query, Author element) {
        query.setParameter(FIRST_NAME_COLUMN, element.getFirstName())
                .setParameter(LAST_NAME_COLUMN, element.getLastName())
                .setParameter(BIRTH_DATE_COLUMN, element.getBirthDate())
                .setParameter(IMAGE_PATH_COLUMN, element.getImagePath())
                .setParameter(ID_COLUMN, element.getId());
    }
}