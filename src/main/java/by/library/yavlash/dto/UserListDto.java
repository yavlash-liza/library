package by.library.yavlash.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
public class UserListDto {
    Long id;
    String firstName;
    String lastName;
    String email;
    String address;
}
