//package by.library.yavlash.repository.impl;
//
//import by.library.yavlash.entity.Role;
//import by.library.yavlash.exception.RepositoryException;
//import by.library.yavlash.repository.BaseRepositoryTest;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//class RoleRepositoryImplTest extends BaseRepositoryTest {
//    private final RoleRepositoryImpl roleRepository;
//
//    public RoleRepositoryImplTest() {
//        roleRepository = new RoleRepositoryImpl(getDataSource());
//    }
//
//    @Test
//    void findByIdTest_shouldReturnTheFirstRoleInDB() throws RepositoryException {
//        //given
//        Role expected = findRoleForFindById();
//
//        //when
//        Role actual = roleRepository.findById(expected.getId());
//
//        //then
//        Assertions.assertEquals(expected, actual);
//    }
//
//    @Test
//    void findAllTest_shouldReturnListOfAllRoles() throws RepositoryException {
//        //given
//        List<Role> expected = findRolesForFindAll();
//
//        //when
//        List<Role> actual = roleRepository.findAll();
//
//        //then
//        Assertions.assertEquals(expected, actual);
//    }
//
//    @Test
//    void addTest_shouldReturnAddedRole() throws RepositoryException {
//        //given
//        Role expected = Role.builder().id(3L).roleName("superUser").build();
//        Role actual = Role.builder().roleName("superUser").build();
//
//        //when
//        actual = roleRepository.add(actual);
//
//        //then
//        Assertions.assertEquals(expected, actual);
//        Assertions.assertEquals(expected, roleRepository.findById(expected.getId()));
//    }
//
//    @Test
//    void updateTest_shouldUpdateRole() throws RepositoryException {
//        //given
//        Role role = Role.builder().id(2L).roleName("superUser").build();
//
//        // when
//        boolean isUpdated = roleRepository.update(role);
//
//        //then
//        Assertions.assertTrue(isUpdated);
//        Assertions.assertEquals(role, roleRepository.findById(role.getId()));
//    }
//
//    @Test
//    void deleteTest_shouldDeleteRole() throws RepositoryException {
//        //given
//        Long roleId = 1L;
//
//        // when
//        boolean isDeleted = roleRepository.delete(roleId);
//
//        //then
//        Assertions.assertTrue(isDeleted);
//    }
//}