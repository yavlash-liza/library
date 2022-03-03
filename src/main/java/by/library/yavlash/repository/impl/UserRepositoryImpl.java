//package by.library.yavlash.repository.impl;
//
//import by.library.yavlash.entity.User;
//import by.library.yavlash.repository.UserRepository;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Date;
//import java.util.ArrayList;
//import java.util.List;
//
//public class UserRepositoryImpl extends AbstractRepositoryImpl<User> implements UserRepository {
//    private static final String FIRST_NAME_COLUMN = "first_name";
//    private static final String LAST_NAME_COLUMN = "last_name";
//    private static final String PASSPORT_COLUMN = "passport";
//    private static final String EMAIL_COLUMN = "email";
//    private static final String ADDRESS_COLUMN = "address";
//    private static final String BIRTH_DATE_COLUMN = "birth_date";
//
//    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM users WHERE id=?";
//    private static final String SELECT_ALL_QUERY = "SELECT * FROM users";
//    private static final String INSERT_QUERY =
//            "INSERT INTO users (first_name, last_name, passport, email, address, birth_date) VALUES (?,?,?,?,?,?)";
//    private static final String UPDATE_QUERY =
//            "UPDATE users SET first_name=?, last_name=?, passport=?, email=?, address=?, birth_date=? WHERE id=?";
//    private static final String DELETE_QUERY = "DELETE FROM users WHERE id=?";
//
//    private static final String SELECT_ORDERS_BY_USER_ID_QUERY = "SELECT id FROM orders WHERE user_id=?";
//    private static final String DELETE_ROLE_LINKS_QUERY = "DELETE FROM user_role_links WHERE user_id=?";
//    private static final String DELETE_ORDERS_QUERY = "DELETE FROM orders WHERE user_id=?";
//    private static final String DELETE_ORDER_LINKS_QUERY = "DELETE FROM order_book_copy_links WHERE order_id=?";
//    private static final String DELETE_BOOK_DAMAGE_QUERY = "DELETE FROM book_damage WHERE user_id=?";
//
//    public UserRepositoryImpl(DataSource dataSource) {
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
//    protected User construct(ResultSet resultSet) throws SQLException {
//        return User.builder()
//                .id(resultSet.getLong(ID_COLUMN))
//                .firstName(resultSet.getString(FIRST_NAME_COLUMN))
//                .lastName(resultSet.getString(LAST_NAME_COLUMN))
//                .passportNumber(resultSet.getString(PASSPORT_COLUMN))
//                .email(resultSet.getString(EMAIL_COLUMN))
//                .address(resultSet.getString(ADDRESS_COLUMN))
//                .birthDate(resultSet.getDate(BIRTH_DATE_COLUMN).toLocalDate())
//                .build();
//    }
//
//    @Override
//    protected void settingPreparedStatement(PreparedStatement preparedStatement, User user) throws SQLException {
//        preparedStatement.setString(1, user.getFirstName());
//        preparedStatement.setString(2, user.getLastName());
//        preparedStatement.setString(3, user.getPassportNumber());
//        preparedStatement.setString(4, user.getEmail());
//        preparedStatement.setString(5, user.getAddress());
//        preparedStatement.setDate(6, Date.valueOf(user.getBirthDate()));
//    }
//
//    @Override
//    protected void deleteLinks(Connection connection, Long id) throws SQLException {
//        deleteUserRoleLinks(connection, id);
//        deleteBookDamage(connection, id);
//        deleteUserOrders(connection, id);
//    }
//
//    private void deleteUserRoleLinks(Connection connection, Long userId) throws SQLException {
//        deleteUserLinks(connection, userId, DELETE_ROLE_LINKS_QUERY);
//    }
//
//    private void deleteUserLinks(Connection connection, Long id, String query) throws SQLException {
//        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//            preparedStatement.setLong(1, id);
//            preparedStatement.executeUpdate();
//        }
//    }
//
//    private void deleteBookDamage(Connection connection, Long userId) throws SQLException {
//        deleteUserLinks(connection, userId, DELETE_BOOK_DAMAGE_QUERY);
//    }
//
//    private void deleteUserOrders(Connection connection, Long userId) throws SQLException {
//        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDERS_BY_USER_ID_QUERY)) {
//            preparedStatement.setLong(1, userId);
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                List<Long> ordersId = new ArrayList<>();
//                while (resultSet.next()) {
//                    ordersId.add(resultSet.getLong(1));
//                }
//                deleteOrdersLinks(connection, ordersId);
//                deleteOrders(connection, userId);
//            }
//        }
//    }
//
//    private void deleteOrdersLinks(Connection connection, List<Long> orders) throws SQLException {
//        for (Long orderId : orders) {
//            deleteUserLinks(connection, orderId, DELETE_ORDER_LINKS_QUERY);
//        }
//    }
//
//    private void deleteOrders(Connection connection, Long userId) throws SQLException {
//        deleteUserLinks(connection, userId, DELETE_ORDERS_QUERY);
//    }
//}