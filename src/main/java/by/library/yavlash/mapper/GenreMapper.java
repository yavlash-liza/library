package by.library.yavlash.mapper;

import by.library.yavlash.dto.GenreDto;
import by.library.yavlash.entity.Genre;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    List<GenreDto> toDto(List<Genre> genre);

    Genre fromSaveDto(GenreDto genreDto);
}