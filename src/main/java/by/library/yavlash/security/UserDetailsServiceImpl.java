package by.library.yavlash.security;

import by.library.yavlash.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(user -> JwtUser.builder()
                        .id(user.getId())
                        .username(user.getEmail())
                        .password(user.getPassword())
                        .roles(user.getRoles())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("No user found with " + email));
    }
}