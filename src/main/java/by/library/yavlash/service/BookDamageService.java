package by.library.yavlash.service;

import by.library.yavlash.dto.BookDamageDto;
import by.library.yavlash.exception.ServiceException;

public interface BookDamageService {
    BookDamageDto findBookDamageById(Long bookDamageId) throws ServiceException;
    boolean addBookDamage(BookDamageDto bookDamageDto) throws ServiceException;
    boolean deleteBookDamage(Long bookDamageId) throws ServiceException;
}