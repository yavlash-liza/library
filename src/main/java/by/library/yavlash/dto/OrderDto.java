package by.library.yavlash.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
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