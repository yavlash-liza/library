package by.library.yavlash.controller;

import by.library.yavlash.dto.GenreDto;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.service.GenreService;
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
@RequestMapping("/genres")
public class GenreController {
    private final GenreService genreService;
    private final Bucket bucket;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
        Bandwidth limit = Bandwidth.classic(50, Refill.greedy(50, Duration.ofMinutes(1)));
        this.bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();
    }

    @GetMapping
    public ResponseEntity<List<GenreDto>> findAll() throws ServiceException {
        if (bucket.tryConsume(1)) {
            List<GenreDto> genres = genreService.findAll();
            if (genres == null) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(genres);
            }
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @PreAuthorize("hasAuthority('GENRE_WRITE')")
    @PostMapping
    public ResponseEntity<Boolean> add(
            @RequestBody GenreDto genreDto
    ) throws ServiceException {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok(genreService.add(genreDto));
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @PreAuthorize("hasAuthority('GENRE_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) throws ServiceException {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok(genreService.softDelete(id));
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }
}