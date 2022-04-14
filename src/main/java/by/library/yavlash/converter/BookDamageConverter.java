package by.library.yavlash.converter;

import by.library.yavlash.dto.BookDamageDto;
import by.library.yavlash.dto.BookDamageSaveDto;
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

    public static BookDamage fromSaveDto(BookDamageSaveDto bookDamageSaveDto) {
        if ( bookDamageSaveDto == null ) {
            return null;
        }
        return BookDamage.builder()
                .id(bookDamageSaveDto.getId())
                .imagePath(bookDamageSaveDto.getImagePath())
                .damageDescription(bookDamageSaveDto.getDamageDescription())
                .user(User.builder().id(bookDamageSaveDto.getUserId()).build())
                .order(Order.builder().id(bookDamageSaveDto.getOrderId()).build())
                .bookCopy(BookCopy.builder().id(bookDamageSaveDto.getBookCopyId()).build())
                .build();
    }

    static Set<BookDamage> bookDamageListDtoListToBookDamageSet(List<Long> bookDamages) {
        return bookDamages.stream()
                .map(bookDamage -> BookDamage.builder().id(bookDamage).build())
                .collect(Collectors.toSet());
    }
}