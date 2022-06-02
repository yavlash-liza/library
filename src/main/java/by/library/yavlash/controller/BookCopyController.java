package by.library.yavlash.controller;

import by.library.yavlash.dto.BookCopyDto;
import by.library.yavlash.dto.BookCopyListDto;
import by.library.yavlash.dto.BookCopySaveDto;
import by.library.yavlash.dto.BookSaveDto;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.service.BookCopyService;
import by.library.yavlash.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookCopyController {
    private static final boolean STATUS = false;
    private static final String ITEMS_ON_PAGE = "3";
    private static final String DEFAULT_PAGE = "0";
    private final BookCopyService bookCopyService;
    private final BookService bookService;

    @GetMapping("/{id}")
    public BookCopyDto findById(@PathVariable Long id) throws ServiceException {
        return bookCopyService.findById(id);
    }

    @GetMapping
    public List<BookCopyListDto> findAll() throws ServiceException {
        return bookCopyService.findAll();
    }

    @GetMapping("/all")
    public List<BookCopyListDto> findAllBookCopies(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = ITEMS_ON_PAGE) int size
    ) throws ServiceException {
        List<BookCopyListDto> bookCopyListDtos;
        if (search == null) {
            bookCopyListDtos = bookCopyService.findListBookCopies(page, size, STATUS);
        } else {
            bookCopyListDtos = bookCopyService.findListBookCopiesByTitle(page, size, STATUS, search);
        }
        return bookCopyListDtos;
    }

    @PreAuthorize("hasAuthority('BOOK_WRITE')")
    @PostMapping("/copies")
    public boolean add(
            @RequestBody BookCopySaveDto bookCopySaveDto
    ) throws ServiceException {
        return bookCopyService.add(bookCopySaveDto);
    }

    @PreAuthorize("hasAuthority('BOOK_WRITE')")
    @PostMapping
    public boolean add(
            @RequestBody BookSaveDto order
    ) throws ServiceException {
        return bookService.add(order);
    }

    @PreAuthorize("hasAuthority('BOOK_WRITE')")
    @PutMapping
    public boolean update(
            @RequestBody BookCopySaveDto bookCopySaveDto
    ) throws ServiceException {
        return bookCopyService.update(bookCopySaveDto);
    }

    @PreAuthorize("hasAuthority('BOOK_DELETE')")
    @DeleteMapping("/copies/{id}")
    public boolean delete(@PathVariable Long id) throws ServiceException {
        return bookCopyService.softDelete(id);
    }
}