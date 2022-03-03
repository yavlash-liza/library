//package by.library.yavlash.repository.impl;
//
//import by.library.yavlash.entity.Book;
//import by.library.yavlash.repository.BookRepository;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class BookRepositoryImpl extends AbstractRepositoryImpl<Book> implements BookRepository {
//    private static final String ID_COLUMN = "id";
//    private static final String TITLE_COLUMN = "title";
//    private static final String PAGES_COLUMN = "pages";
//    private static final String IMAGE_PATH_COLUMN = "image_path";
//
//    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM books WHERE id=?";
//    private static final String SELECT_ALL_QUERY = "SELECT * FROM books";
//    private static final String INSERT_QUERY =
//            "INSERT INTO books (title, pages, image_path) VALUES (?,?,?)";
//    private static final String UPDATE_QUERY =
//            "UPDATE books SET title = ?, pages = ?, image_path = ? WHERE id = ?";
//    private static final String DELETE_QUERY = "DELETE FROM books WHERE id = ?";
//
//    private static final String SELECT_BOOK_COPIES_BY_BOOK_ID_QUERY = "SELECT id FROM book_copies WHERE book_id=?";
//    private static final String DELETE_BOOK_GENRE_LINKS_QUERY = "DELETE FROM book_genre_links WHERE book_id=?";
//    private static final String DELETE_BOOK_AUTHOR_LINKS_QUERY = "DELETE FROM book_author_links WHERE book_id=?";
//    private static final String DELETE_BOOK_COPIES_QUERY = "DELETE FROM book_copies WHERE book_id=?";
//    private static final String DELETE_BOOK_DAMAGE_QUERY = "DELETE FROM book_damage WHERE book_copy_id=?";
//    private static final String DELETE_ORDER_BOOK_COPY_LINKS_QUERY = "DELETE FROM order_book_copy_links WHERE book_copy_id=?";
//
//    public BookRepositoryImpl(DataSource dataSource) {
//        super(dataSource);
//    }
//
//    @Override
//    protected String getSelectByIdQuery() {
//        return SELECT_BY_ID_QUERY;
//    }
//
//    @Override
//    protected String getSelectAllQuery() {
//        return SELECT_ALL_QUERY;
//    }
//
//    @Override
//    protected String getInsertQuery() {
//        return INSERT_QUERY;
//    }
//
//    @Override
//    protected String getUpdateQuery() {
//        return UPDATE_QUERY;
//    }
//
//    @Override
//    protected String getDeleteQuery() {
//        return DELETE_QUERY;
//    }
//
//    @Override
//    protected Book construct(ResultSet resultSet) throws SQLException {
//        return Book.builder()
//                .id(resultSet.getLong(ID_COLUMN))
//                .title(resultSet.getString(TITLE_COLUMN))
//                .pagesNumber(resultSet.getInt(PAGES_COLUMN))
//                .imagePath(resultSet.getString(IMAGE_PATH_COLUMN))
//                .build();
//    }
//
//    @Override
//    protected void settingPreparedStatement(PreparedStatement preparedStatement, Book book) throws SQLException {
//        preparedStatement.setString(1, book.getTitle());
//        preparedStatement.setInt(2, book.getPagesNumber());
//        preparedStatement.setString(3, book.getImagePath());
//    }
//
//    @Override
//    protected void deleteLinks(Connection connection, Long id) throws SQLException {
//        deleteBookGenreLinks(connection, id);
//        deleteBookAuthorLinks(connection, id);
//        deleteBooksBookCopies(connection, id);
//    }
//
//    private void deleteBookGenreLinks(Connection connection, Long bookId) throws SQLException {
//        deleteBookLinks(connection, bookId, DELETE_BOOK_GENRE_LINKS_QUERY);
//    }
//
//    private void deleteBookLinks(Connection connection, Long id, String query) throws SQLException {
//        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//            preparedStatement.setLong(1, id);
//            preparedStatement.executeUpdate();
//        }
//    }
//
//    private void deleteBookAuthorLinks(Connection connection, Long bookId) throws SQLException {
//        deleteBookLinks(connection, bookId, DELETE_BOOK_AUTHOR_LINKS_QUERY);
//    }
//
//    private void deleteBooksBookCopies(Connection connection, Long bookId) throws SQLException {
//        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOK_COPIES_BY_BOOK_ID_QUERY)) {
//            preparedStatement.setLong(1, bookId);
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                List<Long> bookCopiesId = new ArrayList<>();
//                while (resultSet.next()) {
//                    bookCopiesId.add(resultSet.getLong(1));
//                }
//                deleteBookDamage(connection, bookCopiesId);
//                deleteOrderBookCopyLinks(connection, bookCopiesId);
//                deleteBookCopies(connection, bookId);
//            }
//        }
//    }
//
//    private void deleteBookDamage(Connection connection, List<Long> bookCopiesId) throws SQLException {
//        for (Long bookId : bookCopiesId) {
//            deleteBookLinks(connection, bookId, DELETE_BOOK_DAMAGE_QUERY);
//        }
//    }
//
//    private void deleteOrderBookCopyLinks(Connection connection, List<Long> bookCopiesId) throws SQLException {
//        for (Long bookId : bookCopiesId) {
//            deleteBookLinks(connection, bookId, DELETE_ORDER_BOOK_COPY_LINKS_QUERY);
//        }
//    }
//
//    private void deleteBookCopies(Connection connection, Long bookId) throws SQLException {
//        deleteBookLinks(connection, bookId, DELETE_BOOK_COPIES_QUERY);
//    }
//}