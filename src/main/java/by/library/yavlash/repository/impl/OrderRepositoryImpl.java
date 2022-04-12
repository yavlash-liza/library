package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.BookCopy;
import by.library.yavlash.entity.BookDamage;
import by.library.yavlash.entity.Order;
import by.library.yavlash.entity.User;
import by.library.yavlash.exception.RepositoryException;
import by.library.yavlash.repository.OrderRepository;
import by.library.yavlash.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Set;
import java.util.stream.Collectors;

public class OrderRepositoryImpl extends AbstractRepositoryImpl<Order> implements OrderRepository {
    private static final String ORDER_STATUS_COLUMN = "orderStatus";
    private static final String START_DATE_COLUMN = "startDate";
    private static final String END_DATE_COLUMN = "endDate";
    private static final String PRICE_COLUMN = "price";
    private static final String ORDER_ID_COLUMN = "orderId";

    private static final String SELECT_ALL_QUERY = "from Order";
    private static final String UPDATE_QUERY =
            "update Order set orderStatus=:orderStatus, startDate=:startDate, endDate=:endDate, price=:price " +
                    " where id=:id";

    private static final String DELETE_BOOK_DAMAGE_QUERY = "delete BookDamage bd where bd.order.id=:orderId";

    public OrderRepositoryImpl() {
        super(Order.class);
    }

    @Override
    public User findUserByOrderId(Long orderId) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Order order = session.get(Order.class, orderId);
            Hibernate.initialize(order.getUser());
            return order.getUser();
        } catch (Exception ex) {
            throw new RepositoryException(String.format("%s was not found: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    public Set<BookCopy> findBookCopiesByOrderId(Long orderId) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Order order = session.get(Order.class, orderId);
            Hibernate.initialize(order.getBookCopies());
            return order.getBookCopies();
        } catch (Exception ex) {
            throw new RepositoryException(String.format("%s was not found: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    public Set<BookDamage> findBookDamagesByOrderId(Long orderId) throws RepositoryException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Order order = session.get(Order.class, orderId);
            Hibernate.initialize(order.getBookDamages());
            return order.getBookDamages();
        } catch (Exception ex) {
            throw new RepositoryException(String.format("%s was not found: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }

    @Override
    public Set<BookCopy> findBookCopiesByBookCopiesId(Set<Long> bookCopiesId) {
        return bookCopiesId.stream()
                .map(bookCopyId -> BookCopy.builder().id(bookCopyId).build())
                .collect(Collectors.toSet());
    }

    @Override
    protected String defineSelectAllQuery() {
        return SELECT_ALL_QUERY;
    }

    @Override
    protected String defineUpdateQuery() {
        return UPDATE_QUERY;
    }

    protected void deleteLinks(Session session, Order order) {
        deleteBookCopyLinks(order, order.getBookCopies());
        deleteBookDamage(session, order);
    }

    private void deleteBookCopyLinks(Order order, Set<BookCopy> bookCopies) {
        bookCopies.forEach(bookCopy -> bookCopy.getOrders().remove(order));
    }

    private void deleteBookDamage(Session session, Order order) {
        session.createQuery(DELETE_BOOK_DAMAGE_QUERY)
                .setParameter(ORDER_ID_COLUMN, order.getId())
                .executeUpdate();
    }

    @Override
    protected void constructQuery(Query query, Order order) {
        query.setParameter(ORDER_STATUS_COLUMN, order.getOrderStatus())
                .setParameter(START_DATE_COLUMN, order.getStartDate())
                .setParameter(END_DATE_COLUMN, order.getEndDate())
                .setParameter(PRICE_COLUMN, order.getPrice())
                .setParameter(ID_COLUMN, order.getId());
    }
}