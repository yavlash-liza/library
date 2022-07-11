package by.library.yavlash.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthorListDto {
     Long id;
     String firstName;
     String lastName;
}