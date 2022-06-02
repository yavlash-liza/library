package by.library.yavlash.mapper;

import by.library.yavlash.dto.OrderDto;
import by.library.yavlash.dto.OrderListDto;
import by.library.yavlash.dto.OrderSaveDto;
import by.library.yavlash.entity.BaseEntity;
import by.library.yavlash.entity.BookCopy;
import by.library.yavlash.entity.BookDamage;
import by.library.yavlash.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "userId", source = "order.user.id")
    @Mapping(target = "bookCopies", source = "order.bookCopies")
    @Mapping(target = "bookDamages", source = "order.bookDamages")
    OrderDto toDto(Order order);

    @Mapping(target = "user.id", source = "orderSaveDto.userId")
    @Mapping(target = "bookCopies", source = "orderSaveDto.bookCopiesId")
    Order fromSaveDto(OrderSaveDto orderSaveDto);

    OrderListDto toListDto(Order order);

    List<OrderListDto> toListDto(List<Order> order);

    default List<Long> toLongList(Set<BookDamage> bookDamages) {
        return bookDamages.stream().map(BaseEntity::getId)
                .collect(Collectors.toList());
    }

    default Set<BookCopy> fromLongList(List<Long> bookCopiesId) {
        return bookCopiesId.stream().map(bookCopyId -> BookCopy.builder().id(bookCopyId).build())
                .collect(Collectors.toSet());
    }
}