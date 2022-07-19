package by.library.yavlash.service.impl;

import by.library.yavlash.dto.OrderDto;
import by.library.yavlash.dto.OrderListDto;
import by.library.yavlash.dto.OrderSaveDto;
import by.library.yavlash.entity.Order;
import by.library.yavlash.entity.User;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.mapper.BookCopyMapper;
import by.library.yavlash.mapper.OrderMapper;
import by.library.yavlash.repository.OrderRepository;
import by.library.yavlash.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final static String ORDER_CACHE = "orders";
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final BookCopyMapper bookCopyMapper;

    @Override
    @Cacheable(value = ORDER_CACHE, key = "#orderId")
    @Transactional
    public OrderDto findById(Long orderId) {
        return orderRepository.findById(orderId).map(orderMapper::toDto)
                .orElseThrow(() -> new ServiceException(String.format("Order was not found. id = %d", orderId)));
    }

    @Override
    @Cacheable(value = ORDER_CACHE)
    @Transactional
    public List<OrderListDto> findAll() {
        try {
            List<Order> orders = orderRepository.findAll();
            return orderMapper.toListDto(orders);
        } catch (Exception e) {
            throw new ServiceException("Orders were not found.", e);
        }
    }

    @Override
    @Cacheable(value = ORDER_CACHE, key = "{ #root.methodName, #page, #size,  #deleted }")
    @Transactional
    public List<OrderListDto> findListOrders(int page, int size, boolean deleted) {
        try {
            PageRequest pageReq = PageRequest.of(page, size);
            Page<OrderListDto> orderListDtos = orderRepository.findAllByDeleted(deleted, pageReq)
                    .map(orderMapper::toListDto);
            return orderListDtos.getContent();
        } catch (Exception e) {
            throw new ServiceException("Orders were not found.", e);
        }
    }

    @Override
    @CacheEvict(value = ORDER_CACHE, key = "#orderSaveDto.id")
    @Transactional
    public boolean add(OrderSaveDto orderSaveDto) {
        try {
            Order order = orderMapper.fromSaveDto(orderSaveDto);
            orderRepository.save(order);
            return true;
        } catch (Exception e) {
            throw new ServiceException(String.format("Order was not saved. %s", orderSaveDto), e);
        }
    }

    @Override
    @CacheEvict(value = ORDER_CACHE, key = "#orderSaveDto.id")
    @Transactional
    public boolean update(OrderSaveDto orderSaveDto) {
        Order order = orderRepository.findById(orderSaveDto.getId())
                .orElseThrow(() -> new ServiceException(
                        String.format("Order was not updated. Order was not found. id = %d", orderSaveDto.getId())
                ));
        try {
            settingUpdateFields(order, orderSaveDto);
            orderRepository.flush();
        } catch (Exception e) {
            throw new ServiceException(String.format("Order was not updated. %s", orderSaveDto), e);
        }
        return true;
    }

    private void settingUpdateFields(Order beforeUpdate, OrderSaveDto saveDto) {
        if (saveDto.getStartDate() != null) {
            beforeUpdate.setStartDate(saveDto.getStartDate());
        }
        if (saveDto.getEndDate() != null) {
            beforeUpdate.setEndDate(saveDto.getEndDate());
        }
        if (saveDto.getUserId() != null) {
            beforeUpdate.setUser(User.builder().id(saveDto.getUserId()).build());
        }
        if (saveDto.getPrice() != -1) {
            beforeUpdate.setPrice(saveDto.getPrice());
        }
        if (saveDto.getBookCopiesId() != null) {
            beforeUpdate.setBookCopies(bookCopyMapper.fromListLong(saveDto.getBookCopiesId()));
        }
    }

    @Override
    @CacheEvict(value = ORDER_CACHE, key = "#orderId")
    @Transactional
    public boolean softDelete(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ServiceException(
                        String.format("Order was not softly deleted. Order was not found. id = %d", orderId)
                ));
        try {
            order.setDeleted(true);
            orderRepository.flush();
            return true;
        } catch (Exception e) {
            throw new ServiceException(String.format(String.format("Order was not softly deleted. id = %d", orderId), e));
        }
    }
}