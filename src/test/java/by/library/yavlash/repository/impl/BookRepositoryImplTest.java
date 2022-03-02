package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.Book;
import by.library.yavlash.exception.RepositoryException;
import by.library.yavlash.repository.BaseRepositoryTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class BookRepositoryImplTest extends BaseRepositoryTest {
    private final BookRepositoryImpl bookRepository;

    public BookRepositoryImplTest() {
        bookRepository = new BookRepositoryImpl(getDataSource());
    }

    @Test
    public void findByIdTest_shouldReturnTheFirstBookInDB() throws RepositoryException {
        //given
        Book expected = findBookForFindById();

        //when
        Book actual = bookRepository.findById(expected.getId());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_shouldReturnListOfAllBooks() throws RepositoryException {
        //given
        List<Book> expected = findBooksForFindAll();

        //when
        List<Book> actual = bookRepository.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldReturnAddedBook() throws RepositoryException {
        //given
        Book expected = Book.builder().id(6L).title("asd").pagesNumber(12).imagePath("image path").build();
        Book actual = Book.builder().title("asd").pagesNumber(12).imagePath("image path").build();

        //when
        actual = bookRepository.add(actual);

        //then
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, bookRepository.findById(expected.getId()));
    }

    @Test
    void updateTest_shouldUpdateBook() throws RepositoryException {
        //given
        Book book = Book.builder().id(2L).title("Hello").pagesNumber(12).imagePath("image path").build();

        // when
        boolean isUpdated = bookRepository.update(book);

        //then
        Assertions.assertTrue(isUpdated);
        Assertions.assertEquals(book, bookRepository.findById(book.getId()));
    }

    @Test
    public void deleteTest_shouldDeleteBook() throws RepositoryException {
        //given
        Long bookId = 1L;

        // when
        boolean isDeleted = bookRepository.delete(bookId);

        //then
        Assertions.assertTrue(isDeleted);
    }
}