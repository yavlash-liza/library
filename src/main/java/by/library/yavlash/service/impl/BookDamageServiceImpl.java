package by.library.yavlash.service.impl;

import by.library.yavlash.converter.BookDamageConverter;
import by.library.yavlash.dto.BookDamageDto;
import by.library.yavlash.entity.BookCopy;
import by.library.yavlash.entity.BookDamage;
import by.library.yavlash.entity.Order;
import by.library.yavlash.entity.User;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.repository.BookDamageRepository;
import by.library.yavlash.service.BookDamageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookDamageServiceImpl implements BookDamageService {
    private final BookDamageRepository bookDamageRepository;

    @Override
    public BookDamageDto findBookDamageById(Long bookDamageId) throws ServiceException {
        try {
            BookDamage bookDamage = bookDamageRepository.findById(bookDamageId);
            return BookDamageConverter.toDto(bookDamage);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not found: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    @Override
    public boolean addBookDamage(BookDamageDto bookDamageDto) throws ServiceException {
        try {
            BookDamage bookDamage = BookDamageConverter.fromSaveDto(bookDamageDto);
            bookDamage.setUser(User.builder().id(bookDamageDto.getUserId()).build());
            bookDamage.setOrder(Order.builder().id(bookDamageDto.getOrderId()).build());
            bookDamage.setBookCopy(BookCopy.builder().id(bookDamageDto.getBookCopyId()).build());
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