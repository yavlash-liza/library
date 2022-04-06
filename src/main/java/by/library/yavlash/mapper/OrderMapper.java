package by.library.yavlash.mapper;

import by.library.yavlash.dto.order.OrderDto;
import by.library.yavlash.dto.order.OrderListDto;
import by.library.yavlash.dto.order.OrderSaveDto;
import by.library.yavlash.entity.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Order order);
    List<OrderDto> toDtos(List<Order> order);
    OrderListDto toOrderListDto(Order order);
    List<OrderListDto> toOrderListDtos(List<Order> order);
    Order fromSaveDto(OrderSaveDto orderSaveDto);
}