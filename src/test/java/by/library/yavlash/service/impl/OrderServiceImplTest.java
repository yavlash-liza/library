package by.library.yavlash.service.impl;

import by.library.yavlash.dto.BookCopyListDto;
import by.library.yavlash.dto.OrderDto;
import by.library.yavlash.dto.OrderListDto;
import by.library.yavlash.dto.OrderSaveDto;
import by.library.yavlash.entity.Book;
import by.library.yavlash.entity.BookCopy;
import by.library.yavlash.entity.BookDamage;
import by.library.yavlash.entity.Order;
import by.library.yavlash.entity.User;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void findOrderById() throws ServiceException {
        Long id = 1L;
        Set<BookCopy> bookCopies = new HashSet<>() {{add(BookCopy.builder().id(2L).book(Book.builder().id(1L).build()).build());}};
        Set<BookDamage> bookDamages = new HashSet<>() {{add(BookDamage.builder().id(2L).build());}};

        OrderDto expected = OrderDto.builder().id(id).userId(2L)
                .bookCopies(new ArrayList<>() {{add(BookCopyListDto.builder().id(2L).build());}})
                .bookDamages(new ArrayList<>() {{add(2L);}})
                .build();

        //when
        when(orderRepository.findById(id)).thenReturn(Optional.of(Order.builder().id(id).user(User.builder().id(2L).build())
                .bookDamages(bookDamages)
                .bookCopies(bookCopies)
                .build()));
        OrderDto actual = orderService.findOrderById(id);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllOrders() throws ServiceException {
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
    void addOrder() throws ServiceException {
        //given
        OrderSaveDto orderSaveDto = OrderSaveDto.builder()
                .price(13)
                .userId(1L)
                .bookCopiesId(new ArrayList<>() {{
                    add(2L);
                }}).build();

        // when
        boolean actual = orderService.addOrder(orderSaveDto);

        //then
        Assertions.assertTrue(actual);
    }

    @Test
    void updateOrder() throws ServiceException {
        //given
        List<Long> bookDamageList = new ArrayList<>() {{add(2L);}};
        OrderDto expected = OrderDto.builder().id(4L).orderStatus("ACCEPTED").bookDamages(bookDamageList).build();

        //when
        boolean actual = orderService.updateOrder(expected);

        //then
        Assertions.assertTrue(actual);
    }

    @Test
    void deleteOrder() throws ServiceException {
        //given
        Long id = 3L;

        //when
        boolean actual = orderService.deleteOrder(id);

        //then
        Assertions.assertTrue(actual);
    }
}