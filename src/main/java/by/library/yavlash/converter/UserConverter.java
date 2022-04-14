package by.library.yavlash.converter;

import by.library.yavlash.dto.UserDto;
import by.library.yavlash.dto.UserListDto;
import by.library.yavlash.dto.UserSaveDto;
import by.library.yavlash.entity.BaseEntity;
import by.library.yavlash.entity.Role;
import by.library.yavlash.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserConverter {
    public static UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .passportNumber(user.getPassportNumber())
                .email(user.getEmail())
                .address(user.getAddress())
                .birthDate(user.getBirthDate())
                .rolesId(toListLong(user.getRoles()))
                .orders(OrderConverter.toOrderListDtos(new ArrayList<>(user.getOrders())))
                .build();
    }

    public static List<Long> toListLong(Set<Role> roles) {
        if (roles == null) {
            return null;
        }
        return roles.stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList());
    }

    public static UserListDto toListDto(User user) {
        if (user == null) {
            return null;
        }
        return UserListDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .address(user.getAddress())
                .build();
    }

    public static List<UserListDto> toListDtos(List<User> users) {
        if (users == null) {
            return null;
        }
        return users.stream()
                .map(UserConverter::toListDto)
                .collect(Collectors.toList());
    }

    public static User fromSaveDto(UserSaveDto userSaveDto) {
        if (userSaveDto == null) {
            return null;
        }
        return User.builder()
                .id(userSaveDto.getId())
                .firstName(userSaveDto.getFirstName())
                .lastName(userSaveDto.getLastName())
                .passportNumber(userSaveDto.getPassportNumber())
                .email(userSaveDto.getEmail())
                .address(userSaveDto.getAddress())
                .birthDate(userSaveDto.getBirthDate())
                .roles(fromLongToSetRoles(userSaveDto.getRoleId()))
                .build();
    }

    public static User fromDto(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        return User.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .passportNumber(userDto.getPassportNumber())
                .email(userDto.getEmail())
                .address(userDto.getAddress())
                .birthDate(userDto.getBirthDate())
                .orders(OrderConverter.orderListDtoListToOrderSet(userDto.getOrders()))
                .roles(fromLongToSetRoles(userDto.getRolesId()))
                .build();
    }

    private static Set<Role> fromLongToSetRoles(List<Long> rolesId) {
        return rolesId.stream()
                .map(roleId -> Role.builder().id(roleId).build())
                .collect(Collectors.toSet());
    }
}