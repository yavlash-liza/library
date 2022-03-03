//package by.library.yavlash.repository.impl;
//
//import by.library.yavlash.entity.BookDamage;
//import by.library.yavlash.exception.RepositoryException;
//import by.library.yavlash.repository.BaseRepositoryTest;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//class BookDamageRepositoryImplTest extends BaseRepositoryTest {
//    private final BookDamageRepositoryImpl bookDamageRepository;
//
//    public BookDamageRepositoryImplTest() {
//        bookDamageRepository = new BookDamageRepositoryImpl(getDataSource());
//    }
//
//    @Test
//    public void findByIdTest_shouldReturnTheFirstBookDamageInDB() throws RepositoryException {
//        //given
//        BookDamage expected = findBookDamageForFindById();
//
//        //when
//        BookDamage actual = bookDamageRepository.findById(expected.getId());
//
//        //then
//        Assertions.assertEquals(expected, actual);
//    }
//
//    @Test
//    void findAllTest_shouldReturnListOfAllBookDamage() throws RepositoryException {
//        //given
//        List<BookDamage> expected = findBookDamageForFindAll();
//
//        //when
//        List<BookDamage> actual = bookDamageRepository.findAll();
//
//        //then
//        Assertions.assertEquals(expected, actual);
//    }
//
//    @Test
//    void addTest_shouldReturnAddedBookDamage() throws RepositoryException {
//        //given
//        BookDamage expected = BookDamage.builder().id(5L).imagePath("image path").damageDescription("damage5").userId(1L).orderId(2L).bookCopyId(3L).build();
//        BookDamage actual = BookDamage.builder().imagePath("image path").damageDescription("damage5").userId(1L).orderId(2L).bookCopyId(3L).build();
//
//        //when
//        actual = bookDamageRepository.add(actual);
//
//        //then
//        Assertions.assertEquals(expected, actual);
//        Assertions.assertEquals(expected, bookDamageRepository.findById(expected.getId()));
//    }
//
//    @Test
//    void updateTest_shouldUpdateBookDamage() throws RepositoryException {
//        //given
//        BookDamage bookDamage = BookDamage.builder().id(2L).imagePath("image path").damageDescription("damage3").userId(1L).orderId(2L).bookCopyId(3L).build();
//
//        // when
//        boolean isUpdated = bookDamageRepository.update(bookDamage);
//
//        //then
//        Assertions.assertTrue(isUpdated);
//        Assertions.assertEquals(bookDamage, bookDamageRepository.findById(bookDamage.getId()));
//    }
//
//    @Test
//    public void deleteTest_shouldDeleteBookDamage() throws RepositoryException {
//        //given
//        Long bookDamageId = 1L;
//
//        // when
//        boolean isDeleted = bookDamageRepository.delete(bookDamageId);
//
//        //then
//        Assertions.assertTrue(isDeleted);
//    }
//}