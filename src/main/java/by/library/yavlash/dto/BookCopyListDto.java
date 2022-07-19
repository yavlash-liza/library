package by.library.yavlash.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BookCopyListDto {
     Long id;
     String title;
     String imagePath;
     int pricePerDay;
}