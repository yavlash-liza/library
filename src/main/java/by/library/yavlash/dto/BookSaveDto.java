package by.library.yavlash.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Jacksonized
@Builder
@Value
public class BookSaveDto {
     Long id;
     String title;
     int pagesNumber;
     String imagePath;
     List<Long> authorsId;
     List<Long> genresId;
}