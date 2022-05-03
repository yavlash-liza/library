package by.library.yavlash.security;


import lombok.Data;

@Data
public class AuthRequest {
    String username;
    String password;
}