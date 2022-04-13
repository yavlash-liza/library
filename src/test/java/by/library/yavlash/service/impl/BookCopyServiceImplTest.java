package by.library.yavlash.service.impl;

import by.library.yavlash.dto.AuthorListDto;
import by.library.yavlash.dto.BookCopyDto;
import by.library.yavlash.dto.BookCopyListDto;
import by.library.yavlash.dto.BookCopySaveDto;
import by.library.yavlash.dto.BookDamageListDto;
import by.library.yavlash.dto.GenreDto;
import by.library.yavlash.entity.Author;
import by.library.yavlash.entity.Book;
import by.library.yavlash.entity.BookCopy;
import by.library.yavlash.entity.BookDamage;
import by.library.yavlash.entity.Genre;
import by.library.yavlash.exception.RepositoryException;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.mapper.BookCopyMapperImpl;
import by.library.yavlash.repository.BookCopyRepository;
import by.library.yavlash.repository.impl.BookCopyRepositoryImpl;
import by.library.yavlash.service.BookCopyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookCopyServiceImplTest {
    private final BookCopyRepository bookCopyRepository;
    private final BookCopyService bookCopyService;

    public BookCopyServiceImplTest() {
        bookCopyRepository = mock(BookCopyRepositoryImpl.class);
        bookCopyService = new BookCopyServiceImpl(bookCopyRepository, new BookCopyMapperImpl());
    }

    @Test
    void findBookCopyById() throws RepositoryException, ServiceException {
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
                .bookDamages(new ArrayList<>() {{
                    add(BookDamageListDto.builder().id(1L).build());
                }})
                .authors(new ArrayList<>() {{
                    add(AuthorListDto.builder().id(1L).build());
                }})
                .genres(new ArrayList<>() {{
                    add(GenreDto.builder().id(1L).build());
                }}).build();

        //when
        when(bookCopyRepository.findById(id)).thenReturn(BookCopy.builder().id(id).book(book).bookDamages(bookDamages).build());

        BookCopyDto actual = bookCopyService.findBookCopyById(id);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllBookCopies() throws RepositoryException, ServiceException {
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
    void addBookCopy() throws RepositoryException, ServiceException {
        //given && when
        when(bookCopyRepository.add(BookCopy.builder().book(Book.builder().id(1L).build()).build()))
                .thenReturn(true);
        boolean actual = bookCopyService.addBookCopy(BookCopySaveDto.builder().build());

        //then
        Assertions.assertTrue(actual);
    }

    @Test
    void updateBookCopy() throws RepositoryException, ServiceException {
        //given
        BookCopyDto expected = BookCopyDto.builder().id(4L).imagePath("image").build();

        //when
        when(bookCopyRepository.update(BookCopy.builder().id(4L).imagePath("image").build())).thenReturn(true);
        boolean actual = bookCopyService.updateBookCopy(expected);

        //then
        Assertions.assertTrue(actual);
    }

    @Test
    void deleteBookCopy() throws RepositoryException, ServiceException {
        //given
        Long id = 3L;

        //when
        when(bookCopyRepository.delete(id)).thenReturn(true);
        boolean actual = bookCopyService.deleteBookCopy(id);

        //then
        Assertions.assertTrue(actual);
    }
}