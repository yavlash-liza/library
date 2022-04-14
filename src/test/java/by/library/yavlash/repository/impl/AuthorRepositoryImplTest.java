package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.Author;
import by.library.yavlash.exception.RepositoryException;
import by.library.yavlash.repository.BaseRepositoryTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class AuthorRepositoryImplTest extends BaseRepositoryTest {
    private final AuthorRepositoryImpl authorRepository;

    public AuthorRepositoryImplTest() {
        authorRepository = new AuthorRepositoryImpl();
    }

    @Test
    public void findByIdTest_shouldReturnTheFirstAuthorInDB() throws RepositoryException {
        //given
        Author expected = findAuthorForFindById();

        //when
        Author actual = authorRepository.findById(expected.getId());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_shouldReturnListOfAllAuthor() throws RepositoryException {
        //given
        List<Author> expected = findAuthorsForFindAll();

        //when
        List<Author> actual = authorRepository.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldReturnAddedAuthor() throws RepositoryException {
        //given
        List<Author> expected = findAuthorsForFindAll();
        Assertions.assertEquals(5, expected.size());

        //when
        Author newAuthorActual = Author.builder().firstName("Mikhail").lastName("Lermontov").birthDate(LocalDate.of(1999, 8, 8)).imagePath("image path").build();
        boolean isAdded = authorRepository.add(newAuthorActual);
        Author newAuthorExpected = Author.builder().id(6L).firstName("Mikhail").lastName("Lermontov").birthDate(LocalDate.of(1999, 8, 8)).imagePath("image path").build();
        expected.add(newAuthorExpected);

        //then
        Assertions.assertTrue(isAdded);
        Assertions.assertEquals(newAuthorExpected, newAuthorActual);
        Assertions.assertEquals(newAuthorExpected, authorRepository.findById(newAuthorActual.getId()));
    }

    @Test
    void updateTest_shouldUpdateAuthor() throws RepositoryException {
        //given
        Author author = Author.builder().id(2L).firstName("Mikhail").lastName("Lermontov").birthDate(LocalDate.of(1998, 8, 8)).imagePath("image path").build();

        // when
        boolean isUpdated = authorRepository.update(author);

        //then
        Assertions.assertTrue(isUpdated);
        Assertions.assertEquals(author, authorRepository.findById(author.getId()));
    }

    @Test
    public void deleteTest_shouldDeleteAuthor() throws RepositoryException {
        //given
        Author expected = Author.builder().id(2L).firstName("Mikhail").lastName("Lermontov").birthDate(LocalDate.of(1998, 8, 8)).imagePath("image path").build();

        // when
        boolean isDeleted = authorRepository.delete(expected.getId());

        //then
        Assertions.assertTrue(isDeleted);
        Assertions.assertThrows(RepositoryException.class, () -> authorRepository.findById(expected.getId()));
    }
}