package by.library.yavlash.service.impl;

import by.library.yavlash.dto.BookDamageDto;
import by.library.yavlash.dto.BookDamageListDto;
import by.library.yavlash.dto.BookDamageSaveDto;
import by.library.yavlash.entity.BookDamage;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.mapper.BookDamageMapper;
import by.library.yavlash.repository.BookDamageRepository;
import by.library.yavlash.service.BookDamageService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BookDamageServiceImpl implements BookDamageService {
    public BookDamageRepository bookDamageRepository;
    public BookDamageMapper bookDamageMapper;

    @Override
    public void addBookDamage(BookDamageSaveDto bookDamageSaveDto) throws ServiceException {
        try {
            BookDamage bookDamage = bookDamageMapper.fromSaveDto(bookDamageSaveDto);
            bookDamageRepository.add(bookDamage);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not added: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    @Override
    public List<BookDamageListDto> findAllBookDamages() throws ServiceException {
        try {
            List<BookDamage> bookDamages = bookDamageRepository.findAll();
            return bookDamageMapper.toDtos(bookDamages);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s were not found: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    @Override
    public BookDamageDto findBookDamageById(Long bookDamageId) throws ServiceException {
        try {
            BookDamage bookDamage = bookDamageRepository.findById(bookDamageId);
            return bookDamageMapper.toDto(bookDamage);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not found: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    @Override
    public void deleteBookDamage(Long bookDamageId) throws ServiceException {
        try {
            bookDamageRepository.delete(bookDamageId);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not deleted: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }
}