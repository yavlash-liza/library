package by.library.yavlash.service;

import by.library.yavlash.dto.BookDamageDto;
import by.library.yavlash.exception.ServiceException;

public interface BookDamageService {
    BookDamageDto findById(Long bookDamageId) throws ServiceException;
    boolean add(BookDamageDto bookDamageDto) throws ServiceException;
    boolean softDelete(Long bookDamageId) throws ServiceException;
}