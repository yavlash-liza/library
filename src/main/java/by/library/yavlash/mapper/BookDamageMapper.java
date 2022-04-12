package by.library.yavlash.mapper;

import by.library.yavlash.dto.BookDamageDto;
import by.library.yavlash.dto.BookDamageListDto;
import by.library.yavlash.dto.BookDamageSaveDto;
import by.library.yavlash.entity.BookDamage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookDamageMapper {
    @Mapping(target = "userId", source = "bookDamage.user.id")
    @Mapping(target = "orderId", source = "bookDamage.order.id")
    @Mapping(target = "bookCopyId", source = "bookDamage.bookCopy.id")
    BookDamageDto toDto(BookDamage bookDamage);
    List<BookDamageListDto> toDtos(List<BookDamage> bookDamages);
    BookDamage fromSaveDto(BookDamageSaveDto bookDamageSaveDto);
}