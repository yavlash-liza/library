package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.Book;
import by.library.yavlash.repository.BaseRepositoryTest;
import by.library.yavlash.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

class BookRepositoryTest extends BaseRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void findByIdTest_shouldReturnTheFirstBookInDB() {
        //given
        Book book = findBookForFindById();
        Optional<Book> expected = Optional.of(book);

        //when
        Optional<Book> actual = bookRepository.findById(book.getId());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_shouldReturnListOfAllBooks() {
        //given
        List<Book> expected = findBooksForFindAll();

        //when
        List<Book> actual = bookRepository.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldReturnAddedBook() {
        //given
        List<Book> expected = findBooksForFindAll();
        Assertions.assertEquals(5, expected.size());

        //when
        Book newBookActual = Book.builder().title("asd").pagesNumber(12).imagePath("image path").build();
        Book isAdded = bookRepository.save(newBookActual);
        Book newBookExpected = Book.builder().id(6L).title("asd").pagesNumber(12).imagePath("image path").build();
        expected.add(newBookExpected);

        //then
        Assertions.assertNotNull(isAdded);
        Assertions.assertEquals(newBookExpected, newBookActual);
        Assertions.assertEquals(Optional.of(newBookExpected), bookRepository.findById(newBookActual.getId()));
    }

    @Test
    void updateTest_shouldUpdateBook() {
        //given
        Book book = Book.builder().id(2L).title("Hello").pagesNumber(12).imagePath("image path").build();

        // when
        Book isUpdated = bookRepository.save(book);

        //then
        Assertions.assertNotNull(isUpdated);
        Assertions.assertEquals(Optional.of(book), bookRepository.findById(book.getId()));
    }
}