package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.Genre;
import by.library.yavlash.repository.BaseRepositoryTest;
import by.library.yavlash.repository.GenreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

class GenreRepositoryTest extends BaseRepositoryTest {
    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void findByIdTest_shouldReturnTheFirstGenreInDB() {
        //given
        Genre genre = findGenreForFindById();
        Optional<Genre> expected = Optional.of(genre);

        //when
        Optional<Genre> actual = genreRepository.findById(genre.getId());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_shouldReturnListOfAllGenres() {
        //given
        List<Genre> expected = findGenresForFindAll();

        //when
        List<Genre> actual = genreRepository.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldReturnAddedGenre() {
        //given
        List<Genre> expected = findGenresForFindAll();
        Assertions.assertEquals(7, expected.size());

        //when
        Genre newGenreActual = Genre.builder().genreName("tale").build();
        Genre isAdded = genreRepository.save(newGenreActual);
        Genre newGenreExpected = Genre.builder().id(8L).genreName("tale").build();
        expected.add(newGenreExpected);

        //then
        Assertions.assertNotNull(isAdded);
        Assertions.assertEquals(newGenreExpected, newGenreActual);
        Assertions.assertEquals(Optional.of(newGenreExpected), genreRepository.findById(newGenreActual.getId()));
    }

    @Test
    void updateTest_shouldUpdateGenre() {
        //given
        Genre genre = Genre.builder().id(2L).genreName("tale").build();

        // when
        Genre isUpdated = genreRepository.save(genre);

        //then
        Assertions.assertNotNull(isUpdated);
        Assertions.assertEquals(Optional.of(genre), genreRepository.findById(genre.getId()));
    }
}