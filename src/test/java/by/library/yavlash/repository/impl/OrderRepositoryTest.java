package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.Order;
import by.library.yavlash.entity.User;
import by.library.yavlash.repository.BaseRepositoryTest;
import by.library.yavlash.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

class OrderRepositoryTest extends BaseRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void findByIdTest_shouldReturnTheFirstOrderInDB() {
        //given
        Order order = findOrderForFindById();
        Optional<Order> expected = Optional.of(order);

        //when
        Optional<Order> actual = orderRepository.findById(order.getId());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_shouldReturnListOfAllOrders() {
        //given
        List<Order> expected = findOrdersForFindAll();

        //when
        List<Order> actual = orderRepository.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllByDeletedTest_shouldReturnListOfAllOrders() {
        //given
        PageRequest pageReq = PageRequest.of(0, 10);
        Page<Order> expected = findOrdersForPage(pageReq);

        //when
        Page<Order> actual = orderRepository.findAllByDeleted(false, pageReq);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldReturnAddedOrder() {
        //given
        List<Order> expected = findOrdersForFindAll();
        Assertions.assertEquals(5, expected.size());

        //when
        Order newOrderActual = Order.builder().orderStatus("NEW").startDate(LocalDate.of(1999, 7, 6)).endDate(LocalDate.of(1988, 5, 6)).price(223).user(User.builder().id(4L).build()).build();
        Order isAdded = orderRepository.save(newOrderActual);
        Order newOrderExpected = Order.builder().id(6L).orderStatus("NEW").startDate(LocalDate.of(1999, 7, 6)).endDate(LocalDate.of(1988, 5, 6)).price(223).user(User.builder().id(4L).build()).build();
        expected.add(newOrderExpected);

        //then
        Assertions.assertNotNull(isAdded);
        Assertions.assertEquals(newOrderExpected, newOrderActual);
        Assertions.assertEquals(Optional.of(newOrderExpected), orderRepository.findById(newOrderActual.getId()));
    }

    @Test
    void updateTest_shouldUpdateOrder() {
        //given
        Order order = Order.builder().id(2L).orderStatus("NEW").startDate(LocalDate.of(1998, 6, 6)).endDate(LocalDate.of(1998, 6, 6)).price(243).user(User.builder().id(1L).build()).build();

        // when
        Order isUpdated = orderRepository.save(order);

        //then
        Assertions.assertNotNull(isUpdated);
        Assertions.assertEquals(Optional.of(order), orderRepository.findById(order.getId()));
    }
}