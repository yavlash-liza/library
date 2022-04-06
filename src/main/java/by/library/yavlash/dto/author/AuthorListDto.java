package by.library.yavlash.dto.author;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class AuthorListDto {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
}