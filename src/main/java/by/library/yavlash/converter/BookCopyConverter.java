package by.library.yavlash.converter;

import by.library.yavlash.dto.BookCopyDto;
import by.library.yavlash.dto.BookCopyListDto;
import by.library.yavlash.dto.BookCopySaveDto;
import by.library.yavlash.entity.Book;
import by.library.yavlash.entity.BookCopy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BookCopyConverter {
    public static BookCopyDto toDto(BookCopy bookCopy) {
        if (bookCopy == null) {
            return null;
        }
        return BookCopyDto.builder()
                .id(bookCopy.getId())
                .title(bookCopy.getBook().getTitle())
                .pagesNumber(bookCopy.getBook().getPagesNumber())
                .status(bookCopy.getStatus())
                .registrationDate(bookCopy.getRegistrationDate())
                .pricePerDay(bookCopy.getPricePerDay())
                .imagePath(bookCopy.getImagePath())
                .authors(AuthorConverter.toAuthorListDtos(new ArrayList<>(bookCopy.getBook().getAuthors())))
                .genres(GenreConverter.toListDto(new ArrayList<>(bookCopy.getBook().getGenres())))
                .bookDamagesId(BookDamageConverter.toDtos(new ArrayList<>(bookCopy.getBookDamages())))
                .build();
    }

    public static BookCopy fromSaveDto(BookCopySaveDto bookCopySaveDto) {
        if (bookCopySaveDto == null) {
            return null;
        }
        return BookCopy.builder()
                .id(bookCopySaveDto.getId())
                .status(bookCopySaveDto.getStatus())
                .registrationDate(bookCopySaveDto.getRegistrationDate())
                .imagePath(bookCopySaveDto.getImagePath())
                .pricePerDay(bookCopySaveDto.getPricePerDay())
                .book(Book.builder().id(bookCopySaveDto.getBookId()).build())
                .build();
    }

    public static BookCopy fromDto(BookCopyDto bookCopyDto) {
        if (bookCopyDto == null) {
            return null;
        }
        return BookCopy.builder()
                .id(bookCopyDto.getId())
                .status(bookCopyDto.getStatus())
                .registrationDate(bookCopyDto.getRegistrationDate())
                .imagePath(bookCopyDto.getImagePath())
                .pricePerDay(bookCopyDto.getPricePerDay())
                .bookDamages(BookDamageConverter.bookDamageListDtoListToBookDamageSet(bookCopyDto.getBookDamagesId()))
                .build();
    }

    static Set<BookCopy> fromListLongtoSetBookCopies(List<Long> bookCopiesId) {
        return bookCopiesId.stream()
                .map(bookCopyId -> BookCopy.builder().id(bookCopyId).build())
                .collect(Collectors.toSet());
    }

    private static BookCopy bookCopyListDtoToBookCopy(BookCopyListDto bookCopyListDto) {
        if (bookCopyListDto == null) {
            return null;
        }
        return BookCopy.builder()
                .id(bookCopyListDto.getId())
                .imagePath(bookCopyListDto.getImagePath())
                .pricePerDay(bookCopyListDto.getPricePerDay())
                .build();
    }

    static Set<BookCopy> bookCopyListDtoListToBookCopySet(List<BookCopyListDto> list) {
        if (list == null) {
            return null;
        }
        return list.stream()
                .map(BookCopyConverter::bookCopyListDtoToBookCopy)
                .collect(Collectors.toSet());
    }

    public static List<BookCopyListDto> toBookCopyListDtos(List<BookCopy> bookCopies) {
        if (bookCopies == null) {
            return null;
        }
        return bookCopies.stream()
                .map(BookCopyConverter::bookCopyToBookCopyListDto)
                .collect(Collectors.toList());
    }

    private static BookCopyListDto bookCopyToBookCopyListDto(BookCopy bookCopy) {
        if (bookCopy == null) {
            return null;
        }
        return BookCopyListDto.builder()
                .id(bookCopy.getId())
                .imagePath(bookCopy.getImagePath())
                .pricePerDay(bookCopy.getPricePerDay())
                .build();
    }
}