package by.library.yavlash.service.impl;

import by.library.yavlash.dto.GenreDto;
import by.library.yavlash.entity.Genre;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.repository.GenreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenreServiceImplTest {
    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreServiceImpl genreService;

    @Test
    void findAllGenres() throws ServiceException {
        //given
        List<GenreDto> expected = new ArrayList<>() {{
            add(GenreDto.builder().id(1L).build());
            add(GenreDto.builder().id(2L).build());
        }};

        //when
        when(genreRepository.findAll()).thenReturn(new ArrayList<>() {{
            add(Genre.builder().id(1L).build());
            add(Genre.builder().id(2L).build());
        }});
        List<GenreDto> actual = genreService.findAllGenres();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addGenre() throws ServiceException {
        //given && when
        boolean actual = genreService.addGenre(GenreDto.builder().genreName("fantasy").build());

        //then
        Assertions.assertTrue(actual);
    }

    @Test
    void deleteGenre() throws ServiceException {
        //given
        Long id = 3L;

        //when
        boolean actual = genreService.deleteGenre(id);

        //then
        Assertions.assertTrue(actual);
    }
}