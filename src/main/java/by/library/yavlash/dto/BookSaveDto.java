package by.library.yavlash.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class BookSaveDto {
     Long id;
     String title;
     int pagesNumber;
     String imagePath;
     List<Long> authorsId;
     List<Long> genresId;
}