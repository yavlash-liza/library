package by.library.yavlash.mapper;

import by.library.yavlash.dto.RoleDto;
import by.library.yavlash.entity.Role;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    List<RoleDto> toListDto(List<Role> role);

    default Set<Role> fromListLong(List<Long> roleId) {
        return roleId.stream().map(id -> Role.builder().id(id).build())
                .collect(Collectors.toSet());
    }
}