package by.library.yavlash.dto.order;

import by.library.yavlash.dto.BookCopyDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class OrderSaveDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private int price;
    private Long userId;
    private Set<BookCopyDto> bookCopies;
}