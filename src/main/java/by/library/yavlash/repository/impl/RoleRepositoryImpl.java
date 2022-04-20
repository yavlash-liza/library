package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.Role;
import by.library.yavlash.entity.User;
import by.library.yavlash.repository.RoleRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class RoleRepositoryImpl extends AbstractRepositoryImpl<Role> implements RoleRepository {
    private static final String ROLE_NAME_COLUMN = "roleName";

    private static final String SELECT_ALL_QUERY = "from Role";
    private static final String UPDATE_QUERY = "update Role set roleName=:roleName where id=:id";

    public RoleRepositoryImpl(SessionFactory sessionFactory) {
        super(Role.class, sessionFactory);
    }

    @Override
    protected String obtainSelectAllQuery() {
        return SELECT_ALL_QUERY;
    }

    @Override
    protected String obtainUpdateQuery() {
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