package by.library.yavlash.repository;

import by.library.yavlash.entity.Order;
import by.library.yavlash.entity.Role;
import by.library.yavlash.entity.User;
import by.library.yavlash.exception.RepositoryException;

import java.util.Set;

public interface UserRepository extends BaseRepository<User> {
    Set<Role> findRolesByUserId(Long userId) throws RepositoryException;
    Set<Order> findOrdersByUserId(Long userId) throws RepositoryException;
    Set<Role> findRolesByRolesId(Set<Long> rolesId);
}