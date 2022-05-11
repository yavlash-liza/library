package by.library.yavlash.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
public class BookCopyListDto {
     Long id;
     String title;
     String imagePath;
     int pricePerDay;
}