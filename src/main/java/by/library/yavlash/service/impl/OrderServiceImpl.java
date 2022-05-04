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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderDto findById(Long orderId) throws ServiceException {
        return orderRepository.findById(orderId).map(orderMapper::toDto)
                .orElseThrow(() -> new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not found")));
    }

    @Override
    public List<OrderListDto> findAll() throws ServiceException {
        try {
            List<Order> orders = orderRepository.findAll();
            return orderMapper.toOrderListDto(orders);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " were not found "));
        }
    }

    @Override
    public boolean add(OrderSaveDto orderSaveDto) throws ServiceException {
        try {
            Order order = orderMapper.fromSaveDto(orderSaveDto);
            orderRepository.save(order);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " was not added "));
        }
    }

    @Override
    public boolean update(OrderSaveDto orderSaveDto) throws ServiceException {
        try {
            Order order = orderMapper.fromSaveDto(orderSaveDto);
            orderRepository.save(order);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " was not updated "));
        }
    }

    @Override
    public boolean delete(Long orderId) throws ServiceException {
        Optional<Order> optional = orderRepository.findById(orderId);
        if (optional.isPresent()) {
            Order order = optional.get();
            order.setDeleted(true);
            orderRepository.save(order);
            return true;
        } else {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " was not deleted "));
        }
    }
}