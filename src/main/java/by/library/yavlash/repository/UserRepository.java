package by.library.yavlash.repository;

import by.library.yavlash.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("from User u left join fetch u.roles left join fetch u.bookDamages left join fetch u.orders where u.id=:id")
    Optional<User> findById(@Param("id") Long id);
}