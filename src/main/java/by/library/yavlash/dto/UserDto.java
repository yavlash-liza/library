package by.library.yavlash.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;
import java.util.List;

@Jacksonized
@Builder
@Value
public class UserDto {
     Long id;
     String firstName;
     String lastName;
     String email;
     String address;
     String passportNumber;
     LocalDate birthDate;
     List<OrderListDto> orders;
     List<Long> rolesId;
}