package by.library.yavlash.service;

import by.library.yavlash.dto.BookCopyDto;
import by.library.yavlash.dto.BookCopyListDto;
import by.library.yavlash.dto.BookCopySaveDto;
import by.library.yavlash.exception.ServiceException;

import java.util.List;

public interface BookCopyService {
    BookCopyDto findBookCopyById(Long bookCopyId) throws ServiceException;
    List<BookCopyListDto> findAllBookCopies() throws ServiceException;
    boolean addBookCopy(BookCopySaveDto bookCopySaveDto) throws ServiceException;
    boolean updateBookCopy(BookCopyDto bookCopyDto) throws ServiceException;
    boolean deleteBookCopy(Long bookCopyId) throws ServiceException;
}