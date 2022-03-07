package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.BookDamage;
import by.library.yavlash.entity.BookCopy;
import by.library.yavlash.entity.User;
import by.library.yavlash.entity.Order;
import by.library.yavlash.exception.RepositoryException;
import by.library.yavlash.repository.BaseRepositoryTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class BookDamageRepositoryImplTest extends BaseRepositoryTest {
    private final BookDamageRepositoryImpl bookDamageRepository;

    public BookDamageRepositoryImplTest() {
        bookDamageRepository = new BookDamageRepositoryImpl();
    }

    @Test
    public void findByIdTest_shouldReturnTheFirstBookDamageInDB() throws RepositoryException {
        //given
        BookDamage expected = findBookDamageForFindById();

        //when
        BookDamage actual = bookDamageRepository.findById(expected.getId());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_shouldReturnListOfAllBookDamage() throws RepositoryException {
        //given
        List<BookDamage> expected = findBookDamageForFindAll();

        //when
        List<BookDamage> actual = bookDamageRepository.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldReturnAddedBookDamage() throws RepositoryException {
        //given
        List<BookDamage> expected = findBookDamageForFindAll();
        Assertions.assertEquals(4, expected.size());

        //when
        BookDamage newBookDamageActual = BookDamage.builder().imagePath("image path").damageDescription("damage5").user(User.builder().id(1L).build()).order(Order.builder().id(2L).build()).bookCopy(BookCopy.builder().id(3L).build()).build();
        boolean isAdded = bookDamageRepository.add(newBookDamageActual);
        BookDamage newBookDamageExpected = BookDamage.builder().id(5L).imagePath("image path").damageDescription("damage5").user(User.builder().id(1L).build()).order(Order.builder().id(2L).build()).bookCopy(BookCopy.builder().id(3L).build()).build();
        expected.add(newBookDamageExpected);

        //then
        Assertions.assertTrue(isAdded);
        Assertions.assertEquals(newBookDamageExpected, newBookDamageActual);
        Assertions.assertEquals(newBookDamageExpected, bookDamageRepository.findById(newBookDamageActual.getId()));
    }

    @Test
    void updateTest_shouldUpdateBookDamage() throws RepositoryException {
        //given
        BookDamage bookDamage = BookDamage.builder().id(2L).imagePath("image path").damageDescription("damage3").user(User.builder().id(1L).build()).order(Order.builder().id(2L).build()).bookCopy(BookCopy.builder().id(3L).build()).build();

        // when
        boolean isUpdated = bookDamageRepository.update(bookDamage);

        //then
        Assertions.assertTrue(isUpdated);
        Assertions.assertEquals(bookDamage, bookDamageRepository.findById(bookDamage.getId()));
    }

    @Test
    public void deleteTest_shouldDeleteBookDamage() throws RepositoryException {
        //given
        BookDamage expected = BookDamage.builder().id(2L).imagePath("image path").damageDescription("damage3").user(User.builder().id(1L).build()).order(Order.builder().id(2L).build()).bookCopy(BookCopy.builder().id(3L).build()).build();

        // when
        boolean isDeleted = bookDamageRepository.delete(expected.getId());

        //then
        Assertions.assertTrue(isDeleted);
        Assertions.assertNotEquals(expected, bookDamageRepository.findById(expected.getId()));
    }
}