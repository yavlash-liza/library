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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookCopyServiceImpl implements BookCopyService {
    private final BookCopyRepository bookCopyRepository;

    @Override
    public BookCopyDto findBookCopyById(Long bookCopyId) throws ServiceException {
        Optional<BookCopy> bookCopy = bookCopyRepository.findById(bookCopyId);
        return bookCopy.map(BookCopyConverter::toDto)
                .orElseThrow(() -> new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not found")));
    }

    @Override
    public List<BookCopyListDto> findAllBookCopies() throws ServiceException {
        try {
            List<BookCopy> bookCopies = bookCopyRepository.findAll();
            return BookCopyConverter.toBookCopyListDtos(bookCopies);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " were not found "));
        }
    }

    @Override
    public boolean addBookCopy(BookCopySaveDto bookCopySaveDto) throws ServiceException {
        try {
            BookCopy bookCopy = BookCopyConverter.fromSaveDto(bookCopySaveDto);
            bookCopy.setBook(Book.builder().id(bookCopySaveDto.getBookId()).build());
            bookCopyRepository.save(bookCopy);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " was not added "));
        }
    }

    @Override
    public boolean updateBookCopy(BookCopyDto bookCopyDto) throws ServiceException {
        try {
            BookCopy bookCopy = BookCopyConverter.fromDto(bookCopyDto);
            bookCopyRepository.save(bookCopy);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " was not updated "));
        }
    }

    @Override
    public boolean deleteBookCopy(Long bookCopyId) throws ServiceException {
        try {
            Optional<BookCopy> bookCopy = bookCopyRepository.findById(bookCopyId);
            bookCopy.ifPresent(value -> value.setDeleted(true));
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " was not deleted "));
        }
    }
}