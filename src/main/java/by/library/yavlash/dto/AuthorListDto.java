package by.library.yavlash.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthorListDto {
    private Long id;
    private String firstName;
    private String lastName;
}