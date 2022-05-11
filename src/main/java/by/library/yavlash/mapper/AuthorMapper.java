package by.library.yavlash.mapper;

import by.library.yavlash.dto.AuthorDto;
import by.library.yavlash.dto.AuthorListDto;
import by.library.yavlash.dto.AuthorSaveDto;
import by.library.yavlash.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    @Mapping(target = "books", source = "author.books")
    AuthorDto toDto(Author author);

    List<AuthorListDto> toListDto(List<Author> author);

    Author fromSaveDto(AuthorSaveDto authorSaveDto);
}