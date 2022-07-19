package by.library.yavlash.service;

import by.library.yavlash.dto.OrderDto;
import by.library.yavlash.dto.OrderListDto;
import by.library.yavlash.dto.OrderSaveDto;

import java.util.List;

public interface OrderService {
    OrderDto findById(Long orderId);
    List<OrderListDto> findAll();
    List<OrderListDto> findListOrders(int page, int size, boolean deleted);
    boolean add(OrderSaveDto orderSaveDto);
    boolean update(OrderSaveDto orderSaveDto);
    boolean softDelete(Long orderId);
}