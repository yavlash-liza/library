package by.library.yavlash.controller;

import by.library.yavlash.dto.BookDamageDto;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.service.BookDamageService;
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

@RestController
@RequestMapping("/damages")
public class BookDamageController {
    private final BookDamageService bookDamageService;
    private final Bucket bucket;

    public BookDamageController(BookDamageService bookDamageService) {
        this.bookDamageService = bookDamageService;
        Bandwidth limit = Bandwidth.classic(50, Refill.greedy(50, Duration.ofMinutes(1)));
        this.bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDamageDto> findById(@PathVariable Long id) throws ServiceException {
        if (bucket.tryConsume(1)) {
            BookDamageDto bookDamage = bookDamageService.findById(id);
            if (bookDamage == null) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(bookDamage);
            }
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @PreAuthorize("hasAuthority('BOOK_DAMAGE_WRITE')")
    @PostMapping
    public ResponseEntity<Boolean> add(
            @RequestBody BookDamageDto bookDamageDto
    ) throws ServiceException {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok(bookDamageService.add(bookDamageDto));
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @PreAuthorize("hasAuthority('BOOK_DAMAGE_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) throws ServiceException {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok(bookDamageService.softDelete(id));
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }
}