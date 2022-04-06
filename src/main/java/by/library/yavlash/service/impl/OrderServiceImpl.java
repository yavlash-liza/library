package by.library.yavlash.service.impl;

import by.library.yavlash.dto.OrderDto;
import by.library.yavlash.dto.OrderListDto;
import by.library.yavlash.dto.OrderSaveDto;
import by.library.yavlash.entity.Order;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.mapper.OrderMapper;
import by.library.yavlash.repository.OrderRepository;
import by.library.yavlash.service.OrderService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    public OrderRepository orderRepository;
    public OrderMapper orderMapper;

    @Override
    public void addOrder(OrderSaveDto orderSaveDto) throws ServiceException {
        try {
            Order order = orderMapper.fromSaveDto(orderSaveDto);
            orderRepository.add(order);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not added: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    @Override
    public List<OrderListDto> findAllOrders() throws ServiceException {
        try {
            List<Order> orders = orderRepository.findAll();
            return orderMapper.toOrderListDtos(orders);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s were not found: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    @Override
    public OrderDto findOrderById(Long orderId) throws ServiceException {
        try {
            Order order = orderRepository.findById(orderId);
            return orderMapper.toDto(order);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not found: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    @Override
    public void deleteOrder(Long orderId) throws ServiceException {
        try {
            orderRepository.delete(orderId);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not deleted: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    @Override
    public void updateOrder(OrderDto orderDto) throws ServiceException {
        try {
            Order order = orderMapper.fromDto(orderDto);
            orderRepository.update(order);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not updated: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }
}