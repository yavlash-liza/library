package by.library.yavlash.repository;

import by.library.yavlash.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("from Order o left join fetch o.bookCopies left join fetch o.bookDamages left join fetch o.user where o.id=:id")
    Optional<Order> findById(@Param("id") Long id);
}