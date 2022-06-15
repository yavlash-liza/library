package by.library.yavlash.service.impl;

import by.library.yavlash.dto.BookCopyDto;
import by.library.yavlash.dto.BookCopyListDto;
import by.library.yavlash.dto.BookCopySaveDto;
import by.library.yavlash.entity.Book;
import by.library.yavlash.entity.BookCopy;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.mapper.BookCopyMapper;
import by.library.yavlash.repository.BookCopyRepository;
import by.library.yavlash.service.BookCopyService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookCopyServiceImpl implements BookCopyService {
    private final BookCopyRepository bookCopyRepository;
    private final BookCopyMapper bookCopyMapper;

    @Override
    @Cacheable("bookcopies")
    @Transactional
    public BookCopyDto findById(Long bookCopyId) throws ServiceException {
        return bookCopyRepository.findById(bookCopyId).map(bookCopyMapper::toDto)
                .orElseThrow(() -> new ServiceException(String.format("BookCopy was not found. id = %d", bookCopyId)));
    }

    @Override
    @Cacheable("bookcopies")
    @Transactional
    public List<BookCopyListDto> findAll() throws ServiceException {
        try {
            List<BookCopy> bookCopies = bookCopyRepository.findAll();
            return bookCopyMapper.toListDto(bookCopies);
        } catch (Exception e) {
            throw new ServiceException("BookCopies were not found.", e);
        }
    }

    @Override
    @Cacheable("bookcopies")
    @Transactional
    public boolean add(BookCopySaveDto bookCopySaveDto) throws ServiceException {
        try {
            BookCopy bookCopy = bookCopyMapper.fromSaveDto(bookCopySaveDto);
            bookCopyRepository.save(bookCopy);
            return true;
        } catch (Exception e) {
            throw new ServiceException(String.format("BookCopy was not saved. %s", bookCopySaveDto), e);
        }
    }

    @Override
    @Cacheable("bookcopies")
    @Transactional
    public boolean update(BookCopySaveDto bookCopySaveDto) throws ServiceException {
        BookCopy bookCopy = bookCopyRepository.findById(bookCopySaveDto.getId())
                .orElseThrow(() -> new ServiceException(
                        String.format("BookCopy was not updated. BookCopy was not found. id = %d", bookCopySaveDto.getId())
                ));
        try {
            settingUpdateFields(bookCopy, bookCopySaveDto);
            bookCopyRepository.flush();
        } catch (Exception e) {
            throw new ServiceException(String.format("BookCopy was not updated. %s", bookCopySaveDto), e);
        }
        return true;
    }

    private void settingUpdateFields(BookCopy beforeUpdate, BookCopySaveDto saveDto) {
        if (saveDto.getImagePath() != null) {
            beforeUpdate.setImagePath(saveDto.getImagePath());
        }
        if (saveDto.getStatus() != null) {
            beforeUpdate.setStatus(saveDto.getStatus());
        }
        if (saveDto.getRegistrationDate() != null) {
            beforeUpdate.setRegistrationDate(saveDto.getRegistrationDate());
        }
        if (saveDto.getPricePerDay() != -1) {
            beforeUpdate.setPricePerDay(saveDto.getPricePerDay());
        }
        if (saveDto.getBookId() != null) {
            beforeUpdate.setBook(Book.builder().id(saveDto.getBookId()).build());
        }
    }

    @Override
    @CacheEvict("bookcopies")
    @Transactional
    public boolean softDelete(Long bookCopyId) throws ServiceException {
        BookCopy bookCopy = bookCopyRepository.findById(bookCopyId)
                .orElseThrow(() -> new ServiceException(
                        String.format("BookCopy was not softly deleted. BookCopy was not found. id = %d", bookCopyId)
                ));
        try {
            bookCopy.setDeleted(true);
            bookCopyRepository.flush();
            return true;
        } catch (Exception e) {
            throw new ServiceException(String.format("BookCopy was not softly deleted. id = %d", bookCopyId), e);
        }
    }
}