package by.library.yavlash.service;

import by.library.yavlash.dto.BookCopyDto;
import by.library.yavlash.dto.BookCopyListDto;
import by.library.yavlash.dto.BookCopySaveDto;
import by.library.yavlash.exception.ServiceException;

import java.util.List;

public interface BookCopyService {
    void addBookCopy(BookCopySaveDto bookCopySaveDto) throws ServiceException;
    List<BookCopyListDto> findAllBookCopies() throws ServiceException;
    BookCopyDto findBookCopyById(Long bookCopyId) throws ServiceException;
    void deleteBookCopy(Long bookCopyId) throws ServiceException;
    void updateBookCopy(BookCopyDto bookCopyDto) throws ServiceException;
}