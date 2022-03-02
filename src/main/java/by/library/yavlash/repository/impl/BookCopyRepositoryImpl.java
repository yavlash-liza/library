package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.BookCopy;
import by.library.yavlash.repository.BookCopyRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class BookCopyRepositoryImpl extends AbstractRepositoryImpl<BookCopy> implements BookCopyRepository {
    private static final String ID_COLUMN = "id";
    private static final String BOOK_COPY_STATUS_COLUMN = "book_copy_status";
    private static final String REGISTRATION_DATE_COLUMN = "registration_date";
    private static final String IMAGE_PATH_COLUMN = "image_path";
    private static final String PRICE_PER_DAY_COLUMN = "price_per_day";
    private static final String BOOK_ID_COLUMN = "book_id";

    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM book_copies WHERE id=?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM book_copies";
    private static final String INSERT_QUERY =
            "INSERT INTO book_copies (book_copy_status, registration_date, image_path, price_per_day, book_id) VALUES (?,?,?,?,?)";
    private static final String UPDATE_QUERY =
            "UPDATE book_copies SET book_copy_status=?, registration_date=?, image_path=?, price_per_day=?, book_id=? WHERE id=?";
    private static final String DELETE_QUERY = "DELETE FROM book_copies WHERE id=?";

    private static final String DELETE_ORDER_BOOK_COPY_LINKS_QUERY = "DELETE FROM order_book_copy_links WHERE book_copy_id=?";
    private static final String DELETE_BOOK_DAMAGE_QUERY = "DELETE FROM book_damage WHERE book_copy_id=?";

    public BookCopyRepositoryImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected String getSelectByIdQuery() {
        return SELECT_BY_ID_QUERY;
    }

    @Override
    protected String getSelectAllQuery() {
        return SELECT_ALL_QUERY;
    }

    @Override
    protected String getInsertQuery() {
        return INSERT_QUERY;
    }

    @Override
    protected String getUpdateQuery() {
        return UPDATE_QUERY;
    }

    @Override
    protected String getDeleteQuery() {
        return DELETE_QUERY;
    }

    @Override
    protected BookCopy construct(ResultSet resultSet) throws SQLException {
        return BookCopy.builder()
                .id(resultSet.getLong(ID_COLUMN))
                .status(resultSet.getString(BOOK_COPY_STATUS_COLUMN))
                .registrationDate(resultSet.getDate(REGISTRATION_DATE_COLUMN).toLocalDate())
                .imagePath(resultSet.getString(IMAGE_PATH_COLUMN))
                .pricePerDay(resultSet.getInt(PRICE_PER_DAY_COLUMN))
                .bookId(resultSet.getLong(BOOK_ID_COLUMN))
                .build();
    }

    @Override
    protected void settingPreparedStatement(PreparedStatement preparedStatement, BookCopy bookCopy) throws SQLException {
        preparedStatement.setString(1, bookCopy.getStatus());
        preparedStatement.setDate(2, Date.valueOf(bookCopy.getRegistrationDate()));
        preparedStatement.setString(3, bookCopy.getImagePath());
        preparedStatement.setInt(4, bookCopy.getPricePerDay());
        preparedStatement.setLong(5, bookCopy.getBookId());
    }

    @Override
    protected void deleteLinks(Connection connection, Long id) throws SQLException {
        deleteOrderBookCopyLinks(connection, id);
        deleteBookDamage(connection, id);
    }

    private void deleteOrderBookCopyLinks(Connection connection, Long bookCopyId) throws SQLException {
        deleteBookCopyLinks(connection, bookCopyId, DELETE_ORDER_BOOK_COPY_LINKS_QUERY);
    }

    private void deleteBookCopyLinks(Connection connection, Long id, String query) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }

    private void deleteBookDamage(Connection connection, Long bookCopyId) throws SQLException {
        deleteBookCopyLinks(connection, bookCopyId, DELETE_BOOK_DAMAGE_QUERY);
    }
}