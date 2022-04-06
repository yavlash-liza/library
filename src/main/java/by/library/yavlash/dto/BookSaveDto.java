package by.library.yavlash.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BookSaveDto {
    private Long id;
    private String title;
    private int pagesNumber;
    private String imagePath;
    private int quantity;
    private List<Long> authorsId;
    private List<Long> genresId;
}