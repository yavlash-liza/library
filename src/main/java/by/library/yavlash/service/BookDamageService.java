package by.library.yavlash.service;

import by.library.yavlash.dto.BookDamageDto;
import by.library.yavlash.dto.BookDamageListDto;
import by.library.yavlash.dto.BookDamageSaveDto;
import by.library.yavlash.exception.ServiceException;

import java.util.List;

public interface BookDamageService {
    void addBookDamage(BookDamageSaveDto bookDamageSaveDto) throws ServiceException;
    List<BookDamageListDto> findAllBookDamages() throws ServiceException;
    BookDamageDto findBookDamageById(Long bookDamageId) throws ServiceException;
    void deleteBookDamage(Long bookDamageId) throws ServiceException;
}