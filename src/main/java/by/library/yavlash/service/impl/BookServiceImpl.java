package by.library.yavlash.service.impl;

import by.library.yavlash.converter.BookConverter;
import by.library.yavlash.dto.BookSaveDto;
import by.library.yavlash.entity.Book;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.repository.BookRepository;
import by.library.yavlash.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public boolean addBook(BookSaveDto bookSaveDto) throws ServiceException {
        try {
            Book book = BookConverter.fromSaveDto(bookSaveDto);
            bookRepository.add(book);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not added: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    @Override
    public boolean deleteBook(Long bookId) throws ServiceException {
        try {
            bookRepository.delete(bookId);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not deleted: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }
}