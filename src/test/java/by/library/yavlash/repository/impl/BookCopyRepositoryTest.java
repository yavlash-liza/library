package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.Book;
import by.library.yavlash.entity.BookCopy;
import by.library.yavlash.repository.BaseRepositoryTest;
import by.library.yavlash.repository.BookCopyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

class BookCopyRepositoryTest extends BaseRepositoryTest {
    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Test
    public void findByIdTest_shouldReturnTheFirstBookCopyInDB() {
        //given
        BookCopy bookCopy = findBookCopyForFindById();
        Optional<BookCopy> expected = Optional.of(bookCopy);

        //when
        Optional<BookCopy> actual = bookCopyRepository.findById(bookCopy.getId());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_shouldReturnListOfAllBookCopies() {
        //given
        List<BookCopy> expected = findBookCopiesForFindAll();

        //when
        List<BookCopy> actual = bookCopyRepository.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllByDeletedTest_shouldReturnListOfBookCopies() {
        //given
        PageRequest pageReq = PageRequest.of(0, 10);
        Page<BookCopy> expected = findBookCopiesForPage(pageReq);

        //when
        Page<BookCopy> actual = bookCopyRepository.findAllByDeleted(false, pageReq);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllByDeletedAndTitleTest_shouldReturnListOfAllBookCopies() {
        //given
        PageRequest pageReq = PageRequest.of(0, 1);
        String search = "Idiot";
        Page<BookCopy> expected = findBookCopiesPageByTitle(pageReq);

        //when
        Page<BookCopy> actual = bookCopyRepository.findAllByDeletedAndBook_Title(false, search, pageReq);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldReturnAddedBookCopy() {
        //given
        List<BookCopy> expected = findBookCopiesForFindAll();
        Assertions.assertEquals(5, expected.size());

        //when
        BookCopy newBookCopyActual = BookCopy.builder().status("AVAILABLE").imagePath("image path").registrationDate(LocalDate.of(2000, 1, 1)).pricePerDay(13).book(Book.builder().id(1L).build()).build();
        BookCopy isAdded = bookCopyRepository.save(newBookCopyActual);
        BookCopy newBookCopyExpected = BookCopy.builder().id(6L).status("AVAILABLE").imagePath("image path").registrationDate(LocalDate.of(2000, 1, 1)).pricePerDay(13).book(Book.builder().id(1L).build()).build();
        expected.add(newBookCopyExpected);

        //then
        Assertions.assertNotNull(isAdded);
        Assertions.assertEquals(newBookCopyExpected, newBookCopyActual);
        Assertions.assertEquals(Optional.of(newBookCopyExpected), bookCopyRepository.findById(newBookCopyActual.getId()));
    }

    @Test
    void updateTest_shouldUpdateBookCopy() {
        //given
        BookCopy bookCopy = BookCopy.builder().id(2L).status("AVAILABLE").imagePath("image path").registrationDate(LocalDate.of(2000, 1, 1)).pricePerDay(13).book(Book.builder().id(2L).build()).build();

        // when
        BookCopy isUpdated = bookCopyRepository.save(bookCopy);

        //then
        Assertions.assertNotNull(isUpdated);
        Assertions.assertEquals(Optional.of(bookCopy), bookCopyRepository.findById(bookCopy.getId()));
    }
}