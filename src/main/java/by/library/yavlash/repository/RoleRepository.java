package by.library.yavlash.repository;

import by.library.yavlash.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("from Role r left join fetch r.users where r.id=:id")
    Optional<Role> findById(@Param("id") Long id);
}