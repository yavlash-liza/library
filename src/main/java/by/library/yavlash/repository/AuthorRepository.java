package by.library.yavlash.repository;

import by.library.yavlash.entity.Author;
import by.library.yavlash.entity.Book;
import by.library.yavlash.exception.RepositoryException;

import java.util.Set;

//public interface AuthorRepository extends JpaRepository<Author,Long> {
//    Set<Book> findByIdIs(Long id) throws RepositoryException;
//}
public interface AuthorRepository extends BaseRepository<Author>{
    Set<Book> findByIdIs(Long id) throws RepositoryException;
}