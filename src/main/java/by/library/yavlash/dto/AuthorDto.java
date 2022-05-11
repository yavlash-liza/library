package by.library.yavlash.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;
import java.util.List;

@Jacksonized
@Builder
@Value
public class AuthorDto {
     Long id;
     String firstName;
     String lastName;
     LocalDate birthDate;
     String imagePath;
     List<BookCopyListDto> books;
}