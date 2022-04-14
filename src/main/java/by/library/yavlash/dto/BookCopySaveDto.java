package by.library.yavlash.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BookCopySaveDto {
    private Long id;
    private String status;
    private LocalDate registrationDate;
    private int pricePerDay;
    private String imagePath;
    private Long bookId;
}