package by.library.yavlash.mapper;

import by.library.yavlash.dto.BookCopyDto;
import by.library.yavlash.entity.BookCopy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookCopyMapper {
    BookCopyDto toDto(BookCopy bookCopy);
    List<BookCopyDto> toDtos(List<BookCopy> bookCopy);
}