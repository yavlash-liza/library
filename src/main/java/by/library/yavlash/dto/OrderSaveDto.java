package by.library.yavlash.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
public class OrderSaveDto {
    Long id;
    LocalDate startDate;
    LocalDate endDate;
    int price;
    Long userId;
    List<Long> bookCopiesId;
}