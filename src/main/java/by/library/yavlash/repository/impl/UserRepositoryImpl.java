package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.User;
import by.library.yavlash.exception.RepositoryException;
import by.library.yavlash.repository.UserRepository;
import by.library.yavlash.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {
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
            "update User set firstName=:firstName, lastName=:lastName, passportNumber=:passportNumber, email=:email, " +
                    "address=:address, birthDate=:birthDate where id=:id";

    private static final String DELETE_BOOK_DAMAGE_QUERY = "delete BookDamage bd where bd.user.id=:userId";
    private static final String DELETE_ORDER_QUERY = "delete Order o where o.user.id=:userId";

    @Override
    public User findById(Long id) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(User.class, id);
        }
    }

    @Override
    public List<User> findAll() throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(SELECT_ALL_QUERY, User.class).list();
        }
    }

    @Override
    public boolean add(User user) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.save(user);
            return true;
        }
    }

    @Override
    public boolean update(User user) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                session.getTransaction().begin();
                Query query = session.createQuery(UPDATE_QUERY);
                constructQuery(query, user);
                query.executeUpdate();
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException("User was not updated[" + ex.getMessage() + "]");
            }
        }
    }

    @Override
    public boolean delete(Long id) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            try {
                session.getTransaction().begin();
                User user = session.get(User.class, id);
                user.getRoles().forEach(role -> role.getUsers().remove(user));
                deleteBookDamage(session, user);
                deleteOrder(session, user);
                session.delete(user);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
                throw new RepositoryException("User was not deleted[" + ex.getMessage() + "]");
            }
        }
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

    private void constructQuery(Query query, User user) {
        query.setParameter(FIRST_NAME_COLUMN, user.getFirstName())
                .setParameter(LAST_NAME_COLUMN, user.getLastName())
                .setParameter(PASSPORT_COLUMN, user.getPassportNumber())
                .setParameter(EMAIL_COLUMN, user.getEmail())
                .setParameter(ADDRESS_COLUMN, user.getAddress())
                .setParameter(BIRTH_DATE_COLUMN, user.getBirthDate())
                .setParameter(ID_COLUMN, user.getId());
    }
}