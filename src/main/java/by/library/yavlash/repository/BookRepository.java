package by.library.yavlash.repository;

import by.library.yavlash.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("from Book b left join fetch b.bookCopies bc left join fetch b.authors a left join fetch b.genres g where b.id=:id")
    Optional<Book> findById(@Param("id") Long id);
}