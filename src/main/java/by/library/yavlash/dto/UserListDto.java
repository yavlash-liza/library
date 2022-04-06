package by.library.yavlash.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class UserListDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private Long roleId;
}
