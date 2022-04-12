package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.Order;
import by.library.yavlash.entity.Role;
import by.library.yavlash.entity.User;
import by.library.yavlash.exception.RepositoryException;
import by.library.yavlash.repository.UserRepository;
import by.library.yavlash.util.HibernateUtil;
import org.hibernate.Hibernate;
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
    public Set<Role> findRolesByUserId(Long userId) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, userId);
            Hibernate.initialize(user.getRoles());
            return user.getRoles();
        } catch (Exception ex) {
            throw new RepositoryException(String.format("%s was not found: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    public Set<Order> findOrdersByUserId(Long userId) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, userId);
            Hibernate.initialize(user.getOrders());
            return user.getOrders();
        } catch (Exception ex) {
            throw new RepositoryException(String.format("%s was not found: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    protected String obtainSelectAllQuery() {
        return SELECT_ALL_QUERY;
    }

    @Override
    protected String obtainUpdateQuery() {
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