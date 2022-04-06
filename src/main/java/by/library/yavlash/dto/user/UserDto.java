package by.library.yavlash.dto.user;

import by.library.yavlash.dto.RoleDto;
import by.library.yavlash.dto.order.OrderDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private LocalDate birthDate;
    private Set<OrderDto> orders;
    private Set<RoleDto> roleName;
}