package by.library.yavlash.service.impl;

import by.library.yavlash.dto.UserDto;
import by.library.yavlash.dto.UserListDto;
import by.library.yavlash.dto.UserSaveDto;
import by.library.yavlash.entity.User;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.mapper.UserMapper;
import by.library.yavlash.repository.UserRepository;
import by.library.yavlash.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto findUserById(Long userId) throws ServiceException {
        try {
            User user = userRepository.findById(userId);
            user.setRoles(userRepository.findRolesByUserId(userId));
            user.setOrders(userRepository.findOrdersByUserId(userId));
            return userMapper.toDto(user);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not found: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    @Override
    public List<UserListDto> findAllUsers() throws ServiceException {
        try {
            List<User> users = userRepository.findAll();
            return userMapper.toListDtos(users);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s were not found: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    @Override
    public boolean addUser(UserSaveDto userSaveDto) throws ServiceException {
        try {
            User user = userMapper.fromSaveDto(userSaveDto);
            userRepository.add(user);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not added: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    @Override
    public boolean updateUser(UserDto userDto) throws ServiceException {
        try {
            User user = userMapper.fromDto(userDto);
            userRepository.update(user);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not updated: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    @Override
    public boolean deleteUser(Long userId) throws ServiceException {
        try {
            userRepository.delete(userId);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not deleted: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }
}