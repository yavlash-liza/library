package by.library.yavlash.service.impl;

import by.library.yavlash.dto.BookDamageDto;
import by.library.yavlash.dto.BookDamageSaveDto;
import by.library.yavlash.entity.BookCopy;
import by.library.yavlash.entity.BookDamage;
import by.library.yavlash.entity.Order;
import by.library.yavlash.entity.User;
import by.library.yavlash.exception.RepositoryException;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.repository.BookDamageRepository;
import by.library.yavlash.repository.impl.BookDamageRepositoryImpl;
import by.library.yavlash.service.BookDamageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookDamageServiceImplTest {
    private final BookDamageRepository bookDamageRepository;
    private final BookDamageService bookDamageService;

    public BookDamageServiceImplTest() {
        bookDamageRepository = mock(BookDamageRepositoryImpl.class);
        bookDamageService = new BookDamageServiceImpl(bookDamageRepository);
    }

    @Test
    void findBookDamageById() throws RepositoryException, ServiceException {
        //given
        Long id = 1L;
        BookDamage bookDamage = BookDamage.builder()
                .id(id)
                .bookCopy(BookCopy.builder().id(1L).build())
                .order(Order.builder().id(1L).build())
                .user(User.builder().id(1L).build()).build();

        BookDamageDto expected = BookDamageDto.builder().id(id).userId(1L).bookCopyId(1L).orderId(1L).build();

        //when
        when(bookDamageRepository.findById(id)).thenReturn(bookDamage);
        BookDamageDto actual = bookDamageService.findBookDamageById(id);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addBookDamage() throws RepositoryException, ServiceException {
        //given
        BookDamage bookDamage = BookDamage.builder()
                .bookCopy(BookCopy.builder().id(1L).build())
                .order(Order.builder().id(1L).build())
                .user(User.builder().id(1L).build()).build();

        // when
        when(bookDamageRepository.add(bookDamage))
                .thenReturn(true);
        boolean actual = bookDamageService.addBookDamage(BookDamageSaveDto.builder().id(3L).bookCopyId(1L).orderId(1L).userId(1L).build());

        //then
        Assertions.assertTrue(actual);
    }

    @Test
    void deleteBookDamage() throws RepositoryException, ServiceException {
        //given
        Long id = 3L;

        //when
        when(bookDamageRepository.delete(id)).thenReturn(true);
        boolean actual = bookDamageService.deleteBookDamage(id);

        //then
        Assertions.assertTrue(actual);
    }
}