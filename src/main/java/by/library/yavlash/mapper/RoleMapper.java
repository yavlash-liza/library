package by.library.yavlash.mapper;

import by.library.yavlash.dto.RoleDto;
import by.library.yavlash.entity.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto toDto(Role role);
    List<RoleDto> toListDto(List<Role> role);
}