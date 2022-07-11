package by.library.yavlash.service;

import by.library.yavlash.dto.BookDamageDto;

public interface BookDamageService {
    BookDamageDto findById(Long bookDamageId);
    boolean add(BookDamageDto bookDamageDto);
    boolean softDelete(Long bookDamageId);
}