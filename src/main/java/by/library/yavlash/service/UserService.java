package by.library.yavlash.service;

import by.library.yavlash.dto.UserDto;
import by.library.yavlash.dto.UserListDto;
import by.library.yavlash.dto.UserSaveDto;
import by.library.yavlash.exception.ServiceException;

import java.util.List;

public interface UserService {
    void addUser(UserSaveDto userSaveDto) throws ServiceException;
    List<UserListDto> findAllUsers() throws ServiceException;
    UserDto findUserById(Long userId) throws ServiceException;
    void deleteUser(Long userId) throws ServiceException;
    void updateUser(UserDto userDto) throws ServiceException;
}