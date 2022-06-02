package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.Author;
import by.library.yavlash.repository.AuthorRepository;
import by.library.yavlash.repository.BaseRepositoryTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

class AuthorRepositoryTest extends BaseRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void findByIdTest_shouldReturnTheFirstAuthorInDB() {
        //given
        Author author = findAuthorForFindById();
        Optional<Author> expected = Optional.of(author);

        //when
        Optional<Author> actual = authorRepository.findById(author.getId());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_shouldReturnListOfAllAuthors() {
        //given
        List<Author> expected = findAuthorsForFindAll();

        //when
        List<Author> actual = authorRepository.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllByDeletedTest_shouldReturnListOfAllAuthors() {
        //given
        PageRequest pageReq = PageRequest.of(0, 10);
        Page<Author> expected = findAuthorsForPage(pageReq);

        //when
        Page<Author> actual = authorRepository.findAllByDeleted(false, pageReq);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllByDeletedAndLastNameTest_shouldReturnListOfAllAuthors() {
        //given
        PageRequest pageReq = PageRequest.of(0, 1);
        String search = "Tolstoy";
        Page<Author> expected = findAuthorsPageByTitle(pageReq);

        //when
        Page<Author> actual = authorRepository.findAllByDeletedAndLastName(false, search, pageReq);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldReturnAddedAuthor() {
        //given
        List<Author> expected = findAuthorsForFindAll();
        Assertions.assertEquals(5, expected.size());

        //when
        Author newAuthorActual = Author.builder().firstName("Mikhail").lastName("Lermontov").birthDate(LocalDate.of(1999, 8, 8)).imagePath("image path").build();
        Author isAdded = authorRepository.save(newAuthorActual);
        Author newAuthorExpected = Author.builder().id(6L).firstName("Mikhail").lastName("Lermontov").birthDate(LocalDate.of(1999, 8, 8)).imagePath("image path").build();
        expected.add(newAuthorExpected);

        //then
        Assertions.assertNotNull(isAdded);
        Assertions.assertEquals(newAuthorExpected, newAuthorActual);
        Assertions.assertEquals(Optional.of(newAuthorExpected), authorRepository.findById(newAuthorActual.getId()));
    }

    @Test
    void updateTest_shouldUpdateAuthor() {
        //given
        Author author = Author.builder().id(2L).firstName("Mikhail").lastName("Lermontov").birthDate(LocalDate.of(1998, 8, 8)).imagePath("image path").build();

        // when
        Author isUpdated = authorRepository.save(author);

        //then
        Assertions.assertNotNull(isUpdated);
        Assertions.assertEquals(Optional.of(author), authorRepository.findById(author.getId()));
    }
}