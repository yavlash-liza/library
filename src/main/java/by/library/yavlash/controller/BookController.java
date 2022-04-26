package by.library.yavlash.controller;

import by.library.yavlash.dto.BookSaveDto;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody BookSaveDto order) {
        try {
            return new ResponseEntity<>(bookService.add(order), HttpStatus.OK);
        } catch (ServiceException exception) {
            return getResponseEntityForException(exception);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(bookService.delete(id), HttpStatus.OK);
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