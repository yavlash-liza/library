package by.library.yavlash.controller;

import by.library.yavlash.dto.UserDto;
import by.library.yavlash.dto.UserListDto;
import by.library.yavlash.dto.UserSaveDto;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.service.UserService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) throws ServiceException {
        return userService.findById(id);
    }

    @GetMapping
    public List<UserListDto> findAll() throws ServiceException {
        return userService.findAll();
    }

    @PostMapping
    public boolean addUser(@RequestBody UserSaveDto user) throws ServiceException {
        return userService.add(user);
    }

    @PutMapping
    public boolean updateUser(@RequestBody UserDto userDto) throws ServiceException {
        return userService.update(userDto);
    }

    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable Long id) throws ServiceException {
        return userService.delete(id);
    }
}