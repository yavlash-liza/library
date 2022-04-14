package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.User;
import by.library.yavlash.exception.RepositoryException;
import by.library.yavlash.repository.BaseRepositoryTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class UserRepositoryImplTest extends BaseRepositoryTest {
    private final UserRepositoryImpl userRepository;

    public UserRepositoryImplTest() {
        userRepository = new UserRepositoryImpl();
    }

    @Test
    public void findByIdTest_shouldReturnTheFirstUserInDB() throws RepositoryException {
        //given
        User expected = findUserForFindById();

        //when
        User actual = userRepository.findById(expected.getId());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_shouldReturnListOfAllUsers() throws RepositoryException {
        //given
        List<User> expected = findUsersForFindAll();

        //when
        List<User> actual = userRepository.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldReturnAddedUser() throws RepositoryException {
        //given
        List<User> expected = findUsersForFindAll();
        Assertions.assertEquals(5, expected.size());

        //when
        User newUserActual = User.builder().firstName("sergei").lastName("take").passportNumber("1645").email("email235").address("address123").birthDate(LocalDate.of(2002, 5, 5)).build();
        boolean isAdded = userRepository.add(newUserActual);
        User newUserExpected = User.builder().id(6L).firstName("sergei").lastName("take").passportNumber("1645").email("email235").address("address123").birthDate(LocalDate.of(2002, 5, 5)).build();
        expected.add(newUserExpected);

        //then
        Assertions.assertTrue(isAdded);
        Assertions.assertEquals(newUserExpected, newUserActual);
        Assertions.assertEquals(newUserExpected, userRepository.findById(newUserActual.getId()));
    }

    @Test
    void updateTest_shouldUpdateUser() throws RepositoryException {
        //given
        User user = User.builder().id(2L).firstName("sergei").lastName("take").passportNumber("1645").email("email235").address("address123").birthDate(LocalDate.of(2002, 5, 5)).build();

        // when
        boolean isUpdated = userRepository.update(user);

        //then
        Assertions.assertTrue(isUpdated);
        Assertions.assertEquals(user, userRepository.findById(user.getId()));
    }

    @Test
    public void deleteTest_shouldDeleteUser() throws RepositoryException {
        //given
        User expected = User.builder().id(2L).firstName("sergei").lastName("take").passportNumber("1645").email("email235").address("address123").birthDate(LocalDate.of(2002, 5, 5)).build();

        // when
        boolean isDeleted = userRepository.delete(expected.getId());

        //then
        Assertions.assertTrue(isDeleted);
        Assertions.assertThrows(RepositoryException.class, () -> userRepository.findById(expected.getId()));
    }
}