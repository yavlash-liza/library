package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.BookDamage;
import by.library.yavlash.exception.RepositoryException;
import by.library.yavlash.repository.BookDamageRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class BookDamageRepositoryImpl extends AbstractRepositoryImpl<BookDamage> implements BookDamageRepository {
    private static final String IMAGE_PATH_COLUMN = "imagePath";
    private static final String DAMAGE_DESCRIPTION_COLUMN = "damageDescription";

    private static final String SELECT_BY_ID = " from BookDamage bd left join fetch bd.user u " +
            " left join fetch bd.bookCopy left join fetch bd.order where bd.id=:id ";
    private static final String SELECT_ALL_QUERY = "from BookDamage";
    private static final String UPDATE_QUERY =
            "update BookDamage set imagePath=:imagePath, damageDescription=:damageDescription " +
                    " where id=:id";

    public BookDamageRepositoryImpl(SessionFactory sessionFactory) {
        super(BookDamage.class, sessionFactory);
    }

    @Override
    public BookDamage findById(Long id) throws RepositoryException {
        try (Session session = getSessionFactory().openSession()) {
            return session.createQuery(SELECT_BY_ID, BookDamage.class)
                    .setParameter(ID_COLUMN, id)
                    .getSingleResult();
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

    protected void deleteLinks(Session session, BookDamage bookDamage) {
    }

    @Override
    protected void constructQuery(Query query, BookDamage bookDamage) {
        query.setParameter(IMAGE_PATH_COLUMN, bookDamage.getImagePath())
                .setParameter(DAMAGE_DESCRIPTION_COLUMN, bookDamage.getDamageDescription())
                .setParameter(ID_COLUMN, bookDamage.getId());
    }
}