package by.library.yavlash.repository;

import by.library.yavlash.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("from Author a left join fetch a.books b left join fetch b.bookCopies bc where a.id=:id")
    Optional<Author> findById(@Param("id") Long id);
    Page<Author> findAllByDeleted(boolean deleted, Pageable pageable);
    Page<Author> findAllByDeletedAndLastName(boolean deleted, String lastName, Pageable pageable);
}