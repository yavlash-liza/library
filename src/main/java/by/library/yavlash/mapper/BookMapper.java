package by.library.yavlash.mapper;

import by.library.yavlash.dto.BookSaveDto;
import by.library.yavlash.entity.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    Book fromSaveDto(BookSaveDto bookSaveDto);
}