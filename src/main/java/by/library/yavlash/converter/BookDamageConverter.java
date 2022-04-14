package by.library.yavlash.converter;

import by.library.yavlash.dto.BookDamageDto;
import by.library.yavlash.entity.BaseEntity;
import by.library.yavlash.entity.BookCopy;
import by.library.yavlash.entity.BookDamage;
import by.library.yavlash.entity.Order;
import by.library.yavlash.entity.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BookDamageConverter {
    public static BookDamageDto toDto(BookDamage bookDamage) {
        if ( bookDamage == null ) {
            return null;
        }
        return BookDamageDto.builder()
                .id(bookDamage.getId())
                .damageDescription(bookDamage.getDamageDescription())
                .imagePath(bookDamage.getImagePath())
                .bookCopyId(bookDamage.getBookCopy().getId())
                .orderId(bookDamage.getOrder().getId())
                .userId(bookDamage.getUser().getId())
                .build();
    }

    public static List<Long> toDtos(List<BookDamage> bookDamages) {
        if ( bookDamages == null ) {
            return null;
        }
        return bookDamages.stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList());
    }

    public static BookDamage fromSaveDto(BookDamageDto bookDamageDto) {
        if ( bookDamageDto == null ) {
            return null;
        }
        return BookDamage.builder()
                .id(bookDamageDto.getId())
                .imagePath(bookDamageDto.getImagePath())
                .damageDescription(bookDamageDto.getDamageDescription())
                .user(User.builder().id(bookDamageDto.getUserId()).build())
                .order(Order.builder().id(bookDamageDto.getOrderId()).build())
                .bookCopy(BookCopy.builder().id(bookDamageDto.getBookCopyId()).build())
                .build();
    }

    static Set<BookDamage> bookDamageListDtoListToBookDamageSet(List<Long> bookDamages) {
        return bookDamages.stream()
                .map(bookDamage -> BookDamage.builder().id(bookDamage).build())
                .collect(Collectors.toSet());
    }
}