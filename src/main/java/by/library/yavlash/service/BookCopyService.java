package by.library.yavlash.service;

import by.library.yavlash.dto.BookCopyDto;
import by.library.yavlash.dto.BookCopyListDto;
import by.library.yavlash.dto.BookCopySaveDto;

import java.util.List;

public interface BookCopyService {
    BookCopyDto findById(Long bookCopyId);
    List<BookCopyListDto> findAll();
    boolean add(BookCopySaveDto bookCopySaveDto);
    boolean update(BookCopySaveDto bookCopySaveDto);
    boolean softDelete(Long bookCopyId);
}