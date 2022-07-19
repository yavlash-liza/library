package by.library.yavlash.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class OrderListDto {
     Long id;
     String orderStatus;
     LocalDate startDate;
     LocalDate endDate;
     int price;
}