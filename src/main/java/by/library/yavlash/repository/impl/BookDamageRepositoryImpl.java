package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.BookDamage;
import by.library.yavlash.repository.BookDamageRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class BookDamageRepositoryImpl extends AbstractRepositoryImpl<BookDamage> implements BookDamageRepository {
    private static final String IMAGE_PATH_COLUMN = "imagePath";
    private static final String DAMAGE_DESCRIPTION_COLUMN = "damageDescription";

    private static final String SELECT_ALL_QUERY = "from BookDamage";
    private static final String UPDATE_QUERY =
            "update BookDamage set imagePath=:imagePath, damageDescription=:damageDescription " +
                    " where id=:id";

    public BookDamageRepositoryImpl() {
        super(BookDamage.class);
    }

    @Override
    protected String defineSelectAllQuery() {
        return SELECT_ALL_QUERY;
    }

    @Override
    protected String defineUpdateQuery() {
        return UPDATE_QUERY;
    }

    protected void deleteLinks(Session session, BookDamage bookDamage) {
    }

    @Override
    protected void constructQuery(Query query, BookDamage element) {
        query.setParameter(IMAGE_PATH_COLUMN, element.getImagePath())
                .setParameter(DAMAGE_DESCRIPTION_COLUMN, element.getDamageDescription());
    }
}