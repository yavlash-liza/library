package by.library.yavlash.service.impl;

import by.library.yavlash.config.TestServiceConfiguration;
import by.library.yavlash.dto.BookSaveDto;
import by.library.yavlash.entity.Book;
import by.library.yavlash.exception.RepositoryException;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = TestServiceConfiguration.class)
class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void addBook() throws RepositoryException, ServiceException {
        //given
        BookSaveDto bookSaveDto = BookSaveDto.builder()
                .genresId(new ArrayList<>() {{
                    add(1L);
                }})
                .authorsId(new ArrayList<>() {{
                    add(1L);
                }})
                .title("Hamlet")
                .build();

        // when
        when(bookRepository.add(Book.builder().title("Hamlet").build()))
                .thenReturn(true);
        boolean actual = bookService.addBook(bookSaveDto);

        //then
        Assertions.assertTrue(actual);
    }

    @Test
    void deleteBook() throws RepositoryException, ServiceException {
        //given
        Long id = 3L;

        //when
        when(bookRepository.delete(id)).thenReturn(true);
        boolean actual = bookService.deleteBook(id);

        //then
        Assertions.assertTrue(actual);
    }
}