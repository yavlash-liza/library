package by.library.yavlash.service;

import by.library.yavlash.dto.OrderDto;
import by.library.yavlash.dto.OrderListDto;
import by.library.yavlash.dto.OrderSaveDto;
import by.library.yavlash.exception.ServiceException;

import java.util.List;

public interface OrderService {
    OrderDto findById(Long orderId) throws ServiceException;
    List<OrderListDto> findAll() throws ServiceException;
    boolean add(OrderSaveDto orderSaveDto) throws ServiceException;
    boolean update(OrderSaveDto orderSaveDto) throws ServiceException;
    boolean delete(Long orderId) throws ServiceException;
}