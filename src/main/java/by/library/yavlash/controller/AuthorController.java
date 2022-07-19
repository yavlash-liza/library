package by.library.yavlash.controller;

import by.library.yavlash.dto.AuthorDto;
import by.library.yavlash.dto.AuthorListDto;
import by.library.yavlash.dto.AuthorSaveDto;
import by.library.yavlash.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {
    private static final boolean STATUS = false;
    private static final String ITEMS_ON_PAGE = "3";
    private static final String DEFAULT_PAGE = "0";
    private final AuthorService authorService;

    @GetMapping("/{id}")
    public AuthorDto findById(@PathVariable Long id) {
        return authorService.findById(id);
    }

    @GetMapping
    public List<AuthorListDto> findAll() {
        return authorService.findAll();
    }

    @GetMapping("/all")
    public List<AuthorListDto> findAllAuthors(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = ITEMS_ON_PAGE) int size
    ) throws ServiceException {
        List<AuthorListDto> authorListDtos;
        if (search == null) {
            authorListDtos = authorService.findListAuthors(page, size, STATUS);
        } else {
            authorListDtos = authorService.findListAuthorsBySearch(page, size, STATUS, search);
        }
        return authorListDtos;
    }

    @PreAuthorize("hasAuthority('AUTHOR_WRITE')")
    @PostMapping
    public boolean add(
            @RequestBody AuthorSaveDto authorSaveDto
    ) {
        return authorService.add(authorSaveDto);
    }

    @PreAuthorize("hasAuthority('AUTHOR_DELETE')")
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return authorService.softDelete(id);
    }
}