package by.library.yavlash.converter;

import by.library.yavlash.dto.AuthorDto;
import by.library.yavlash.dto.AuthorListDto;
import by.library.yavlash.dto.AuthorSaveDto;
import by.library.yavlash.dto.BookCopyListDto;
import by.library.yavlash.entity.Author;
import by.library.yavlash.entity.Book;
import by.library.yavlash.entity.BookCopy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AuthorConverter {
    public static AuthorDto toDto(Author author) {
        if (author == null) {
            return null;
        }
        return AuthorDto.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .birthDate(author.getBirthDate())
                .imagePath(author.getImagePath())
                .books(bookSetToBookCopyListDtoList(author.getBooks()))
                .build();
    }

    public static List<AuthorDto> toDtos(List<Author> author) {
        if (author == null) {
            return null;
        }
        return author.stream()
                .map(AuthorConverter::toDto)
                .collect(Collectors.toList());
    }

    public static AuthorListDto toAuthorListDto(Author author) {
        if (author == null) {
            return null;
        }
        return AuthorListDto.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .build();
    }

    public static List<AuthorListDto> toAuthorListDtos(List<Author> author) {
        if (author == null) {
            return null;
        }
        return author.stream()
                .map(AuthorConverter::toAuthorListDto)
                .collect(Collectors.toList());
    }

    public static Author fromSaveDto(AuthorSaveDto authorSaveDto) {
        if (authorSaveDto == null) {
            return null;
        }
        return Author.builder()
                .id(authorSaveDto.getId())
                .firstName(authorSaveDto.getFirstName())
                .lastName(authorSaveDto.getLastName())
                .birthDate(authorSaveDto.getBirthDate())
                .imagePath(authorSaveDto.getImagePath())
                .build();
    }

    private static List<BookCopyListDto> bookSetToBookCopyListDtoList(Set<Book> books) {
        if (books == null) {
            return null;
        }
        List<BookCopyListDto> bookCopiesListDto = new ArrayList<>();
        books.forEach(book -> {
            List<BookCopy> bookCopies = new ArrayList<>(book.getBookCopies());
            bookCopiesListDto.addAll(BookCopyConverter.toBookCopyListDtos(bookCopies));
        });
        return bookCopiesListDto;
    }
}