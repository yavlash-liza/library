package by.library.yavlash.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;
import java.util.List;

@Jacksonized
@Builder
@Value
public class OrderSaveDto {
    Long id;
    LocalDate startDate;
    LocalDate endDate;
    int price;
    Long userId;
    List<Long> bookCopiesId;
}