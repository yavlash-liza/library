package by.library.yavlash.controller;

import by.library.yavlash.dto.AuthorDto;
import by.library.yavlash.dto.AuthorListDto;
import by.library.yavlash.dto.AuthorSaveDto;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("/{id}")
    public AuthorDto findById(@PathVariable Long id) throws ServiceException {
        return authorService.findById(id);
    }

    @GetMapping
    public List<AuthorListDto> findAll() throws ServiceException {
        return authorService.findAll();
    }

    @PreAuthorize("hasRole({'admin'})")
    @PostMapping
    public boolean add(
            @RequestBody AuthorSaveDto authorSaveDto
    ) throws ServiceException {
        return authorService.add(authorSaveDto);
    }

    @PreAuthorize("hasRole({'admin'})")
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) throws ServiceException {
        return authorService.delete(id);
    }
}