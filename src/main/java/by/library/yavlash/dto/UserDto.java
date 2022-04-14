package by.library.yavlash.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String passportNumber;
    private LocalDate birthDate;
    private List<OrderListDto> orders;
    private List<Long> rolesId;
}