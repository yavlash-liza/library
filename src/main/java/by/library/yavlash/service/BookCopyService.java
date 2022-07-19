package by.library.yavlash.service;

import by.library.yavlash.dto.BookCopyDto;
import by.library.yavlash.dto.BookCopyListDto;
import by.library.yavlash.dto.BookCopySaveDto;

import java.util.List;

public interface BookCopyService {
    BookCopyDto findById(Long bookCopyId);
    List<BookCopyListDto> findAll();
    List<BookCopyListDto> findListBookCopies(int page, int size, boolean deleted);
    List<BookCopyListDto> findListBookCopiesByTitle(int page, int size, boolean deleted, String title);
    boolean add(BookCopySaveDto bookCopySaveDto);
    boolean update(BookCopySaveDto bookCopySaveDto);
    boolean softDelete(Long bookCopyId);
}