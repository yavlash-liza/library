package by.library.yavlash.service.impl;

import by.library.yavlash.converter.BookCopyConverter;
import by.library.yavlash.dto.BookCopyDto;
import by.library.yavlash.dto.BookCopyListDto;
import by.library.yavlash.dto.BookCopySaveDto;
import by.library.yavlash.entity.Book;
import by.library.yavlash.entity.BookCopy;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.repository.BookCopyRepository;
import by.library.yavlash.service.BookCopyService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BookCopyServiceImpl implements BookCopyService {
    private final BookCopyRepository bookCopyRepository;

    @Override
    public BookCopyDto findBookCopyById(Long bookCopyId) throws ServiceException {
        try {
            BookCopy bookCopy = bookCopyRepository.findById(bookCopyId);
            return BookCopyConverter.toDto(bookCopy);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not found: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    @Override
    public List<BookCopyListDto> findAllBookCopies() throws ServiceException {
        try {
            List<BookCopy> bookCopies = bookCopyRepository.findAll();
            return BookCopyConverter.toBookCopyListDtos(bookCopies);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s were not found: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    @Override
    public boolean addBookCopy(BookCopySaveDto bookCopySaveDto) throws ServiceException {
        try {
            BookCopy bookCopy = BookCopyConverter.fromSaveDto(bookCopySaveDto);
            bookCopy.setBook(Book.builder().id(bookCopySaveDto.getBookId()).build());
            bookCopyRepository.add(bookCopy);
            return true;

        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not added: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    @Override
    public boolean updateBookCopy(BookCopyDto bookCopyDto) throws ServiceException {
        try {
            BookCopy bookCopy = BookCopyConverter.fromDto(bookCopyDto);
            bookCopyRepository.update(bookCopy);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not updated: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    @Override
    public boolean deleteBookCopy(Long bookCopyId) throws ServiceException {
        try {
            bookCopyRepository.delete(bookCopyId);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not deleted: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }
}