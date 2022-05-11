package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.User;
import by.library.yavlash.repository.BaseRepositoryTest;
import by.library.yavlash.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

class UserRepositoryTest extends BaseRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByIdTest_shouldReturnTheFirstUserInDB() {
        //given
        User user = findUserForFindById();
        Optional<User> expected = Optional.of(user);

        //when
        Optional<User> actual = userRepository.findById(user.getId());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_shouldReturnListOfAllUsers() {
        //given
        List<User> expected = findUsersForFindAll();

        //when
        List<User> actual = userRepository.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldReturnAddedUser() {
        //given
        List<User> expected = findUsersForFindAll();
        Assertions.assertEquals(5, expected.size());

        //when
        User newUserActual = User.builder().firstName("sergei").lastName("take").passportNumber("1645").email("email235@mail.com").password("$2a$12$puiOOWl2E7oexBqiw3WnL.dbvpef2.tmrhBtw6116hS.uP61poDk2").address("address123").birthDate(LocalDate.of(2002, 5, 5)).build();
        User isAdded = userRepository.save(newUserActual);
        User newUserExpected = User.builder().id(6L).firstName("sergei").lastName("take").passportNumber("1645").email("email235@mail.com").address("address123").password("$2a$12$puiOOWl2E7oexBqiw3WnL.dbvpef2.tmrhBtw6116hS.uP61poDk2").birthDate(LocalDate.of(2002, 5, 5)).build();
        expected.add(newUserExpected);

        //then
        Assertions.assertNotNull(isAdded);
        Assertions.assertEquals(newUserExpected, newUserActual);
        Assertions.assertEquals(Optional.of(newUserExpected), userRepository.findById(newUserActual.getId()));
    }

    @Test
    void updateTest_shouldUpdateUser() {
        //given
        User user = User.builder().id(2L).firstName("sergei").lastName("take").passportNumber("1645").email("email235@mail.com").address("address123").password("$2a$12$puiOOWl2E7oexBqiw3WnL.dbvpef2.tmrhBtw6116hS.uP61poDk2").birthDate(LocalDate.of(2002, 5, 5)).build();

        // when
        User isUpdated = userRepository.save(user);
        //then
        Assertions.assertNotNull(isUpdated);
        Assertions.assertEquals(Optional.of(user), userRepository.findById(user.getId()));
    }
}