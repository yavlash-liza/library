package by.library.yavlash.service.impl;

import by.library.yavlash.dto.OrderDto;
import by.library.yavlash.dto.OrderListDto;
import by.library.yavlash.dto.OrderSaveDto;
import by.library.yavlash.entity.BookCopy;
import by.library.yavlash.entity.Order;
import by.library.yavlash.entity.User;
import by.library.yavlash.exception.RepositoryException;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.mapper.OrderMapperImpl;
import by.library.yavlash.repository.OrderRepository;
import by.library.yavlash.repository.impl.OrderRepositoryImpl;
import by.library.yavlash.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    public OrderServiceImplTest() {
        orderRepository = mock(OrderRepositoryImpl.class);
        orderService = new OrderServiceImpl(orderRepository, new OrderMapperImpl());
    }

    @Test
    void addOrder() throws RepositoryException, ServiceException {
        //given && when
        when(orderRepository.findBookCopiesByBookCopiesId(new HashSet<>() {{
            add(2L);
        }}))
                .thenReturn(new HashSet<>() {{
                    add(BookCopy.builder().id(2L).build());
                }});

        when(orderRepository.add(Order.builder()
                .user(User.builder().id(1L).build())
                .bookCopies(new HashSet<>() {{
                    add(BookCopy.builder().id(2L).build());
                }})
                .price(13).build()))
                .thenReturn(true);
        boolean actual = orderService.addOrder(OrderSaveDto.builder()
                .price(13)
                .userId(1L)
                .bookCopiesId(new ArrayList<>() {{
                    add(2L);
                }}).build());

        //then
        Assertions.assertEquals(true, actual);
    }

    @Test
    void findAllOrders() throws RepositoryException, ServiceException {
        //given
        List<OrderListDto> expected = new ArrayList<>() {{
            add(OrderListDto.builder().id(1L).build());
            add(OrderListDto.builder().id(2L).build());
        }};

        //when
        when(orderRepository.findAll()).thenReturn(new ArrayList<>() {{
            add(Order.builder().id(1L).build());
            add(Order.builder().id(2L).build());
        }});
        List<OrderListDto> actual = orderService.findAllOrders();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findOrderById() throws RepositoryException, ServiceException {
        Long id = 1L;
        OrderDto expected = OrderDto.builder().id(id).userId(2L)
                .bookCopies(new ArrayList<>())
                .bookDamages(new ArrayList<>()).build();

        //when
        when(orderRepository.findById(id)).thenReturn(Order.builder().id(id).user(User.builder().id(2L).build()).build());
        when(orderRepository.findBookDamagesByOrderId(id)).thenReturn(new HashSet<>());
        OrderDto actual = orderService.findOrderById(id);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void deleteOrder() throws RepositoryException, ServiceException {
        //given
        Long id = 3L;

        //when
        when(orderRepository.delete(id)).thenReturn(true);
        boolean actual = orderService.deleteOrder(id);

        //then
        Assertions.assertEquals(true, actual);
    }

    @Test
    void updateOrder() throws RepositoryException, ServiceException {
        //given
        OrderDto expected = OrderDto.builder().id(4L).orderStatus("ACCEPTED").build();

        //when
        when(orderRepository.update(Order.builder().id(4L).orderStatus("ACCEPTED").build())).thenReturn(true);
        boolean actual = orderService.updateOrder(expected);

        //then
        Assertions.assertTrue(actual);
    }
}