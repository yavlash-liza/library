package by.library.yavlash.controller;

import by.library.yavlash.dto.BookDamageDto;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.service.BookDamageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/damages")
@RequiredArgsConstructor
public class BookDamageController {
    private final BookDamageService bookDamageService;

    @GetMapping("/{id}")
    public BookDamageDto findById(@PathVariable Long id) throws ServiceException {
        return bookDamageService.findById(id);
    }

    @PreAuthorize("hasRole(('admin'))")
    @PostMapping
    public boolean add(@RequestBody BookDamageDto bookDamageDto) throws ServiceException {
        return bookDamageService.add(bookDamageDto);
    }

    @PreAuthorize("hasRole(('admin'))")
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) throws ServiceException {
        return bookDamageService.delete(id);
    }
}