package by.library.yavlash.service;

import by.library.yavlash.dto.UserDto;
import by.library.yavlash.dto.UserListDto;
import by.library.yavlash.dto.UserSaveDto;
import by.library.yavlash.exception.ServiceException;

import java.util.List;

public interface UserService {
    UserDto findById(Long userId) throws ServiceException;
    List<UserListDto> findAll() throws ServiceException;
    boolean add(UserSaveDto userSaveDto) throws ServiceException;
    boolean update(UserSaveDto userSaveDto) throws ServiceException;
    boolean delete(Long userId) throws ServiceException;
}