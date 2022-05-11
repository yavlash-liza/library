package by.library.yavlash.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Jacksonized
@Builder
@Value
public class BookCopySaveDto {
     Long id;
     String status;
     LocalDate registrationDate;
     int pricePerDay;
     String imagePath;
     Long bookId;
}