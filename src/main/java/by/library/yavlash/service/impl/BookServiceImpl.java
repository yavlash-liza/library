package by.library.yavlash.service.impl;

import by.library.yavlash.dto.BookSaveDto;
import by.library.yavlash.entity.Book;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.mapper.BookMapper;
import by.library.yavlash.repository.BookRepository;
import by.library.yavlash.service.BookService;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;

@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public boolean addBook(BookSaveDto bookSaveDto) throws ServiceException {
        try {
            Book book = bookMapper.fromSaveDto(bookSaveDto);
            book.setGenres(bookRepository.findGenresByGenresId(new HashSet<>(bookSaveDto.getGenresId())));
            book.setAuthors(bookRepository.findAuthorsByAuthorsId(new HashSet<>(bookSaveDto.getAuthorsId())));
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