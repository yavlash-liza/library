package by.library.yavlash.mapper;

import by.library.yavlash.dto.BookDamageDto;
import by.library.yavlash.entity.BookDamage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookDamageMapper {
    @Mapping(target = "userId", source = "bookDamage.user.id")
    @Mapping(target = "orderId", source = "bookDamage.order.id")
    @Mapping(target = "bookCopyId", source = "bookDamage.bookCopy.id")
    BookDamageDto toDto(BookDamage bookDamage);

    @Mapping(target = "user.id", source = "bookDamageDto.userId")
    @Mapping(target = "order.id", source = "bookDamageDto.orderId")
    @Mapping(target = "bookCopy.id", source = "bookDamageDto.bookCopyId")
    BookDamage fromSaveDto(BookDamageDto bookDamageDto);
}