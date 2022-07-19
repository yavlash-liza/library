package by.library.yavlash.repository;

import by.library.yavlash.entity.BookCopy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    @Query("from BookCopy bc left join fetch bc.bookDamages bd left join fetch bc.book b left join fetch b.authors a left join fetch b.genres g where bc.id=:id")
    Optional<BookCopy> findById(@Param("id") Long id);
    Page<BookCopy> findAllByDeleted(boolean deleted, Pageable pageable);
    Page<BookCopy> findAllByDeletedAndBook_Title(boolean deleted, String title, Pageable pageable);
}