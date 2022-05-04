package by.library.yavlash.mapper;

import by.library.yavlash.dto.BookSaveDto;
import by.library.yavlash.entity.Author;
import by.library.yavlash.entity.Book;
import by.library.yavlash.entity.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "authors", source = "bookSaveDto.authorsId")
    @Mapping(target = "genres", source = "bookSaveDto.genresId")
    Book fromSaveDto(BookSaveDto bookSaveDto);

    default Set<Genre> constructGenres(List<Long> genresId) {
        return genresId.stream()
                .map(id -> Genre.builder().id(id).build())
                .collect(Collectors.toSet());
    }

    default Set<Author> constructAuthors(List<Long> authorsId) {
        return authorsId.stream()
                .map(id -> Author.builder().id(id).build())
                .collect(Collectors.toSet());
    }
}