package by.library.yavlash.controller;

import by.library.yavlash.dto.BookCopyDto;
import by.library.yavlash.dto.BookCopyListDto;
import by.library.yavlash.dto.BookCopySaveDto;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.service.BookCopyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book_copy")
@RequiredArgsConstructor
public class BookCopyController {
    private final BookCopyService bookCopyService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookCopyById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(bookCopyService.findById(id), HttpStatus.OK);
        } catch (ServiceException exception) {
            return getResponseEntityForException(exception);
        }
    }

    @GetMapping
    public ResponseEntity<List<BookCopyListDto>> findAll() throws ServiceException {
        List<BookCopyListDto> bookCopies = bookCopyService.findAll();
        return bookCopies != null ? new ResponseEntity<>(bookCopies, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> addBookCopy(@RequestBody BookCopySaveDto bookCopySaveDto) {
        try {
            return new ResponseEntity<>(bookCopyService.add(bookCopySaveDto), HttpStatus.OK);
        } catch (ServiceException exception) {
            return getResponseEntityForException(exception);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateBookCopy(@RequestBody BookCopyDto bookCopyDto) {
        try {
            return new ResponseEntity<>(bookCopyService.update(bookCopyDto), HttpStatus.OK);
        } catch (ServiceException exception) {
            return getResponseEntityForException(exception);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBookCopy(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(bookCopyService.delete(id), HttpStatus.OK);
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