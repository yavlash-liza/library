package by.library.yavlash.dto.book;

import by.library.yavlash.dto.GenreDto;
import by.library.yavlash.dto.author.AuthorDto;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class BookListDto {
    private Long id;
    private String title;
    private int pagesNumber;
    private int books;
    private Set<AuthorDto> authors;
    private Set<GenreDto> genres;
}