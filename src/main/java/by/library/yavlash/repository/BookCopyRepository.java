package by.library.yavlash.repository;

import by.library.yavlash.entity.Book;
import by.library.yavlash.entity.BookCopy;
import by.library.yavlash.entity.BookDamage;
import by.library.yavlash.entity.Order;
import by.library.yavlash.exception.RepositoryException;

import java.util.Set;

public interface BookCopyRepository extends BaseRepository<BookCopy> {
    Book findBookByBookCopyId(Long bookCopyId) throws RepositoryException;
    Set<BookDamage> findBookDamagesByBookCopyId(Long bookCopyId) throws RepositoryException;
    Set<Order> findOrdersByBookCopyId(Long bookCopyId) throws RepositoryException;
}