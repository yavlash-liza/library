package by.library.yavlash.controller;

import by.library.yavlash.dto.BookCopyDto;
import by.library.yavlash.dto.BookCopyListDto;
import by.library.yavlash.dto.BookCopySaveDto;
import by.library.yavlash.dto.BookSaveDto;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.service.BookCopyService;
import by.library.yavlash.service.BookService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookCopyController {
    private final BookCopyService bookCopyService;
    private final BookService bookService;
    private final Bucket bucket;

    public BookCopyController(BookCopyService bookCopyService, BookService bookService) {
        this.bookCopyService = bookCopyService;
        this.bookService = bookService;
        Bandwidth limit = Bandwidth.classic(50, Refill.greedy(50, Duration.ofMinutes(1)));
        this.bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookCopyDto> findById(@PathVariable Long id) throws ServiceException {
        if (bucket.tryConsume(1)) {
            BookCopyDto bookCopy = bookCopyService.findById(id);
            if (bookCopy == null) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(bookCopy);
            }
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @GetMapping
    public ResponseEntity<List<BookCopyListDto>> findAll() throws ServiceException {
        if (bucket.tryConsume(1)) {
            List<BookCopyListDto> bookCopies = bookCopyService.findAll();
            if (bookCopies == null) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(bookCopies);
            }
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @PreAuthorize("hasAuthority('BOOK_WRITE')")
    @PostMapping("/copies")
    public ResponseEntity<Boolean> add(
            @RequestBody BookCopySaveDto bookCopySaveDto
    ) throws ServiceException {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok(bookCopyService.add(bookCopySaveDto));
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @PreAuthorize("hasAuthority('BOOK_WRITE')")
    @PostMapping
    public ResponseEntity<Boolean> add(
            @RequestBody BookSaveDto bookSaveDto
    ) throws ServiceException {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok(bookService.add(bookSaveDto));
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @PreAuthorize("hasAuthority('BOOK_WRITE')")
    @PutMapping
    public ResponseEntity<Boolean> update(
            @RequestBody BookCopySaveDto bookCopySaveDto
    ) throws ServiceException {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok(bookCopyService.update(bookCopySaveDto));
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @PreAuthorize("hasAuthority('BOOK_DELETE')")
    @DeleteMapping("/copies/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) throws ServiceException {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok(bookCopyService.softDelete(id));
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }
}