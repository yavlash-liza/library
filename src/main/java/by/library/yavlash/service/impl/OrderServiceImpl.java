package by.library.yavlash.service.impl;

import by.library.yavlash.dto.OrderDto;
import by.library.yavlash.dto.OrderListDto;
import by.library.yavlash.dto.OrderSaveDto;
import by.library.yavlash.entity.BookCopy;
import by.library.yavlash.entity.Order;
import by.library.yavlash.entity.User;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.mapper.OrderMapper;
import by.library.yavlash.repository.OrderRepository;
import by.library.yavlash.service.OrderService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderDto findOrderById(Long orderId) throws ServiceException {
        try {
            Order order = orderRepository.findById(orderId);
//            order.setUser(User.builder().id(order.getUser().getId()).build());
//            order.setBookCopies(orderRepository.findBookCopiesByOrderId(orderId));
//            order.setBookDamages(orderRepository.findBookDamagesByOrderId(orderId));
            return orderMapper.toDto(order);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not found: {%s}", getClass().getSimpleName(), exception.getMessage()));
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
    public boolean addOrder(OrderSaveDto orderSaveDto) throws ServiceException {
        try {
            Order order = orderMapper.fromSaveDto(orderSaveDto);
            order.setUser(User.builder().id(orderSaveDto.getUserId()).build());
            order.setBookCopies(findBookCopiesByBookCopiesId(orderSaveDto.getBookCopiesId()));
            orderRepository.add(order);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not added: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    private Set<BookCopy> findBookCopiesByBookCopiesId(List<Long> bookCopiesId) {
        return bookCopiesId.stream()
                .map(bookCopyId -> BookCopy.builder().id(bookCopyId).build())
                .collect(Collectors.toSet());
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
            Order order = orderMapper.fromDto(orderDto);
            orderRepository.update(order);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not updated: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }
}