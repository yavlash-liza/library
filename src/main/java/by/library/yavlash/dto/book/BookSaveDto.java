package by.library.yavlash.dto.book;

import by.library.yavlash.dto.GenreDto;
import by.library.yavlash.dto.author.AuthorSaveDto;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class BookSaveDto {
    private Long id;
    private String title;
    private int pagesNumber;
    private String imagePath;
    private int books;
    private Set<AuthorSaveDto> authors;
    private Set<GenreDto> genres;
}