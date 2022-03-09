package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.BookDamage;
import by.library.yavlash.repository.BookDamageRepository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookDamageRepositoryImpl extends AbstractRepositoryImpl<BookDamage> implements BookDamageRepository {
    private static final String ID_COLUMN = "id";
    private static final String IMAGE_PATH_COLUMN = "image_path";
    private static final String DAMAGE_DESCRIPTION_COLUMN = "damage_description";
    private static final String USER_ID_COLUMN = "user_id";
    private static final String ORDER_ID_COLUMN = "order_id";
    private static final String BOOK_COPY_ID_COLUMN = "book_copy_id";

    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM book_damage WHERE id=?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM book_damage";
    private static final String INSERT_QUERY =
            "INSERT INTO book_damage (image_path, damage_description, user_id, order_id, book_copy_id) VALUES (?,?,?,?,?)";
    private static final String UPDATE_QUERY =
            "UPDATE book_damage SET image_path=?, damage_description=?, user_id=?, order_id=?, book_copy_id=? WHERE id=?";
    private static final String DELETE_QUERY = "DELETE FROM book_damage WHERE id=?";

    public BookDamageRepositoryImpl(DataSource dataSource) {
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
    protected BookDamage construct(ResultSet resultSet) throws SQLException {
        return BookDamage.builder()
                .id(resultSet.getLong(ID_COLUMN))
                .imagePath(resultSet.getString(IMAGE_PATH_COLUMN))
                .damageDescription(resultSet.getString(DAMAGE_DESCRIPTION_COLUMN))
                .userId(resultSet.getLong(USER_ID_COLUMN))
                .orderId(resultSet.getLong(ORDER_ID_COLUMN))
                .bookCopyId(resultSet.getLong(BOOK_COPY_ID_COLUMN))
                .build();
    }

    @Override
    protected void settingPreparedStatement(PreparedStatement preparedStatement, BookDamage bookDamage) throws SQLException {
        preparedStatement.setString(1, bookDamage.getImagePath());
        preparedStatement.setString(2, bookDamage.getDamageDescription());
        preparedStatement.setLong(3, bookDamage.getUserId());
        preparedStatement.setLong(4, bookDamage.getOrderId());
        preparedStatement.setLong(5, bookDamage.getBookCopyId());
    }
}