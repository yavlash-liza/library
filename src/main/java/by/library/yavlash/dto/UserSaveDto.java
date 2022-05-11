package by.library.yavlash.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;
import java.util.List;

@Jacksonized
@Builder
@Value
public class UserSaveDto {
     Long id;
     String firstName;
     String lastName;
     String passportNumber;
     String email;
     String address;
     LocalDate birthDate;
     List<Long> roleId;
}