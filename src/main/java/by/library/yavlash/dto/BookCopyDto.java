package by.library.yavlash.dto;

import by.library.yavlash.dto.book.BookDto;
import by.library.yavlash.dto.order.OrderDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class BookCopyDto {
    private Long id;
    private String status;
    private LocalDate registrationDate;
    private String imagePath;
    private int pricePerDay;
    private BookDto book;
    private Set<OrderDto> orders;
    private Set<BookDamageDto> bookDamages;
}