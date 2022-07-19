package by.library.yavlash.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class BookCopySaveDto {
     Long id;
     String status;
     LocalDate registrationDate;
     int pricePerDay;
     String imagePath;
     Long bookId;
}