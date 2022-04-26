package by.library.yavlash.controller;

import by.library.yavlash.dto.AuthorListDto;
import by.library.yavlash.dto.AuthorSaveDto;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.service.AuthorService;
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

import java.util.List;

@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(authorService.findById(id), HttpStatus.OK);
        } catch (ServiceException exception) {
            return getResponseEntityForException(exception);
        }
    }

    @GetMapping
    public ResponseEntity<List<AuthorListDto>> findAll() throws ServiceException {
        List<AuthorListDto> authors = authorService.findAll();
        return authors != null ? new ResponseEntity<>(authors, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> addAuthor(@RequestBody AuthorSaveDto authorSaveDto) {
        try {
            return new ResponseEntity<>(authorService.add(authorSaveDto), HttpStatus.OK);
        } catch (ServiceException exception) {
            return getResponseEntityForException(exception);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(authorService.delete(id), HttpStatus.OK);
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