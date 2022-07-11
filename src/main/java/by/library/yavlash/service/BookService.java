package by.library.yavlash.service;

import by.library.yavlash.dto.BookSaveDto;

public interface BookService {
    boolean add(BookSaveDto bookSaveDto);
    boolean softDelete(Long bookId);
}