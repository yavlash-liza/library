package by.library.yavlash.service.impl;

import by.library.yavlash.dto.AuthorListDto;
import by.library.yavlash.dto.BookCopyDto;
import by.library.yavlash.dto.BookCopyListDto;
import by.library.yavlash.dto.BookCopySaveDto;
import by.library.yavlash.dto.GenreDto;
import by.library.yavlash.entity.Author;
import by.library.yavlash.entity.Book;
import by.library.yavlash.entity.BookCopy;
import by.library.yavlash.entity.BookDamage;
import by.library.yavlash.entity.Genre;
import by.library.yavlash.exception.RepositoryException;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.repository.BookCopyRepository;
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
class BookCopyServiceImplTest {
    @Mock
    private BookCopyRepository bookCopyRepository;

    @InjectMocks
    private BookCopyServiceImpl bookCopyService;

    @Test
    void findBookCopyById() throws ServiceException {
        //given
        Long id = 1L;
        Set<Book> books = new HashSet<>() {{add(Book.builder().id(id).build());}};
        Set<BookDamage> bookDamages = new HashSet<>() {{
            add(BookDamage.builder().id(1L).build());
        }};
        Book book = Book.builder().id(id)
                .bookCopies(new HashSet<>() {{
                    add(BookCopy.builder().id(id).build());
                }})
                .authors(new HashSet<>() {{
                    add(Author.builder().id(1L).books(books).build());
                }})
                .genres(new HashSet<>() {{
                    add(Genre.builder().id(1L).books(books).build());
                }})
                .build();

        BookCopyDto expected = BookCopyDto.builder().id(id)
                .bookDamagesId(new ArrayList<>() {{
                    add(1L);
                }})
                .authors(new ArrayList<>() {{
                    add(AuthorListDto.builder().id(1L).build());
                }})
                .genres(new ArrayList<>() {{
                    add(GenreDto.builder().id(1L).build());
                }}).build();

        //when
        when(bookCopyRepository.findById(id)).thenReturn(Optional.of(BookCopy.builder().id(id).book(book).bookDamages(bookDamages).build()));

        BookCopyDto actual = bookCopyService.findBookCopyById(id);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllBookCopies() throws ServiceException {
        //given
        List<BookCopyListDto> expected = new ArrayList<>() {{
            add(BookCopyListDto.builder().id(1L).build());
            add(BookCopyListDto.builder().id(2L).build());
        }};

        //when
        when(bookCopyRepository.findAll()).thenReturn(new ArrayList<>() {{
            add(BookCopy.builder().id(1L).book(Book.builder().id(1L).build()).build());
            add(BookCopy.builder().id(2L).book(Book.builder().id(1L).build()).build());
        }});
        List<BookCopyListDto> actual = bookCopyService.findAllBookCopies();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addBookCopy() throws ServiceException {
        //given && when
        boolean actual = bookCopyService.addBookCopy(BookCopySaveDto.builder().build());

        //then
        Assertions.assertTrue(actual);
    }

    @Test
    void updateBookCopy() throws RepositoryException, ServiceException {
        //given
        List<Long> list = new ArrayList(){{add(2L);}};
        BookCopyDto expected = BookCopyDto.builder().id(4L).imagePath("image").bookDamagesId(list).build();

        //when
        boolean actual = bookCopyService.updateBookCopy(expected);

        //then
        Assertions.assertTrue(actual);
    }

    @Test
    void deleteBookCopy() throws ServiceException {
        //given
        Long id = 3L;

        //when
        boolean actual = bookCopyService.deleteBookCopy(id);

        //then
        Assertions.assertTrue(actual);
    }
}