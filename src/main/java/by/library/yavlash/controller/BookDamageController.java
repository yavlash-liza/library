package by.library.yavlash.controller;

import by.library.yavlash.dto.BookDamageDto;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.service.BookDamageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book_damage")
@RequiredArgsConstructor
public class BookDamageController {
    private final BookDamageService bookDamageService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookDamageById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(bookDamageService.findById(id), HttpStatus.OK);
        } catch (ServiceException exception) {
            return getResponseEntityForException(exception);
        }
    }

    @PostMapping
    public ResponseEntity<?> addBookDamage(@RequestBody BookDamageDto bookDamageDto) {
        try {
            return new ResponseEntity<>(bookDamageService.add(bookDamageDto), HttpStatus.OK);
        } catch (ServiceException exception) {
            return getResponseEntityForException(exception);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBookDamage(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(bookDamageService.delete(id), HttpStatus.OK);
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