package by.library.yavlash.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookCopyListDto {
    private Long id;
    private String title;
    private String imagePath;
    private int pricePerDay;
}