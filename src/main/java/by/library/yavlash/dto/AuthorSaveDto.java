package by.library.yavlash.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Jacksonized
@Builder
@Value
public class AuthorSaveDto {
     Long id;
     String firstName;
     String lastName;
     LocalDate birthDate;
     String imagePath;
}