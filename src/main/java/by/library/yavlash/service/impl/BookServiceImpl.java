package by.library.yavlash.service.impl;

import by.library.yavlash.dto.BookSaveDto;
import by.library.yavlash.entity.Book;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.mapper.BookMapper;
import by.library.yavlash.repository.BookRepository;
import by.library.yavlash.service.BookService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    public BookRepository bookRepository;
    public BookMapper bookMapper;

    @Override
    public void addBook(BookSaveDto bookSaveDto) throws ServiceException {
        try {
            Book book = bookMapper.fromSaveDto(bookSaveDto);
            bookRepository.add(book);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not added: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    @Override
    public void deleteBook(Long bookId) throws ServiceException {
        try {
            bookRepository.delete(bookId);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not deleted: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }
}