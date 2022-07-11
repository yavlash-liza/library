package by.library.yavlash.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
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