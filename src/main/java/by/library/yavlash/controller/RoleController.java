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
@PreAuthorize("hasRole(('admin'))")
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    public List<RoleDto> findAll() throws ServiceException {
        return roleService.findAll();
    }
}