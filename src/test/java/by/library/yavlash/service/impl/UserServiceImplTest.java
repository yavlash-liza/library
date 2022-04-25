package by.library.yavlash.service.impl;

import by.library.yavlash.dto.OrderListDto;
import by.library.yavlash.dto.UserDto;
import by.library.yavlash.dto.UserListDto;
import by.library.yavlash.dto.UserSaveDto;
import by.library.yavlash.entity.Order;
import by.library.yavlash.entity.Role;
import by.library.yavlash.entity.User;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void findByIdTest_shouldReturnTheFirstUserInDB() throws ServiceException {
        //given
        Long id = 1L;
        UserDto expected = UserDto.builder().id(id)
                .rolesId(new ArrayList<>(){{add(2L);}})
                .orders(new ArrayList<>(){{add(OrderListDto.builder().id(2L).build());}})
                .build();
        Set<Order> orders = new HashSet<>() {{add(Order.builder().id(2L).build());}};
        Set<Role> roles = new HashSet<>() {{add(Role.builder().id(2L).build());}};

        //when
        when(userRepository.findById(id)).thenReturn(Optional.of(User.builder().id(id).roles(roles).orders(orders).build()));
        UserDto actual = userService.findById(id);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_shouldReturnListOfAllUsers() throws ServiceException {
        //given
        List<UserListDto> expected = new ArrayList<>() {{
            add(UserListDto.builder().id(1L).build());
            add(UserListDto.builder().id(2L).build());
        }};

        //when
        when(userRepository.findAll()).thenReturn(new ArrayList<>() {{
            add(User.builder().id(1L).build());
            add(User.builder().id(2L).build());
        }});
        List<UserListDto> actual = userService.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldAddUser() throws ServiceException {
        //given && when
        boolean actual = userService.add(UserSaveDto.builder().roleId(new ArrayList<>() {{add(1L);}}).build());

        //then
        Assertions.assertTrue(actual);
    }

    @Test
    void updateTest_shouldUpdateUser() throws ServiceException {
        //given
        List<Long> roleList = new ArrayList<>() {{add(1L);}};
        UserDto expected = UserDto.builder().id(4L).firstName("Sergei").lastName("Smirnov").rolesId(roleList).build();

        //when
        boolean actual = userService.update(expected);

        //then
        Assertions.assertTrue(actual);
    }

    @Test
    void deleteTest_shouldDeleteUser() throws ServiceException {
        //given
        Long id = 3L;
        User expected = User.builder().id(id).orders(new HashSet<>()).bookDamages(new HashSet<>()).build();

        //when
        when(userRepository.findById(id)).thenReturn(Optional.of(expected));
        when(userRepository.save(expected)).thenReturn(expected);
        boolean actual = userService.delete(id);

        //then
        Assertions.assertTrue(actual);
    }
}