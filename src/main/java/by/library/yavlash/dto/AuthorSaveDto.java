package by.library.yavlash.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class AuthorSaveDto {
     Long id;
     String firstName;
     String lastName;
     LocalDate birthDate;
     String imagePath;
}