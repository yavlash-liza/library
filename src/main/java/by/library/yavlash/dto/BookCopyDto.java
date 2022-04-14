package by.library.yavlash.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class BookCopyDto {
    private Long id;
    private String title;
    private int pagesNumber;
    private String status;
    private LocalDate registrationDate;
    private int pricePerDay;
    private String imagePath;
    private List<AuthorListDto> authors;
    private List<GenreDto> genres;
    private List<Long> bookDamagesId;
}