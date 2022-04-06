package by.library.yavlash.mapper;

import by.library.yavlash.dto.UserDto;
import by.library.yavlash.dto.UserListDto;
import by.library.yavlash.dto.UserSaveDto;
import by.library.yavlash.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    UserListDto toListDto(User user);
    List<UserListDto> toListDtos(List<User> user);
    User fromSaveDto(UserSaveDto userSaveDto);
    User fromDto(UserDto userDto);
}
