package by.library.yavlash.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class OrderSaveDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private int price;
    private Long userId;
    private List<Long> bookCopiesId;
}