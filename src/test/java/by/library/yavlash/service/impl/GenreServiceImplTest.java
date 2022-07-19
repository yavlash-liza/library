package by.library.yavlash.service.impl;

import by.library.yavlash.dto.GenreDto;
import by.library.yavlash.entity.Genre;
import by.library.yavlash.mapper.GenreMapper;
import by.library.yavlash.repository.GenreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
class GenreServiceImplTest {
    @Mock
    private GenreRepository genreRepository;

    @Spy
    private GenreMapper genreMapper = Mappers.getMapper(GenreMapper.class);

    @InjectMocks
    private GenreServiceImpl genreService;

    @Test
    void findAllTest_shouldReturnListOfAllGenres() {
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
        List<GenreDto> actual = genreService.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldAddGenre() {
        //given && when
        boolean actual = genreService.add(GenreDto.builder().genreName("fantasy").build());

        //then
        Assertions.assertTrue(actual);
    }

    @Test
    void deleteTest_shouldDeleteGenre() {
        //given
        Long id = 3L;
        Genre expected = Genre.builder().id(id).books(new HashSet<>()).build();

        //when
        when(genreRepository.findById(id)).thenReturn(Optional.of(expected));
        when(genreRepository.save(expected)).thenReturn(expected);
        boolean actual = genreService.softDelete(id);

        //then
        Assertions.assertTrue(actual);
    }
}