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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public OrderDto findOrderById(Long orderId) throws ServiceException {
        Optional<Order> order = orderRepository.findById(orderId);
        return order.map(OrderConverter::toDto)
                .orElseThrow(() -> new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not found")));
    }

    @Override
    public List<OrderListDto> findAllOrders() throws ServiceException {
        try {
            List<Order> orders = orderRepository.findAll();
            return OrderConverter.toOrderListDtos(orders);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " were not found "));
        }
    }

    @Override
    public boolean addOrder(OrderSaveDto orderSaveDto) throws ServiceException {
        try {
            Order order = OrderConverter.fromSaveDto(orderSaveDto);
            order.setUser(User.builder().id(orderSaveDto.getUserId()).build());
            orderRepository.save(order);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " was not added "));
        }
    }

    @Override
    public boolean deleteOrder(Long orderId) throws ServiceException {
        try {
            Optional<Order> order = orderRepository.findById(orderId);
            order.ifPresent(value -> value.setDeleted(true));
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " was not deleted "));
        }
    }

    @Override
    public boolean updateOrder(OrderDto orderDto) throws ServiceException {
        try {
            Order order = OrderConverter.fromDto(orderDto);
            orderRepository.save(order);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " was not updated "));
        }
    }
}