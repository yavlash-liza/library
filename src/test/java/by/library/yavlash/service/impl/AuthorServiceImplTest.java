package by.library.yavlash.service.impl;

import by.library.yavlash.dto.AuthorDto;
import by.library.yavlash.dto.AuthorListDto;
import by.library.yavlash.dto.AuthorSaveDto;
import by.library.yavlash.dto.BookCopyListDto;
import by.library.yavlash.entity.Author;
import by.library.yavlash.entity.Book;
import by.library.yavlash.entity.BookCopy;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.repository.AuthorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {
    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Test
    void findById_shouldReturnAuthorDto() throws ServiceException {
        //given
        Long id = 1L;

        Set<Book> books = new HashSet<>() {{
            add(Book.builder().id(1L)
                    .bookCopies(new HashSet<>() {{
                        add(BookCopy.builder().id(1L)
                                .book(Book.builder().id(1L).build()).build());
                    }}).build());
        }};
        AuthorDto expected = AuthorDto.builder().id(id).firstName("Liza")
                .books(new ArrayList<>() {{
                    add(BookCopyListDto.builder().id(1L).build());
                }}).build();

        //when
        when(authorRepository.findById(id)).thenReturn(Optional.of(Author.builder().id(id).firstName("Liza").books(books).build()));
        AuthorDto actual = authorService.findAuthorById(id);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAll_shouldReturnListOfAuthorListDto() throws ServiceException {
        //given
        List<AuthorListDto> expected = new ArrayList<>() {{
            add(AuthorListDto.builder().id(1L).build());
            add(AuthorListDto.builder().id(2L).build());
        }};

        //when
        when(authorRepository.findAll()).thenReturn(new ArrayList<>() {{
            add(Author.builder().id(1L).build());
            add(Author.builder().id(2L).build());
        }});
        List<AuthorListDto> actual = authorService.findAllAuthors();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void add_shouldAddAuthor() throws ServiceException {
        //given && when
        boolean actual = authorService.addAuthor(AuthorSaveDto.builder().firstName("Liza").build());

        //then
        Assertions.assertTrue(actual);
    }

    @Test
    void delete_shouldDeleteAuthor() throws ServiceException {
        //given
        Long id = 3L;

        //when
        boolean actual = authorService.deleteAuthor(id);

        //then
        Assertions.assertTrue(actual);
    }
}