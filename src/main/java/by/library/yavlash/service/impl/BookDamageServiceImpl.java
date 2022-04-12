package by.library.yavlash.service.impl;

import by.library.yavlash.dto.BookDamageDto;
import by.library.yavlash.dto.BookDamageListDto;
import by.library.yavlash.dto.BookDamageSaveDto;
import by.library.yavlash.entity.BookCopy;
import by.library.yavlash.entity.BookDamage;
import by.library.yavlash.entity.Order;
import by.library.yavlash.entity.User;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.mapper.BookDamageMapper;
import by.library.yavlash.repository.BookDamageRepository;
import by.library.yavlash.service.BookDamageService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BookDamageServiceImpl implements BookDamageService {
    private final BookDamageRepository bookDamageRepository;
    private final BookDamageMapper bookDamageMapper;

    @Override
    public BookDamageDto findBookDamageById(Long bookDamageId) throws ServiceException {
        try {
            BookDamage bookDamage = bookDamageRepository.findById(bookDamageId);
            bookDamage.setBookCopy(BookCopy.builder().id(bookDamage.getBookCopy().getId()).build());
            bookDamage.setUser(User.builder().id(bookDamage.getUser().getId()).build());
            bookDamage.setOrder(Order.builder().id(bookDamage.getOrder().getId()).build());
            return bookDamageMapper.toDto(bookDamage);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not found: {%s}", getClass().getSimpleName(), exception.getMessage()));
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
    public boolean addBookDamage(BookDamageSaveDto bookDamageSaveDto) throws ServiceException {
        try {
            BookDamage bookDamage = bookDamageMapper.fromSaveDto(bookDamageSaveDto);
            bookDamage.setUser(User.builder().id(bookDamageSaveDto.getUserId()).build());
            bookDamage.setOrder(Order.builder().id(bookDamageSaveDto.getOrderId()).build());
            bookDamage.setBookCopy(BookCopy.builder().id(bookDamageSaveDto.getBookCopyId()).build());
            bookDamageRepository.add(bookDamage);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not added: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    @Override
    public boolean deleteBookDamage(Long bookDamageId) throws ServiceException {
        try {
            bookDamageRepository.delete(bookDamageId);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not deleted: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }
}