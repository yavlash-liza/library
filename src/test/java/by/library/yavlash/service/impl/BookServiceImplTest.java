package by.library.yavlash.service.impl;

import by.library.yavlash.dto.BookSaveDto;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void addBook() throws ServiceException {
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
        boolean actual = bookService.addBook(bookSaveDto);

        //then
        Assertions.assertTrue(actual);
    }

    @Test
    void deleteBook() throws ServiceException {
        //given
        Long id = 3L;

        //when
        boolean actual = bookService.deleteBook(id);

        //then
        Assertions.assertTrue(actual);
    }
}