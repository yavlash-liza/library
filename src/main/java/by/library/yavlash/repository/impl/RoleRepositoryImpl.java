package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.Role;
import by.library.yavlash.entity.User;
import by.library.yavlash.exception.RepositoryException;
import by.library.yavlash.repository.RoleRepository;
import by.library.yavlash.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Set;

public class RoleRepositoryImpl extends AbstractRepositoryImpl<Role> implements RoleRepository {
    private static final String ROLE_NAME_COLUMN = "roleName";

    private static final String SELECT_ALL_QUERY = "from Role";
    private static final String UPDATE_QUERY = "update Role set roleName=:roleName where id=:id";

    public RoleRepositoryImpl() {
        super(Role.class);
    }

    @Override
    public Set<User> findUsersByRoleId(Long roleId) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Role role = session.get(Role.class, roleId);
            Hibernate.initialize(role.getUsers());
            return role.getUsers();
        } catch (Exception ex) {
            throw new RepositoryException(String.format("%s was not found: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    protected String defineSelectAllQuery() {
        return SELECT_ALL_QUERY;
    }

    @Override
    protected String defineUpdateQuery() {
        return UPDATE_QUERY;
    }

    protected void deleteLinks(Session session, Role role) {
        deleteUsersLinks(role, role.getUsers());
    }

    private void deleteUsersLinks(Role role, Set<User> users) {
        users.forEach(user -> user.getRoles().remove(role));
    }

    @Override
    protected void constructQuery(Query query, Role role) {
        query.setParameter(ROLE_NAME_COLUMN, role.getRoleName())
                .setParameter(ID_COLUMN, role.getId());
    }
}