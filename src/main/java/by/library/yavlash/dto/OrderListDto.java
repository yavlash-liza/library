package by.library.yavlash.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Jacksonized
@Builder
@Value
public class OrderListDto {
     Long id;
     String orderStatus;
     LocalDate startDate;
     LocalDate endDate;
     int price;
}