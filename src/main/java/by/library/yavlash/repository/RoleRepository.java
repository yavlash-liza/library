package by.library.yavlash.repository;

import by.library.yavlash.entity.Role;
import by.library.yavlash.entity.User;
import by.library.yavlash.exception.RepositoryException;

import java.util.Set;

public interface RoleRepository extends BaseRepository<Role> {
    Set<User> findUsersByRoleId(Long roleId) throws RepositoryException;
}