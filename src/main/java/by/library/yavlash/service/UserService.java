package by.library.yavlash.service;

import by.library.yavlash.dto.UserDto;
import by.library.yavlash.dto.UserListDto;
import by.library.yavlash.dto.UserSaveDto;

import java.util.List;

public interface UserService {
    UserDto findById(Long userId);
    List<UserListDto> findAll();
    List<UserListDto> findListUsers(int page, int size, boolean deleted);
    List<UserListDto> findListUsersBySearch(int page, int size, boolean deleted, String search);
    boolean add(UserSaveDto userSaveDto);
    boolean update(UserSaveDto userSaveDto);
    boolean softDelete(Long userId);
}