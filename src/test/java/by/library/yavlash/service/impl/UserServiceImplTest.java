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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void findUserById() throws ServiceException {
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
        UserDto actual = userService.findUserById(id);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllUsers() throws ServiceException {
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
        List<UserListDto> actual = userService.findAllUsers();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addUser() throws ServiceException {
        //given && when
        boolean actual = userService.addUser(UserSaveDto.builder().roleId(new ArrayList<>() {{add(1L);}}).build());

        //then
        Assertions.assertTrue(actual);
    }

    @Test
    void updateUser() throws ServiceException {
        //given
        List<Long> roleList = new ArrayList<>() {{add(1L);}};
        UserDto expected = UserDto.builder().id(4L).firstName("Sergei").lastName("Smirnov").rolesId(roleList).build();

        //when
        boolean actual = userService.updateUser(expected);

        //then
        Assertions.assertTrue(actual);
    }

    @Test
    void deleteUser() throws ServiceException {
        //given
        Long id = 3L;

        //when
        boolean actual = userService.deleteUser(id);

        //then
        Assertions.assertTrue(actual);
    }
}