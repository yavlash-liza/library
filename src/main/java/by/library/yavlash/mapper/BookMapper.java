package by.library.yavlash.mapper;

import by.library.yavlash.dto.book.BookDto;
import by.library.yavlash.dto.book.BookListDto;
import by.library.yavlash.dto.book.BookSaveDto;
import by.library.yavlash.entity.Book;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDto toDto(Book book);
    List<BookDto> toDtos(List<Book> book);
    BookListDto toBookListDto(Book book);
    List<BookListDto> toBookListDtos(List<Book> book);
    Book fromSaveDto(BookSaveDto bookSaveDto);
}