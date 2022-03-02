package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.Order;
import by.library.yavlash.repository.OrderRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class OrderRepositoryImpl extends AbstractRepositoryImpl<Order> implements OrderRepository {
    private static final String ID_COLUMN = "id";
    private static final String ORDER_STATUS_COLUMN = "order_status";
    private static final String START_DATE_COLUMN = "start_date";
    private static final String END_DATE_COLUMN = "end_date";
    private static final String PRICE_COLUMN = "price";
    private static final String USER_ID_COLUMN = "user_id";

    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM orders WHERE id=?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM orders";
    private static final String INSERT_QUERY =
            "INSERT INTO orders (order_status, start_date, end_date, price, user_id) VALUES (?,?,?,?,?)";
    private static final String UPDATE_QUERY =
            "UPDATE orders SET order_status=?, start_date=?, end_date=?, price=?, user_id=? WHERE id=?";
    private static final String DELETE_QUERY = "DELETE FROM orders WHERE id=?";

    private static final String DELETE_ORDER_BOOK_COPY_LINKS_QUERY = "DELETE FROM order_book_copy_links WHERE order_id=?";
    private static final String DELETE_BOOK_DAMAGE_QUERY = "DELETE FROM book_damage WHERE order_id=?";

    public OrderRepositoryImpl(DataSource dataSource) {
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
    protected Order construct(ResultSet resultSet) throws SQLException {
        return Order.builder()
                .id(resultSet.getLong(ID_COLUMN))
                .orderStatus(resultSet.getString(ORDER_STATUS_COLUMN))
                .startDate(resultSet.getDate(START_DATE_COLUMN).toLocalDate())
                .endDate(resultSet.getDate(END_DATE_COLUMN).toLocalDate())
                .price(resultSet.getInt(PRICE_COLUMN))
                .userId(resultSet.getLong(USER_ID_COLUMN))
                .build();
    }

    @Override
    protected void settingPreparedStatement(PreparedStatement preparedStatement, Order order) throws SQLException {
        preparedStatement.setString(1, order.getOrderStatus());
        preparedStatement.setDate(2, Date.valueOf(order.getStartDate()));
        preparedStatement.setDate(3, Date.valueOf(order.getEndDate()));
        preparedStatement.setInt(4, order.getPrice());
        preparedStatement.setLong(5, order.getUserId());
    }

    @Override
    protected void deleteLinks(Connection connection, Long id) throws SQLException{
        deleteOrderBookCopyLinks(connection, id);
        deleteBookDamage(connection, id);
    }

    private void deleteOrderBookCopyLinks(Connection connection, Long orderId) throws SQLException {
        deleteOrderLinks(connection, orderId, DELETE_ORDER_BOOK_COPY_LINKS_QUERY);
    }

    private void deleteOrderLinks(Connection connection, Long id, String query) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }

    private void deleteBookDamage(Connection connection, Long orderId) throws SQLException {
        deleteOrderLinks(connection, orderId, DELETE_BOOK_DAMAGE_QUERY);
    }
}