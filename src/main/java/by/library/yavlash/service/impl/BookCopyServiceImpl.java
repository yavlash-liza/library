package by.library.yavlash.service.impl;

import by.library.yavlash.dto.BookCopyDto;
import by.library.yavlash.dto.BookCopyListDto;
import by.library.yavlash.dto.BookCopySaveDto;
import by.library.yavlash.entity.BookCopy;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.mapper.BookCopyMapper;
import by.library.yavlash.repository.BookCopyRepository;
import by.library.yavlash.service.BookCopyService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BookCopyServiceImpl implements BookCopyService {
    public BookCopyRepository bookCopyRepository;
    public BookCopyMapper bookCopyMapper;

    @Override
    public void addBookCopy(BookCopySaveDto bookCopySaveDto) throws ServiceException {
        try {
            BookCopy bookCopy = bookCopyMapper.fromSaveDto(bookCopySaveDto);
            bookCopyRepository.add(bookCopy);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not added: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    @Override
    public List<BookCopyListDto> findAllBookCopies() throws ServiceException {
        try {
            List<BookCopy> bookCopies = bookCopyRepository.findAll();
            return bookCopyMapper.toBookCopyListDtos(bookCopies);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s were not found: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    @Override
    public BookCopyDto findBookCopyById(Long bookCopyId) throws ServiceException {
        try {
            BookCopy bookCopy = bookCopyRepository.findById(bookCopyId);
            return bookCopyMapper.toDto(bookCopy);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not found: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    @Override
    public void deleteBookCopy(Long bookCopyId) throws ServiceException {
        try {
            bookCopyRepository.delete(bookCopyId);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not deleted: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    @Override
    public void updateBookCopy(BookCopyDto bookCopyDto) throws ServiceException {
        try {
            BookCopy bookCopy = bookCopyMapper.fromDto(bookCopyDto);
            bookCopyRepository.update(bookCopy);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not updated: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }
}