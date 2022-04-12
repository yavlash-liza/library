package by.library.yavlash.mapper;

import by.library.yavlash.dto.BookCopyDto;
import by.library.yavlash.dto.BookCopyListDto;
import by.library.yavlash.dto.BookCopySaveDto;
import by.library.yavlash.entity.BookCopy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookCopyMapper {
    @Mapping(target = "bookDamages", source = "bookCopy.bookDamages")
    @Mapping(target = "genres", source = "bookCopy.book.genres")
    @Mapping(target = "authors", source = "bookCopy.book.authors")
    BookCopyDto toDto(BookCopy bookCopy);
    List<BookCopyListDto> toBookCopyListDtos(List<BookCopy> bookCopies);
    BookCopy fromSaveDto(BookCopySaveDto bookCopySaveDto);
    BookCopy fromDto(BookCopyDto bookCopyDto);
}