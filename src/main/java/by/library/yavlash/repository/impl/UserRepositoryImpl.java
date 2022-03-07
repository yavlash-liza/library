package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.Role;
import by.library.yavlash.entity.User;
import by.library.yavlash.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Set;

public class UserRepositoryImpl extends AbstractRepositoryImpl<User> implements UserRepository {
    private static final String FIRST_NAME_COLUMN = "firstName";
    private static final String LAST_NAME_COLUMN = "lastName";
    private static final String PASSPORT_COLUMN = "passportNumber";
    private static final String EMAIL_COLUMN = "email";
    private static final String ADDRESS_COLUMN = "address";
    private static final String BIRTH_DATE_COLUMN = "birthDate";
    private static final String ID_COLUMN = "id";
    private static final String USER_ID_COLUMN = "userId";

    private static final String SELECT_ALL_QUERY = "from User";
    private static final String UPDATE_QUERY =
            "update User set firstName=:firstName, lastName=:lastName, passportNumber=:passportNumber," +
                    " email=:email, address=:address, birthDate=:birthDate " +
                    " where id=:id";

    private static final String DELETE_BOOK_DAMAGE_QUERY = "delete BookDamage bd where bd.user.id=:userId";
    private static final String DELETE_ORDER_QUERY = "delete Order o where o.user.id=:userId";

    public UserRepositoryImpl() {
        super(User.class);
    }

    @Override
    protected String defineSelectAllQuery() {
        return SELECT_ALL_QUERY;
    }

    @Override
    protected String defineUpdateQuery() {
        return UPDATE_QUERY;
    }

    protected void deleteLinks(Session session, User user) {
        deleteRoleLinks(user, user.getRoles());
        deleteBookDamage(session, user);
        deleteOrder(session, user);
    }

    private void deleteRoleLinks(User user, Set<Role> roles) {
        roles.forEach(role -> role.getUsers().remove(user));
    }

    private void deleteBookDamage(Session session, User user) {
        session.createQuery(DELETE_BOOK_DAMAGE_QUERY)
                .setParameter(USER_ID_COLUMN, user.getId())
                .executeUpdate();
    }

    private void deleteOrder(Session session, User user) {
        session.createQuery(DELETE_ORDER_QUERY)
                .setParameter(USER_ID_COLUMN, user.getId())
                .executeUpdate();
    }

    @Override
    protected void constructQuery(Query query, User user) {
        query.setParameter(FIRST_NAME_COLUMN, user.getFirstName())
                .setParameter(LAST_NAME_COLUMN, user.getLastName())
                .setParameter(PASSPORT_COLUMN, user.getPassportNumber())
                .setParameter(EMAIL_COLUMN, user.getEmail())
                .setParameter(ADDRESS_COLUMN, user.getAddress())
                .setParameter(BIRTH_DATE_COLUMN, user.getBirthDate())
                .setParameter(ID_COLUMN, user.getId());
    }
}