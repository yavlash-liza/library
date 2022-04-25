package by.library.yavlash.service.impl;

import by.library.yavlash.dto.BookDamageDto;
import by.library.yavlash.entity.BookCopy;
import by.library.yavlash.entity.BookDamage;
import by.library.yavlash.entity.Order;
import by.library.yavlash.entity.User;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.repository.BookDamageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookDamageServiceImplTest {
    @Mock
    private BookDamageRepository bookDamageRepository;

    @InjectMocks
    private BookDamageServiceImpl bookDamageService;

    @Test
    void findByIdTest_shouldReturnTheFirstBookDamageInDB() throws ServiceException {
        //given
        Long id = 1L;
        BookDamage bookDamage = BookDamage.builder()
                .id(id)
                .bookCopy(BookCopy.builder().id(1L).build())
                .order(Order.builder().id(1L).build())
                .user(User.builder().id(1L).build()).build();

        BookDamageDto expected = BookDamageDto.builder().id(id).userId(1L).bookCopyId(1L).orderId(1L).build();

        //when
        when(bookDamageRepository.findById(id)).thenReturn(Optional.of(bookDamage));
        BookDamageDto actual = bookDamageService.findById(id);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldAddBookDamage() throws ServiceException {
        //given && when
        boolean actual = bookDamageService.add(BookDamageDto.builder().bookCopyId(1L).orderId(1L).userId(1L).build());

        //then
        Assertions.assertTrue(actual);
    }

    @Test
    void deleteTest_shouldDeleteBookDamage() throws ServiceException {
        //given
        Long id = 3L;
        BookDamage expected = BookDamage.builder()
                .id(id)
                .bookCopy(BookCopy.builder().id(1L).build())
                .order(Order.builder().id(1L).build())
                .user(User.builder().id(1L).build()).build();

        //when
        when(bookDamageRepository.findById(id)).thenReturn(Optional.of(expected));
        when(bookDamageRepository.save(expected)).thenReturn(expected);
        boolean actual = bookDamageService.delete(id);

        //then
        Assertions.assertTrue(actual);
    }
}