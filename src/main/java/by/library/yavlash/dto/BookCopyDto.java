package by.library.yavlash.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;
import java.util.List;

@Jacksonized
@Builder
@Value
public class BookCopyDto {
     Long id;
     String title;
     int pagesNumber;
     String status;
     LocalDate registrationDate;
     int pricePerDay;
     String imagePath;
     List<AuthorListDto> authors;
     List<GenreDto> genres;
     List<Long> bookDamagesId;
}