package by.library.yavlash.converter;

import by.library.yavlash.dto.BookSaveDto;
import by.library.yavlash.entity.Author;
import by.library.yavlash.entity.Book;
import by.library.yavlash.entity.Genre;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BookConverter {
    public static Book fromSaveDto(BookSaveDto bookSaveDto) {
        if ( bookSaveDto == null ) {
            return null;
        }
        return Book.builder()
                .id(bookSaveDto.getId())
                .title(bookSaveDto.getTitle())
                .pagesNumber(bookSaveDto.getPagesNumber())
                .imagePath(bookSaveDto.getImagePath())
                .genres(constructGenres(bookSaveDto.getGenresId()))
                .authors(constructAuthors(bookSaveDto.getAuthorsId()))
                .build();
    }

    private static Set<Genre> constructGenres(List<Long> genresId) {
        return genresId.stream()
                .map(genreId -> Genre.builder().id(genreId).build())
                .collect(Collectors.toSet());
    }

    private static Set<Author> constructAuthors(List<Long> authorsId) {
        return authorsId.stream()
                .map(authorId -> Author.builder().id(authorId).build())
                .collect(Collectors.toSet());
    }
}