package by.library.yavlash.service.impl;

import by.library.yavlash.dto.BookDamageDto;
import by.library.yavlash.entity.BookDamage;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.mapper.BookDamageMapper;
import by.library.yavlash.repository.BookDamageRepository;
import by.library.yavlash.service.BookDamageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookDamageServiceImpl implements BookDamageService {
    private final BookDamageRepository bookDamageRepository;
    private final BookDamageMapper bookDamageMapper;

    @Override
    @Transactional
    public BookDamageDto findById(Long bookDamageId) throws ServiceException {
        return bookDamageRepository.findById(bookDamageId).map(bookDamageMapper::toDto)
                .orElseThrow(() -> new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not found")));
    }

    @Override
    @Transactional
    public boolean add(BookDamageDto bookDamageDto) throws ServiceException {
        try {
            BookDamage bookDamage = bookDamageMapper.fromSaveDto(bookDamageDto);
            bookDamageRepository.save(bookDamage);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " was not added "), exception);
        }
    }

    @Override
    @Transactional
    public boolean softDelete(Long bookDamageId) throws ServiceException {
        BookDamage bookDamage = bookDamageRepository.findById(bookDamageId)
                .orElseThrow(() -> new ServiceException(
                        String.format("%s:{%s}", getClass().getSimpleName(), " was not softly deleted and was not found.")
                ));
        try {
            bookDamage.setDeleted(true);
            bookDamageRepository.flush();
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s:{%s}", getClass().getSimpleName(), " was not softly deleted."), exception);
        }
    }
}