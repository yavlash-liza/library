package by.library.yavlash.service;

import by.library.yavlash.dto.BookCopyDto;
import by.library.yavlash.dto.BookCopyListDto;
import by.library.yavlash.dto.BookCopySaveDto;
import by.library.yavlash.exception.ServiceException;

import java.util.List;

public interface BookCopyService {
    boolean addBookCopy(BookCopySaveDto bookCopySaveDto) throws ServiceException;
    List<BookCopyListDto> findAllBookCopies() throws ServiceException;
    BookCopyDto findBookCopyById(Long bookCopyId) throws ServiceException;
    boolean deleteBookCopy(Long bookCopyId) throws ServiceException;
    boolean updateBookCopy(BookCopyDto bookCopyDto) throws ServiceException;
}