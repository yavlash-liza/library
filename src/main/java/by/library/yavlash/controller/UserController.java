package by.library.yavlash.controller;

import by.library.yavlash.dto.UserDto;
import by.library.yavlash.dto.UserListDto;
import by.library.yavlash.dto.UserSaveDto;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAnyRole('admin', 'user')")
    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Long id) throws ServiceException {
        return userService.findById(id);
    }

    @PreAuthorize("hasRole({'admin'})")
    @GetMapping
    public List<UserListDto> findAll() throws ServiceException {
        return userService.findAll();
    }

    @PostMapping
    public boolean add(@RequestBody UserSaveDto user) throws ServiceException {
        return userService.add(user);
    }

    @PreAuthorize("hasAnyRole('admin', 'user')")
    @PutMapping
    public boolean update(@RequestBody UserSaveDto userSaveDto) throws ServiceException {
        return userService.update(userSaveDto);
    }

    @PreAuthorize("hasRole({'admin'})")
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) throws ServiceException {
        return userService.delete(id);
    }
}