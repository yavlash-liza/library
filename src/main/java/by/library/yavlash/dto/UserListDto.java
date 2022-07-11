package by.library.yavlash.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserListDto {
    Long id;
    String firstName;
    String lastName;
    String email;
    String address;
}
