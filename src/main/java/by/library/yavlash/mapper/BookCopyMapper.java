package by.library.yavlash.mapper;

import by.library.yavlash.dto.BookCopyDto;
import by.library.yavlash.dto.BookCopyListDto;
import by.library.yavlash.dto.BookCopySaveDto;
import by.library.yavlash.entity.BookCopy;
import by.library.yavlash.entity.BookDamage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookCopyMapper {
    @Mapping(target = "title", source = "bookCopy.book.title")
    @Mapping(target = "pagesNumber", source = "bookCopy.book.pagesNumber")
    @Mapping(target = "bookDamagesId", source = "bookCopy.bookDamages")
    @Mapping(target = "genres", source = "bookCopy.book.genres")
    @Mapping(target = "authors", source = "bookCopy.book.authors")
    BookCopyDto toDto(BookCopy bookCopy);

    @Mapping(target = "book.id", source = "bookCopySaveDto.bookId")
    BookCopy fromSaveDto(BookCopySaveDto bookCopySaveDto);

    @Mapping(target = "title", source = "bookCopy.book.title")
    BookCopyListDto toListDto(BookCopy bookCopy);

    List<BookCopyListDto> toListDto(List<BookCopy> bookCopies);

    default List<Long> toLongList(Set<BookDamage> bookDamages) {
        return bookDamages.stream().map(BookDamage::getId)
                .collect(Collectors.toList());
    }

    default Set<BookCopy> fromListLong(List<Long> bookCopiesId) {
        return bookCopiesId.stream().map(id -> BookCopy.builder().id(id).build())
                .collect(Collectors.toSet());
    }
}