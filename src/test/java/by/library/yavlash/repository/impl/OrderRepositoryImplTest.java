//package by.library.yavlash.repository.impl;
//
//import by.library.yavlash.entity.Order;
//import by.library.yavlash.exception.RepositoryException;
//import by.library.yavlash.repository.BaseRepositoryTest;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDate;
//import java.util.List;
//
//class OrderRepositoryImplTest extends BaseRepositoryTest {
//    private final OrderRepositoryImpl orderRepository;
//
//    public OrderRepositoryImplTest() {
//        orderRepository = new OrderRepositoryImpl(getDataSource());
//    }
//
//    @Test
//    public void findByIdTest_shouldReturnTheFirstOrderInDB() throws RepositoryException {
//        //given
//        Order expected = findOrderForFindById();
//
//        //when
//        Order actual = orderRepository.findById(expected.getId());
//
//        //then
//        Assertions.assertEquals(expected, actual);
//    }
//
//    @Test
//    void findAllTest_shouldReturnListOfAllOrders() throws RepositoryException {
//        //given
//        List<Order> expected = findOrdersForFindAll();
//
//        //when
//        List<Order> actual = orderRepository.findAll();
//
//        //then
//        Assertions.assertEquals(expected, actual);
//    }
//
//    @Test
//    void addTest_shouldReturnAddedOrder() throws RepositoryException {
//        //given
//        Order expected = Order.builder().id(6L).orderStatus("NEW").startDate(LocalDate.of(1999, 7, 6)).endDate(LocalDate.of(1988, 5, 6)).price(223).userId(4L).build();
//        Order actual = Order.builder().orderStatus("NEW").startDate(LocalDate.of(1999, 7, 6)).endDate(LocalDate.of(1988, 5, 6)).price(223).userId(4L).build();
//
//        //when
//        actual = orderRepository.add(actual);
//
//        //then
//        Assertions.assertEquals(expected, actual);
//        Assertions.assertEquals(expected, orderRepository.findById(expected.getId()));
//    }
//
//    @Test
//    void updateTest_shouldUpdateOrder() throws RepositoryException {
//        //given
//        Order order = Order.builder().id(2L).orderStatus("NEW").startDate(LocalDate.of(1998, 6, 6)).endDate(LocalDate.of(1998, 6, 6)).price(243).userId(1L).build();
//
//        // when
//        boolean isUpdated = orderRepository.update(order);
//
//        //then
//        Assertions.assertTrue(isUpdated);
//        Assertions.assertEquals(order, orderRepository.findById(order.getId()));
//    }
//
//    @Test
//    public void deleteTest_shouldDeleteOrder() throws RepositoryException {
//        //given
//        Long orderId = 1L;
//
//        // when
//        boolean isDeleted = orderRepository.delete(orderId);
//
//        //then
//        Assertions.assertTrue(isDeleted);
//    }
//}