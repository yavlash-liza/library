package by.library.yavlash.mapper;

import by.library.yavlash.dto.BookDamageDto;
import by.library.yavlash.dto.BookDamageListDto;
import by.library.yavlash.dto.BookDamageSaveDto;
import by.library.yavlash.entity.BookDamage;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookDamageMapper {
    BookDamageDto toDto(BookDamage order);
    List<BookDamageListDto> toDtos(List<BookDamage> order);
    BookDamage fromSaveDto(BookDamageSaveDto orderSaveDto);
}