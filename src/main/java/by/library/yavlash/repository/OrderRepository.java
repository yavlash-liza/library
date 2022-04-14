package by.library.yavlash.repository;

import by.library.yavlash.entity.BookCopy;
import by.library.yavlash.entity.BookDamage;
import by.library.yavlash.entity.Order;
import by.library.yavlash.entity.User;
import by.library.yavlash.exception.RepositoryException;

import java.util.Set;

public interface OrderRepository extends BaseRepository<Order> {
    User findUserByOrderId(Long orderId) throws RepositoryException;
    Set<BookCopy> findBookCopiesByOrderId(Long orderId) throws RepositoryException;
    Set<BookDamage> findBookDamagesByOrderId(Long orderId) throws RepositoryException;
}