package by.library.yavlash.repository;

import by.library.yavlash.entity.BookDamage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookDamageRepository extends JpaRepository<BookDamage, Long> {
    @Query("from BookDamage bd left join fetch bd.user u left join fetch bd.bookCopy bc left join fetch bd.order o where bd.id=:id")
    Optional<BookDamage> findById(@Param("id") Long id);
}