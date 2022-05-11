package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.BookCopy;
import by.library.yavlash.entity.BookDamage;
import by.library.yavlash.entity.Order;
import by.library.yavlash.entity.User;
import by.library.yavlash.repository.BaseRepositoryTest;
import by.library.yavlash.repository.BookDamageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

class BookDamageRepositoryTest extends BaseRepositoryTest {
    @Autowired
    private BookDamageRepository bookDamageRepository;

    @Test
    public void findByIdTest_shouldReturnTheFirstBookDamageInDB() {
        //given
        BookDamage bookDamage = findBookDamageForFindById();
        Optional<BookDamage> expected = Optional.of(bookDamage);

        //when
        Optional<BookDamage> actual = bookDamageRepository.findById(bookDamage.getId());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_shouldReturnListOfAllBookDamage() {
        //given
        List<BookDamage> expected = findBookDamageForFindAll();

        //when
        List<BookDamage> actual = bookDamageRepository.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldReturnAddedBookDamage() {
        //given
        List<BookDamage> expected = findBookDamageForFindAll();
        Assertions.assertEquals(4, expected.size());

        //when
        BookDamage newBookDamageActual = BookDamage.builder().imagePath("image path").damageDescription("damage5").user(User.builder().id(1L).build()).order(Order.builder().id(2L).build()).bookCopy(BookCopy.builder().id(3L).build()).build();
        BookDamage isAdded = bookDamageRepository.save(newBookDamageActual);
        BookDamage newBookDamageExpected = BookDamage.builder().id(5L).imagePath("image path").damageDescription("damage5").user(User.builder().id(1L).build()).order(Order.builder().id(2L).build()).bookCopy(BookCopy.builder().id(3L).build()).build();
        expected.add(newBookDamageExpected);

        //then
        Assertions.assertNotNull(isAdded);
        Assertions.assertEquals(newBookDamageExpected, newBookDamageActual);
        Assertions.assertEquals(Optional.of(newBookDamageExpected), bookDamageRepository.findById(newBookDamageActual.getId()));
    }

    @Test
    void updateTest_shouldUpdateBookDamage() {
        //given
        BookDamage bookDamage = BookDamage.builder().id(2L).imagePath("image path").damageDescription("damage3").user(User.builder().id(1L).build()).order(Order.builder().id(2L).build()).bookCopy(BookCopy.builder().id(3L).build()).build();

        // when
        BookDamage isUpdated = bookDamageRepository.save(bookDamage);

        //then
        Assertions.assertNotNull(isUpdated);
        Assertions.assertEquals(Optional.of(bookDamage), bookDamageRepository.findById(bookDamage.getId()));
    }
}