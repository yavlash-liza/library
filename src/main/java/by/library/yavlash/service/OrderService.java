package by.library.yavlash.service;

import by.library.yavlash.dto.OrderDto;
import by.library.yavlash.dto.OrderListDto;
import by.library.yavlash.dto.OrderSaveDto;
import by.library.yavlash.exception.ServiceException;

import java.util.List;

public interface OrderService {
    void addOrder(OrderSaveDto orderSaveDto) throws ServiceException;
    List<OrderListDto> findAllOrders() throws ServiceException;
    OrderDto findOrderById(Long orderId) throws ServiceException;
    void deleteOrder(Long orderId) throws ServiceException;
    void updateOrder(OrderDto orderDto) throws ServiceException;
}