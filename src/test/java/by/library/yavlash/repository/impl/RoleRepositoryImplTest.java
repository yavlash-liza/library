package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.Role;
import by.library.yavlash.exception.RepositoryException;
import by.library.yavlash.repository.BaseRepositoryTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class RoleRepositoryImplTest extends BaseRepositoryTest {
    @Autowired
    private RoleRepositoryImpl roleRepository;

    @Test
    void findByIdTest_shouldReturnTheFirstRoleInDB() throws RepositoryException {
        //given
        Role expected = findRoleForFindById();

        //when
        Role actual = roleRepository.findById(expected.getId());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_shouldReturnListOfAllRoles() throws RepositoryException {
        //given
        List<Role> expected = findRolesForFindAll();

        //when
        List<Role> actual = roleRepository.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldReturnAddedRole() throws RepositoryException {
        //given
        List<Role> expected = findRolesForFindAll();
        Assertions.assertEquals(2, expected.size());

        //when
        Role newRoleActual = Role.builder().roleName("superUser").build();
        boolean isAdded = roleRepository.add(newRoleActual);
        Role newRoleExpected = Role.builder().id(3L).roleName("superUser").build();
        expected.add(newRoleExpected);

        //then
        Assertions.assertTrue(isAdded);
        Assertions.assertEquals(newRoleExpected, newRoleActual);
        Assertions.assertEquals(newRoleExpected, roleRepository.findById(newRoleActual.getId()));
    }

    @Test
    void updateTest_shouldUpdateRole() throws RepositoryException {
        //given
        Role role = Role.builder().id(2L).roleName("superUser").build();

        // when
        boolean isUpdated = roleRepository.update(role);

        //then
        Assertions.assertTrue(isUpdated);
        Assertions.assertEquals(role, roleRepository.findById(role.getId()));
    }

    @Test
    void deleteTest_shouldDeleteRole() throws RepositoryException {
        //given
        Role expected = Role.builder().id(2L).roleName("superUser").build();

        // when
        boolean isDeleted = roleRepository.delete(expected.getId());

        //then
        Assertions.assertTrue(isDeleted);
        Assertions.assertNull(roleRepository.findById(expected.getId()));
    }
}