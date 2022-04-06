package by.library.yavlash.mapper;

import by.library.yavlash.dto.AuthorDto;
import by.library.yavlash.dto.AuthorListDto;
import by.library.yavlash.dto.AuthorSaveDto;
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