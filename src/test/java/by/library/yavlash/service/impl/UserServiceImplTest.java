package by.library.yavlash.service.impl;

import by.library.yavlash.dto.OrderListDto;
import by.library.yavlash.dto.UserDto;
import by.library.yavlash.dto.UserListDto;
import by.library.yavlash.dto.UserSaveDto;
import by.library.yavlash.entity.Order;
import by.library.yavlash.entity.Role;
import by.library.yavlash.entity.User;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.mapper.UserMapper;
import by.library.yavlash.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
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

    @Spy
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

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
        Long id = 3L;
        User user = User.builder().id(id).build();
        UserSaveDto expected = UserSaveDto.builder().id(id).firstName("Sergei").lastName("Smirnov").build();

        //when
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
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
        boolean actual = userService.softDelete(id);

        //then
        Assertions.assertTrue(actual);
    }
}