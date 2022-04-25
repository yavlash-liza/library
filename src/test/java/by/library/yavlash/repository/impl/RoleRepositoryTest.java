package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.Role;
import by.library.yavlash.repository.BaseRepositoryTest;
import by.library.yavlash.repository.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

class RoleRepositoryTest extends BaseRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    void findByIdTest_shouldReturnTheFirstRoleInDB() {
        //given
        Role role = findRoleForFindById();
        Optional<Role> expected = Optional.of(role);

        //when
        Optional<Role>  actual = roleRepository.findById(role.getId());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_shouldReturnListOfAllRoles() {
        //given
        List<Role> expected = findRolesForFindAll();

        //when
        List<Role> actual = roleRepository.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldReturnAddedRole() {
        //given
        List<Role> expected = findRolesForFindAll();
        Assertions.assertEquals(2, expected.size());

        //when
        Role newRoleActual = Role.builder().roleName("superUser").build();
        Role isAdded = roleRepository.save(newRoleActual);
        Role newRoleExpected = Role.builder().id(3L).roleName("superUser").build();
        expected.add(newRoleExpected);

        //then
        Assertions.assertNotNull(isAdded);
        Assertions.assertEquals(newRoleExpected, newRoleActual);
        Assertions.assertEquals(Optional.of(newRoleExpected), roleRepository.findById(newRoleActual.getId()));
    }

    @Test
    void updateTest_shouldUpdateRole() {
        //given
        Role role = Role.builder().id(2L).roleName("superUser").build();

        // when
        Role isUpdated = roleRepository.save(role);

        //then
        Assertions.assertNotNull(isUpdated);
        Assertions.assertEquals(Optional.of(role), roleRepository.findById(role.getId()));
    }
}