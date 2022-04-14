package by.library.yavlash.converter;

import by.library.yavlash.dto.GenreDto;
import by.library.yavlash.entity.Book;
import by.library.yavlash.entity.BookCopy;
import by.library.yavlash.entity.Genre;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GenreConverter {
    private static Set<Genre> bookCopyBookGenres(BookCopy bookCopy) {
        if (bookCopy == null) {
            return null;
        }
        Book book = bookCopy.getBook();
        if (book == null) {
            return null;
        }
        Set<Genre> genres = book.getGenres();
        if (genres == null) {
            return null;
        }
        return genres;
    }

    protected static List<GenreDto> genreSetToGenreDtoList(Set<Genre> set) {
        if (set == null) {
            return null;
        }
        return set.stream()
                .map(GenreConverter::toDto)
                .collect(Collectors.toList());
    }

    public static GenreDto toDto(Genre genre) {
        if ( genre == null ) {
            return null;
        }
        return GenreDto.builder()
                .id(genre.getId())
                .genreName(genre.getGenreName())
                .build();
    }

    public static List<GenreDto> toListDto(List<Genre> genre) {
        if ( genre == null ) {
            return null;
        }
        return genre.stream()
                .map(GenreConverter::toDto)
                .collect(Collectors.toList());
    }

    public static Genre fromSaveDto(GenreDto genreDto) {
        if ( genreDto == null ) {
            return null;
        }
        return Genre.builder()
                .id(genreDto.getId())
                .genreName(genreDto.getGenreName())
                .build();
    }
}