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
        genreRepository = new GenreRepositoryImpl(getDataSource());
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
        Genre expected = Genre.builder().id(8L).genreName("tale").build();
        Genre actual = Genre.builder().genreName("tale").build();

        //when
        actual = genreRepository.add(actual);

        //then
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, genreRepository.findById(expected.getId()));
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
        Long genreId = 1L;

        // when
        boolean isDeleted = genreRepository.delete(genreId);

        //then
        Assertions.assertTrue(isDeleted);
    }
}