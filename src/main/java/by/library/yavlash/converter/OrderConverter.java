package by.library.yavlash.converter;

import by.library.yavlash.dto.OrderDto;
import by.library.yavlash.dto.OrderListDto;
import by.library.yavlash.dto.OrderSaveDto;
import by.library.yavlash.entity.Order;
import by.library.yavlash.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderConverter {
    public static OrderDto toDto(Order order) {
        if (order == null) {
            return null;
        }
        return OrderDto.builder()
                .id(order.getId())
                .orderStatus(order.getOrderStatus())
                .startDate(order.getStartDate())
                .endDate(order.getEndDate())
                .price(order.getPrice())
                .userId(order.getUser().getId())
                .bookCopies(BookCopyConverter.toBookCopyListDtos(new ArrayList<>(order.getBookCopies())))
                .bookDamages(BookDamageConverter.toDtos(new ArrayList<>(order.getBookDamages())))
                .build();
    }

    public static List<OrderDto> toDtos(List<Order> order) {
        if (order == null) {
            return null;
        }
        return order.stream()
                .map(OrderConverter::toDto)
                .collect(Collectors.toList());
    }

    public static OrderListDto toOrderListDto(Order order) {
        if (order == null) {
            return null;
        }
        return OrderListDto.builder()
                .id(order.getId())
                .orderStatus(order.getOrderStatus())
                .startDate(order.getStartDate())
                .endDate(order.getEndDate())
                .price(order.getPrice())
                .build();
    }

    public static List<OrderListDto> toOrderListDtos(List<Order> order) {
        if (order == null) {
            return null;
        }
        return order.stream()
                .map(OrderConverter::toOrderListDto)
                .collect(Collectors.toList());
    }

    static Set<Order> orderListDtoListToOrderSet(List<OrderListDto> list) {
        if (list == null) {
            return null;
        }
        return list.stream()
                .map(OrderConverter::fromListDto)
                .collect(Collectors.toSet());
    }

    public static Order fromListDto(OrderListDto orderListDto) {
        if (orderListDto == null) {
            return null;
        }
        return Order.builder()
                .id(orderListDto.getId())
                .orderStatus(orderListDto.getOrderStatus())
                .startDate(orderListDto.getStartDate())
                .endDate(orderListDto.getEndDate())
                .price(orderListDto.getPrice())
                .build();
    }

    public static Order fromSaveDto(OrderSaveDto orderSaveDto) {
        if (orderSaveDto == null) {
            return null;
        }
        return Order.builder()
                .id(orderSaveDto.getId())
                .startDate(orderSaveDto.getStartDate())
                .endDate(orderSaveDto.getEndDate())
                .price(orderSaveDto.getPrice())
                .user(User.builder().id(orderSaveDto.getUserId()).build())
                .bookCopies(BookCopyConverter.fromListLongtoSetBookCopies(orderSaveDto.getBookCopiesId()))
                .build();
    }

    public static Order fromDto(OrderDto orderDto) {
        if (orderDto == null) {
            return null;
        }
        return Order.builder()
                .id(orderDto.getId())
                .orderStatus(orderDto.getOrderStatus())
                .startDate(orderDto.getStartDate())
                .endDate(orderDto.getEndDate())
                .price(orderDto.getPrice())
                .user(User.builder().id(orderDto.getUserId()).build())
                .bookDamages(BookDamageConverter.bookDamageListDtoListToBookDamageSet(orderDto.getBookDamages()))
                .bookCopies(BookCopyConverter.bookCopyListDtoListToBookCopySet(orderDto.getBookCopies()))
                .build();
    }
}