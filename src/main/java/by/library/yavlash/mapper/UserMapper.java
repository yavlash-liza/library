package by.library.yavlash.mapper;

import by.library.yavlash.dto.RoleIdDto;
import by.library.yavlash.dto.UserDto;
import by.library.yavlash.dto.UserListDto;
import by.library.yavlash.dto.UserSaveDto;
import by.library.yavlash.entity.Role;
import by.library.yavlash.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "rolesId", source = "user.roles")
    UserDto toDto(User user);
    @Mapping(target = "rolesId.id", source = "roles.role.id")
    List<RoleIdDto> toListLong(Set<Role> roles);
    UserListDto toListDto(User user);
    List<UserListDto> toListDtos(List<User> user);
    User fromSaveDto(UserSaveDto userSaveDto);
    User fromDto(UserDto userDto);
}
