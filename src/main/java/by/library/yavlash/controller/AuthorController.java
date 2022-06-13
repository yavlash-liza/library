package by.library.yavlash.controller;

import by.library.yavlash.dto.AuthorDto;
import by.library.yavlash.dto.AuthorListDto;
import by.library.yavlash.dto.AuthorSaveDto;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.service.AuthorService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;
    private final Bucket bucket;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
        Bandwidth limit = Bandwidth.classic(50, Refill.greedy(50, Duration.ofMinutes(1)));
        this.bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> findById(@PathVariable Long id) throws ServiceException {
        if (bucket.tryConsume(1)) {
            AuthorDto author = authorService.findById(id);
            if (author == null) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(author);
            }
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @GetMapping
    public ResponseEntity<List<AuthorListDto>> findAll() throws ServiceException {
        if (bucket.tryConsume(1)) {
            List<AuthorListDto> authors = authorService.findAll();
            if (authors == null) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(authors);
            }
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @PreAuthorize("hasAuthority('AUTHOR_WRITE')")
    @PostMapping
    public ResponseEntity<Boolean> add(
            @RequestBody AuthorSaveDto authorSaveDto
    ) throws ServiceException {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok(authorService.add(authorSaveDto));
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @PreAuthorize("hasAuthority('AUTHOR_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) throws ServiceException {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok(authorService.softDelete(id));
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }
}