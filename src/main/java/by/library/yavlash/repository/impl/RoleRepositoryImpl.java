package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.Role;
import by.library.yavlash.repository.RoleRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRepositoryImpl extends AbstractRepositoryImpl<Role> implements RoleRepository {
    private static final String ID_COLUMN = "id";
    private static final String ROLE_NAME_COLUMN = "role_name";

    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM roles WHERE id=?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM roles";
    private static final String INSERT_QUERY = "INSERT INTO roles (role_name) VALUES (?)";
    private static final String UPDATE_QUERY = "UPDATE roles SET role_name=? WHERE id=?";
    private static final String DELETE_QUERY = "DELETE FROM roles WHERE id=?";

    private static final String DELETE_ROLE_LINKS_QUERY = "DELETE FROM user_role_links WHERE role_id=?";

    public RoleRepositoryImpl(DataSource dataSource) {
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
    protected Role construct(ResultSet resultSet) throws SQLException {
        return Role.builder()
                .id(resultSet.getLong(ID_COLUMN))
                .roleName(resultSet.getString(ROLE_NAME_COLUMN))
                .build();
    }

    @Override
    protected void settingPreparedStatement(PreparedStatement preparedStatement, Role role) throws SQLException {
        preparedStatement.setString(1, role.getRoleName());
    }

    @Override
    protected void deleteLinks(Connection connection, Long id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ROLE_LINKS_QUERY)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }
}