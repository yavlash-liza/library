package by.library.yavlash.service;

import by.library.yavlash.dto.UserDto;
import by.library.yavlash.dto.UserListDto;
import by.library.yavlash.dto.UserSaveDto;
import by.library.yavlash.exception.ServiceException;

import java.util.List;

public interface UserService {
    boolean addUser(UserSaveDto userSaveDto) throws ServiceException;
    List<UserListDto> findAllUsers() throws ServiceException;
    UserDto findUserById(Long userId) throws ServiceException;
    boolean deleteUser(Long userId) throws ServiceException;
    boolean updateUser(UserDto userDto) throws ServiceException;
}