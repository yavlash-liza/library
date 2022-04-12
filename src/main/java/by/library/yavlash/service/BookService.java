package by.library.yavlash.service;

import by.library.yavlash.dto.BookSaveDto;
import by.library.yavlash.exception.ServiceException;

public interface BookService {
    boolean addBook(BookSaveDto bookSaveDto) throws ServiceException;
    boolean deleteBook(Long bookId) throws ServiceException;
}