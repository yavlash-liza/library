package by.library.yavlash.service;

import by.library.yavlash.dto.BookSaveDto;
import by.library.yavlash.exception.ServiceException;

public interface BookService {
    void addBook(BookSaveDto bookSaveDto) throws ServiceException;
    void deleteBook(Long bookId) throws ServiceException;
}