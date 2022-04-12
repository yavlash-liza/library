package by.library.yavlash.mapper;

import by.library.yavlash.dto.OrderDto;
import by.library.yavlash.dto.OrderListDto;
import by.library.yavlash.dto.OrderSaveDto;
import by.library.yavlash.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "userId", source = "order.user.id")
    OrderDto toDto(Order order);
    List<OrderDto> toDtos(List<Order> order);
    OrderListDto toOrderListDto(Order order);
    List<OrderListDto> toOrderListDtos(List<Order> order);
    Order fromSaveDto(OrderSaveDto orderSaveDto);
    Order fromDto(OrderDto orderDto);
}