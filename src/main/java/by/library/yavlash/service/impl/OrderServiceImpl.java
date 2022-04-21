package by.library.yavlash.service.impl;

import by.library.yavlash.converter.OrderConverter;
import by.library.yavlash.dto.OrderDto;
import by.library.yavlash.dto.OrderListDto;
import by.library.yavlash.dto.OrderSaveDto;
import by.library.yavlash.entity.Order;
import by.library.yavlash.entity.User;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.repository.OrderRepository;
import by.library.yavlash.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public OrderDto findOrderById(Long orderId) throws ServiceException {
        try {
            Order order = orderRepository.findById(orderId);
            return OrderConverter.toDto(order);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not found: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    @Override
    public List<OrderListDto> findAllOrders() throws ServiceException {
        try {
            List<Order> orders = orderRepository.findAll();
            return OrderConverter.toOrderListDtos(orders);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s were not found: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    @Override
    public boolean addOrder(OrderSaveDto orderSaveDto) throws ServiceException {
        try {
            Order order = OrderConverter.fromSaveDto(orderSaveDto);
            order.setUser(User.builder().id(orderSaveDto.getUserId()).build());
            orderRepository.add(order);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not added: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    @Override
    public boolean deleteOrder(Long orderId) throws ServiceException {
        try {
            orderRepository.delete(orderId);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not deleted: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    @Override
    public boolean updateOrder(OrderDto orderDto) throws ServiceException {
        try {
            Order order = OrderConverter.fromDto(orderDto);
            orderRepository.update(order);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not updated: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }
}