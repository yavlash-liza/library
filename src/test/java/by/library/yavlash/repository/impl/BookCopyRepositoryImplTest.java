package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.Book;
import by.library.yavlash.entity.BookCopy;
import by.library.yavlash.exception.RepositoryException;
import by.library.yavlash.repository.BaseRepositoryTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class BookCopyRepositoryImplTest extends BaseRepositoryTest {
    private final BookCopyRepositoryImpl bookCopyRepository;

    public BookCopyRepositoryImplTest() {
        bookCopyRepository = new BookCopyRepositoryImpl();
    }

    @Test
    public void findByIdTest_shouldReturnTheFirstBookCopyInDB() throws RepositoryException {
        //given
        BookCopy expected = findBookCopyForFindById();

        //when
        BookCopy actual = bookCopyRepository.findById(expected.getId());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_shouldReturnListOfAllBookCopy() throws RepositoryException {
        //given
        List<BookCopy> expected = findBookCopiesForFindAll();

        //when
        List<BookCopy> actual = bookCopyRepository.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldReturnAddedBookCopy() throws RepositoryException {
        //given
        List<BookCopy> expected = findBookCopiesForFindAll();
        Assertions.assertEquals(5, expected.size());

        //when
        BookCopy newBookCopyActual = BookCopy.builder().status("AVAILABLE").registrationDate(LocalDate.of(2000, 1, 1)).price(70).pricePerDay(13).book(Book.builder().id(1L).build()).build();
        boolean isAdded = bookCopyRepository.add(newBookCopyActual);
        BookCopy newBookCopyExpected = BookCopy.builder().id(6L).status("AVAILABLE").registrationDate(LocalDate.of(2000, 1, 1)).price(70).pricePerDay(13).book(Book.builder().id(1L).build()).build();
        expected.add(newBookCopyExpected);

        //then
        Assertions.assertTrue(isAdded);
        Assertions.assertEquals(newBookCopyExpected, newBookCopyActual);
        Assertions.assertEquals(newBookCopyExpected, bookCopyRepository.findById(newBookCopyActual.getId()));
    }

    @Test
    void updateTest_shouldUpdateBookCopy() throws RepositoryException {
        //given
        BookCopy bookCopy = BookCopy.builder().id(2L).status("AVAILABLE").registrationDate(LocalDate.of(2000, 1, 1)).price(70).pricePerDay(13).book(Book.builder().id(2L).build()).build();

        // when
        boolean isUpdated = bookCopyRepository.update(bookCopy);

        //then
        Assertions.assertTrue(isUpdated);
        Assertions.assertEquals(bookCopy, bookCopyRepository.findById(bookCopy.getId()));
    }

    @Test
    public void deleteTest_shouldDeleteBookCopy() throws RepositoryException {
        //given
        Long bookCopyId = 1L;

        // when
        boolean isDeleted = bookCopyRepository.delete(bookCopyId);

        //then
        Assertions.assertTrue(isDeleted);
    }
}