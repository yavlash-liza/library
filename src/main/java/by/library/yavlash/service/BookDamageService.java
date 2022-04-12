package by.library.yavlash.service;

import by.library.yavlash.dto.BookDamageDto;
import by.library.yavlash.dto.BookDamageListDto;
import by.library.yavlash.dto.BookDamageSaveDto;
import by.library.yavlash.exception.ServiceException;

import java.util.List;

public interface BookDamageService {
    boolean addBookDamage(BookDamageSaveDto bookDamageSaveDto) throws ServiceException;
    List<BookDamageListDto> findAllBookDamages() throws ServiceException;
    BookDamageDto findBookDamageById(Long bookDamageId) throws ServiceException;
    boolean deleteBookDamage(Long bookDamageId) throws ServiceException;
}