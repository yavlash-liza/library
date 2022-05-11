package by.library.yavlash.controller;

import by.library.yavlash.dto.RoleDto;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    @PreAuthorize("hasAuthority('ROLE_READ')")
    @GetMapping
    public List<RoleDto> findAll() throws ServiceException {
        return roleService.findAll();
    }
}