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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookCopyServiceImpl implements BookCopyService {
    private final BookCopyRepository bookCopyRepository;
    private final BookCopyMapper bookCopyMapper;

    @Override
    public BookCopyDto findById(Long bookCopyId) throws ServiceException {
        return bookCopyRepository.findById(bookCopyId).map(bookCopyMapper::toDto)
                .orElseThrow(() -> new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not found")));
    }

    @Override
    public List<BookCopyListDto> findAll() throws ServiceException {
        try {
            List<BookCopy> bookCopies = bookCopyRepository.findAll();
            return bookCopyMapper.toListDto(bookCopies);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " were not found "));
        }
    }

    @Override
    public boolean add(BookCopySaveDto bookCopySaveDto) throws ServiceException {
        try {
            BookCopy bookCopy = bookCopyMapper.fromSaveDto(bookCopySaveDto);
            bookCopyRepository.save(bookCopy);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " was not added "));
        }
    }

    @Override
    public boolean update(BookCopySaveDto bookCopySaveDto) throws ServiceException {
        try {
            BookCopy bookCopy = bookCopyMapper.fromSaveDto(bookCopySaveDto);
            bookCopyRepository.save(bookCopy);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " was not updated "));
        }
    }

    @Override
    public boolean delete(Long bookCopyId) throws ServiceException {
        Optional<BookCopy> optional = bookCopyRepository.findById(bookCopyId);
        if (optional.isPresent()) {
            BookCopy bookCopy = optional.get();
            bookCopy.setDeleted(true);
            bookCopyRepository.save(bookCopy);
            return true;
        } else {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " was not deleted "));
        }
    }
}