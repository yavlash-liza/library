package by.library.yavlash.service.impl;

import by.library.yavlash.dto.BookSaveDto;
import by.library.yavlash.entity.Book;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.mapper.BookMapper;
import by.library.yavlash.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;

    @Spy
    private BookMapper bookMapper = Mappers.getMapper(BookMapper.class);

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void addTest_shouldAddBook() throws ServiceException {
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
        boolean actual = bookService.add(bookSaveDto);

        //then
        Assertions.assertTrue(actual);
    }

    @Test
    void deleteTest_shouldDeleteBook() throws ServiceException {
        //given
        Long id = 3L;
        Book expected = Book.builder().id(id).genres(new HashSet<>()).authors(new HashSet<>()).bookCopies(new HashSet<>()).build();

        //when
        when(bookRepository.findById(id)).thenReturn(Optional.of(expected));
        when(bookRepository.save(expected)).thenReturn(expected);
        boolean actual = bookService.softDelete(id);

        //then
        Assertions.assertTrue(actual);
    }
}