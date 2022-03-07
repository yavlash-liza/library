package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.BookCopy;
import by.library.yavlash.entity.Order;
import by.library.yavlash.repository.BookCopyRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Set;

public class BookCopyRepositoryImpl extends AbstractRepositoryImpl<BookCopy> implements BookCopyRepository {
    private static final String BOOK_COPY_STATUS_COLUMN = "status";
    private static final String REGISTRATION_DATE_COLUMN = "registrationDate";
    private static final String IMAGE_PATH_COLUMN = "imagePath";
    private static final String PRICE_PER_DAY_COLUMN = "pricePerDay";
    private static final String BOOK_COPY_ID_COLUMN = "bookCopyId";

    private static final String SELECT_ALL_QUERY = "from BookCopy";
    private static final String UPDATE_QUERY =
            "update BookCopy set status=:status, registrationDate=:registrationDate, " +
                    " imagePath=:imagePath, pricePerDay=:pricePerDay " +
                    " where id=:id";

    private static final String DELETE_BOOK_DAMAGE_QUERY = "delete BookDamage bd where bd.bookCopy.id=:bookCopyId";

    public BookCopyRepositoryImpl() {
        super(BookCopy.class);
    }

    @Override
    protected String defineSelectAllQuery() {
        return SELECT_ALL_QUERY;
    }

    @Override
    protected String defineUpdateQuery() {
        return UPDATE_QUERY;
    }

    protected void deleteLinks(Session session, BookCopy bookCopy) {
        deleteOrderLinks(bookCopy, bookCopy.getOrders());
        deleteBookDamage(session, bookCopy);
    }

    private void deleteOrderLinks(BookCopy bookCopy, Set<Order> orders) {
        orders.forEach(order -> order.getBookCopies().remove(bookCopy));
    }

    private void deleteBookDamage(Session session, BookCopy bookCopy) {
        session.createQuery(DELETE_BOOK_DAMAGE_QUERY)
                .setParameter(BOOK_COPY_ID_COLUMN, bookCopy.getId())
                .executeUpdate();
    }

    @Override
    protected void constructQuery(Query query, BookCopy bookCopy) {
        query.setParameter(BOOK_COPY_STATUS_COLUMN, bookCopy.getStatus())
                .setParameter(REGISTRATION_DATE_COLUMN, bookCopy.getRegistrationDate())
                .setParameter(IMAGE_PATH_COLUMN, bookCopy.getImagePath())
                .setParameter(PRICE_PER_DAY_COLUMN, bookCopy.getPricePerDay());
    }
}