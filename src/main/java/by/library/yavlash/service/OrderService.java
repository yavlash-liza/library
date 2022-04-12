package by.library.yavlash.service;

import by.library.yavlash.dto.OrderDto;
import by.library.yavlash.dto.OrderListDto;
import by.library.yavlash.dto.OrderSaveDto;
import by.library.yavlash.exception.ServiceException;

import java.util.List;

public interface OrderService {
    OrderDto findOrderById(Long orderId) throws ServiceException;
    List<OrderListDto> findAllOrders() throws ServiceException;
    boolean addOrder(OrderSaveDto orderSaveDto) throws ServiceException;
    boolean updateOrder(OrderDto orderDto) throws ServiceException;
    boolean deleteOrder(Long orderId) throws ServiceException;
}