package by.library.yavlash.controller;

import by.library.yavlash.dto.RoleDto;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.service.RoleService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;
    private final Bucket bucket;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
        Bandwidth limit = Bandwidth.classic(50, Refill.greedy(50, Duration.ofMinutes(1)));
        this.bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();
    }

    @PreAuthorize("hasAuthority('ROLE_READ')")
    @GetMapping
    public ResponseEntity<List<RoleDto>> findAll() throws ServiceException {
        if (bucket.tryConsume(1)) {
            List<RoleDto> roles = roleService.findAll();
            if (roles == null) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(roles);
            }
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }
}