package by.library.yavlash.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
public class AuthorDto {
     Long id;
     String firstName;
     String lastName;
     LocalDate birthDate;
     String imagePath;
     List<BookCopyListDto> books;
}