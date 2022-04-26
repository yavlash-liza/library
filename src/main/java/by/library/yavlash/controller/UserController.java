package by.library.yavlash.controller;

import by.library.yavlash.dto.UserDto;
import by.library.yavlash.dto.UserListDto;
import by.library.yavlash.dto.UserSaveDto;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
        } catch (ServiceException exception) {
            return getResponseEntityForException(exception);
        }
    }

    @GetMapping
    public ResponseEntity<List<UserListDto>> findAll() throws ServiceException {
        List<UserListDto> userListDtos = userService.findAll();
        return userListDtos != null ? new ResponseEntity<>(userListDtos, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody UserSaveDto user) {
        try {
            return new ResponseEntity<>(userService.add(user), HttpStatus.OK);
        } catch (ServiceException exception) {
            return getResponseEntityForException(exception);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDto) {
        try {
            return new ResponseEntity<>(userService.update(userDto), HttpStatus.OK);
        } catch (ServiceException exception) {
            return getResponseEntityForException(exception);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(userService.delete(id), HttpStatus.OK);
        } catch (ServiceException exception) {
            return getResponseEntityForException(exception);
        }
    }

    private ResponseEntity<?> getResponseEntityForException(ServiceException exception) {
        return exception.getMessage() != null
                ? new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>("Nothing is found", HttpStatus.NOT_FOUND);
    }
}