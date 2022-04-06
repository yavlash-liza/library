package by.library.yavlash.dto.order;

import by.library.yavlash.dto.BookCopyDto;
import by.library.yavlash.dto.user.UserDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class OrderDto {
    private Long id;
    private String orderStatus;
    private LocalDate startDate;
    private LocalDate endDate;
    private int price;
    private UserDto user;
    private Set<BookCopyDto> bookCopies;
}