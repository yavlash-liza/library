package by.library.yavlash.mapper;

import by.library.yavlash.dto.author.AuthorDto;
import by.library.yavlash.dto.author.AuthorListDto;
import by.library.yavlash.dto.author.AuthorSaveDto;
import by.library.yavlash.entity.Author;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorDto toDto(Author author);
    List<AuthorDto> toDtos(List<Author> author);
    AuthorListDto toAuthorListDto(Author author);
    List<AuthorListDto> toAuthorListDtos(List<Author> author);
    Author fromSaveDto(AuthorSaveDto authorSaveDto);
}