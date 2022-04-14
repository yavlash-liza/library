package by.library.yavlash.converter;

import by.library.yavlash.dto.RoleDto;
import by.library.yavlash.entity.Role;

import java.util.List;
import java.util.stream.Collectors;

public class RoleConverter {
    public static RoleDto toDto(Role role) {
        if (role == null) {
            return null;
        }
        return RoleDto.builder()
                .id(role.getId())
                .roleName(role.getRoleName())
                .build();
    }

    public static List<RoleDto> toListDto(List<Role> roles) {
        if (roles == null) {
            return null;
        }
        return roles.stream()
                .map(RoleConverter::toDto)
                .collect(Collectors.toList());
    }
}