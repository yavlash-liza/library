package by.library.yavlash.service;

import by.library.yavlash.dto.BookCopyDto;
import by.library.yavlash.dto.BookCopyListDto;
import by.library.yavlash.dto.BookCopySaveDto;
import by.library.yavlash.exception.ServiceException;

import java.util.List;

public interface BookCopyService {
    BookCopyDto findById(Long bookCopyId) throws ServiceException;
    List<BookCopyListDto> findAll() throws ServiceException;
    List<BookCopyListDto> findListBookCopies(int page, int size, boolean deleted) throws ServiceException;
    List<BookCopyListDto> findListBookCopiesByTitle(int page, int size, boolean deleted, String title) throws ServiceException;
    boolean add(BookCopySaveDto bookCopySaveDto) throws ServiceException;
    boolean update(BookCopySaveDto bookCopySaveDto) throws ServiceException;
    boolean softDelete(Long bookCopyId) throws ServiceException;

}