package by.library.yavlash.mapper;

import by.library.yavlash.dto.UserDto;
import by.library.yavlash.dto.UserListDto;
import by.library.yavlash.dto.UserSaveDto;
import by.library.yavlash.entity.Role;
import by.library.yavlash.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "rolesId", source = "user.roles")
    UserDto toDto(User user);

    @Mapping(target = "roles", source = "userSaveDto.roleId")
    User fromSaveDto(UserSaveDto userSaveDto);

    List<UserListDto> toListDto(List<User> user);

    default List<Long> toLongList(Set<Role> roles) {
        return roles.stream().map(Role::getId)
                .collect(Collectors.toList());
    }

    default Set<Role> fromLongList(List<Long> roleId) {
        return roleId.stream().map(id -> Role.builder().id(id).build())
                .collect(Collectors.toSet());
    }
}
