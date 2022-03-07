package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.Genre;
import by.library.yavlash.exception.RepositoryException;
import by.library.yavlash.repository.BaseRepositoryTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class GenreRepositoryImplTest extends BaseRepositoryTest {
    private final GenreRepositoryImpl genreRepository;

    public GenreRepositoryImplTest() {
        genreRepository = new GenreRepositoryImpl();
    }

    @Test
    public void findByIdTest_shouldReturnTheFirstGenreInDB() throws RepositoryException {
        //given
        Genre expected = findGenreForFindById();

        //when
        Genre actual = genreRepository.findById(expected.getId());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_shouldReturnListOfAllGenres() throws RepositoryException {
        //given
        List<Genre> expected = findGenresForFindAll();

        //when
        List<Genre> actual = genreRepository.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldReturnAddedGenre() throws RepositoryException {
        //given
        List<Genre> expected = findGenresForFindAll();
        Assertions.assertEquals(7, expected.size());

        //when
        Genre newGenreActual = Genre.builder().genreName("tale").build();
        boolean isAdded = genreRepository.add(newGenreActual);
        Genre newGenreExpected = Genre.builder().id(8L).genreName("tale").build();
        expected.add(newGenreExpected);

        //then
        Assertions.assertTrue(isAdded);
        Assertions.assertEquals(newGenreExpected, newGenreActual);
        Assertions.assertEquals(newGenreExpected, genreRepository.findById(newGenreActual.getId()));
    }

    @Test
    void updateTest_shouldUpdateGenre() throws RepositoryException {
        //given
        Genre genre = Genre.builder().id(2L).genreName("tale").build();

        // when
        boolean isUpdated = genreRepository.update(genre);

        //then
        Assertions.assertTrue(isUpdated);
        Assertions.assertEquals(genre, genreRepository.findById(genre.getId()));
    }

    @Test
    public void deleteTest_shouldDeleteGenre() throws RepositoryException {
        //given
        Genre expected = Genre.builder().id(2L).genreName("tale").build();

        // when
        boolean isDeleted = genreRepository.delete(expected.getId());

        //then
        Assertions.assertTrue(isDeleted);
        Assertions.assertNotEquals(expected, genreRepository.findById(expected.getId()));
    }
}