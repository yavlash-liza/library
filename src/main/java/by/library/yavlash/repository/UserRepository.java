package by.library.yavlash.repository;

import by.library.yavlash.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("from User u left join fetch u.roles left join fetch u.bookDamages left join fetch u.orders where u.id=:id")
    Optional<User> findById(Long id);

    @Query("from User u left join fetch u.roles r left join fetch r.authorities where u.email=:email")
    Optional<User> findByEmail(String email);
}