package by.library.yavlash.dto;

import by.library.yavlash.dto.order.OrderDto;
import by.library.yavlash.dto.user.UserDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDamageDto {
    private Long id;
    private String imagePath;
    private String damageDescription;
    private UserDto user;
    private OrderDto order;
}