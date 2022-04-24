package by.library.yavlash.service.impl;

import by.library.yavlash.converter.BookConverter;
import by.library.yavlash.dto.BookSaveDto;
import by.library.yavlash.entity.Book;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.repository.BookRepository;
import by.library.yavlash.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public boolean addBook(BookSaveDto bookSaveDto) throws ServiceException {
        try {
            Book book = BookConverter.fromSaveDto(bookSaveDto);
            bookRepository.save(book);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " was not added "));
        }
    }

    @Override
    public boolean deleteBook(Long bookId) throws ServiceException {
        try {
            Optional<Book> book = bookRepository.findById(bookId);
            book.ifPresent(value -> value.setDeleted(true));
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " was not deleted "));
        }
    }
}