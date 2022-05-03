package by.library.yavlash.security;


import lombok.Data;

@Data
public class AuthRequestDto {
    String username;
    String password;
}