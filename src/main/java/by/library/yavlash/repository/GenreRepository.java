package by.library.yavlash.repository;

import by.library.yavlash.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Query("from Genre g left join fetch g.books b left join fetch b.bookCopies bc where g.id=:id")
    Optional<Genre> findById(@Param("id") Long id);
}