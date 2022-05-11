package by.library.yavlash.service;

import by.library.yavlash.dto.BookSaveDto;
import by.library.yavlash.exception.ServiceException;

public interface BookService {
    boolean add(BookSaveDto bookSaveDto) throws ServiceException;
    boolean softDelete(Long bookId) throws ServiceException;
}