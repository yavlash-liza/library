package by.library.yavlash.controller;

import by.library.yavlash.dto.UserDto;
import by.library.yavlash.dto.UserListDto;
import by.library.yavlash.dto.UserSaveDto;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private static final boolean STATUS = false;
    private static final String ITEMS_ON_PAGE = "3";
    private static final String DEFAULT_PAGE = "0";
    private final UserService userService;

    @PreAuthorize("hasAuthority('USER_READ')")
    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PreAuthorize("hasAuthority('USER_READ')")
    @GetMapping
    public List<UserListDto> findAll() {
        return userService.findAll();
    }

    @PreAuthorize("hasAuthority('USER_READ')")
    @GetMapping("/all")
    public List<UserListDto> findAllAuthors(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = ITEMS_ON_PAGE) int size
    ) throws ServiceException {
        List<UserListDto> userListDtos;
        if (search == null) {
            userListDtos = userService.findListUsers(page, size, STATUS);
        } else {
            userListDtos = userService.findListUsersBySearch(page, size, STATUS, search);
        }
        return userListDtos;
    }

    @PostMapping
    public boolean add(
            @RequestBody UserSaveDto user
    ) {
        return userService.add(user);
    }

    @PreAuthorize("hasAuthority('USER_WRITE')")
    @PutMapping
    public boolean update(
            @RequestBody UserSaveDto userSaveDto
    ) {
        return userService.update(userSaveDto);
    }

    @PreAuthorize("hasAuthority('USER_DELETE')")
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return userService.softDelete(id);
    }
}