package by.library.yavlash.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;
import java.util.List;

@Jacksonized
@Builder
@Value
public class OrderDto {
     Long id;
     String orderStatus;
     LocalDate startDate;
     LocalDate endDate;
     int price;
     Long userId;
     List<BookCopyListDto> bookCopies;
     List<Long> bookDamages;
}