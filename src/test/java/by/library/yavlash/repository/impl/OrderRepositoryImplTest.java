package by.library.yavlash.repository.impl;

import by.library.yavlash.entity.Order;
import by.library.yavlash.entity.User;
import by.library.yavlash.exception.RepositoryException;
import by.library.yavlash.repository.BaseRepositoryTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class OrderRepositoryImplTest extends BaseRepositoryTest {
    private final OrderRepositoryImpl orderRepository;

    public OrderRepositoryImplTest() {
        orderRepository = new OrderRepositoryImpl();
    }

    @Test
    public void findByIdTest_shouldReturnTheFirstOrderInDB() throws RepositoryException {
        //given
        Order expected = findOrderForFindById();

        //when
        Order actual = orderRepository.findById(expected.getId());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_shouldReturnListOfAllOrders() throws RepositoryException {
        //given
        List<Order> expected = findOrdersForFindAll();

        //when
        List<Order> actual = orderRepository.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldReturnAddedOrder() throws RepositoryException {
        //given
        List<Order> expected = findOrdersForFindAll();
        Assertions.assertEquals(5, expected.size());

        //when
        Order newOrderActual = Order.builder().orderStatus("NEW").startDate(LocalDate.of(1999, 7, 6)).endDate(LocalDate.of(1988, 5, 6)).price(223).user(User.builder().id(4L).build()).build();
        boolean isAdded = orderRepository.add(newOrderActual);
        Order newOrderExpected = Order.builder().id(6L).orderStatus("NEW").startDate(LocalDate.of(1999, 7, 6)).endDate(LocalDate.of(1988, 5, 6)).price(223).user(User.builder().id(4L).build()).build();
        expected.add(newOrderExpected);

        //then
        Assertions.assertTrue(isAdded);
        Assertions.assertEquals(newOrderExpected, newOrderActual);
        Assertions.assertEquals(newOrderExpected, orderRepository.findById(newOrderActual.getId()));
    }

    @Test
    void updateTest_shouldUpdateOrder() throws RepositoryException {
        //given
        Order order = Order.builder().id(2L).orderStatus("NEW").startDate(LocalDate.of(1998, 6, 6)).endDate(LocalDate.of(1998, 6, 6)).price(243).user(User.builder().id(1L).build()).build();

        // when
        boolean isUpdated = orderRepository.update(order);

        //then
        Assertions.assertTrue(isUpdated);
        Assertions.assertEquals(order, orderRepository.findById(order.getId()));
    }

    @Test
    public void deleteTest_shouldDeleteOrder() throws RepositoryException {
        //given
        Order expected = Order.builder().id(2L).orderStatus("NEW").startDate(LocalDate.of(1998, 6, 6)).endDate(LocalDate.of(1998, 6, 6)).price(243).user(User.builder().id(1L).build()).build();

        // when
        boolean isDeleted = orderRepository.delete(expected.getId());

        //then
        Assertions.assertTrue(isDeleted);
        Assertions.assertThrows(RepositoryException.class, () -> orderRepository.findById(expected.getId()));
    }
}