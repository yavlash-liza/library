package by.library.yavlash.service.impl;

import by.library.yavlash.dto.BookDamageDto;
import by.library.yavlash.entity.BookDamage;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.mapper.BookDamageMapper;
import by.library.yavlash.repository.BookDamageRepository;
import by.library.yavlash.service.BookDamageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookDamageServiceImpl implements BookDamageService {
    private final BookDamageRepository bookDamageRepository;
    private final BookDamageMapper bookDamageMapper;

    @Override
    public BookDamageDto findById(Long bookDamageId) throws ServiceException {
        return bookDamageRepository.findById(bookDamageId).map(bookDamageMapper::toDto)
                .orElseThrow(() -> new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not found")));
    }

    @Override
    public boolean add(BookDamageDto bookDamageDto) throws ServiceException {
        try {
            BookDamage bookDamage = bookDamageMapper.fromSaveDto(bookDamageDto);
            bookDamageRepository.save(bookDamage);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " was not added "));
        }
    }

    @Override
    public boolean delete(Long bookDamageId) throws ServiceException {
        Optional<BookDamage> optional = bookDamageRepository.findById(bookDamageId);
        if (optional.isPresent()) {
            BookDamage bookDamage = optional.get();
            bookDamage.setDeleted(true);
            bookDamageRepository.save(bookDamage);
            return true;
        } else {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " was not deleted "));
        }
    }
}