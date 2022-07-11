package by.library.yavlash.service.impl;

import by.library.yavlash.dto.BookDamageDto;
import by.library.yavlash.entity.BookDamage;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.mapper.BookDamageMapper;
import by.library.yavlash.repository.BookDamageRepository;
import by.library.yavlash.service.BookDamageService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookDamageServiceImpl implements BookDamageService {
    private final static String DAMAGE_CACHE = "damages";
    private final BookDamageRepository bookDamageRepository;
    private final BookDamageMapper bookDamageMapper;

    @Override
    @Cacheable(value = DAMAGE_CACHE, key = "#bookDamageId")
    @Transactional
    public BookDamageDto findById(Long bookDamageId) {
        return bookDamageRepository.findById(bookDamageId).map(bookDamageMapper::toDto)
                .orElseThrow(() -> new ServiceException(String.format("BookDamage was not found.  %s", bookDamageId)));
    }

    @Override
    @CacheEvict(value = DAMAGE_CACHE, key = "#bookDamageDto.id")
    @Transactional
    public boolean add(BookDamageDto bookDamageDto) {
        try {
            BookDamage bookDamage = bookDamageMapper.fromSaveDto(bookDamageDto);
            bookDamageRepository.save(bookDamage);
            return true;
        } catch (Exception e) {
            throw new ServiceException(String.format("BookDamage was not saved. %s", bookDamageDto), e);
        }
    }

    @Override
    @CacheEvict(value = DAMAGE_CACHE, key = "#bookDamageId")
    @Transactional
    public boolean softDelete(Long bookDamageId) {
        BookDamage bookDamage = bookDamageRepository.findById(bookDamageId)
                .orElseThrow(() -> new ServiceException(
                        String.format("BookDamage was not softly deleted. BookDamage was not found. id = %d", bookDamageId)
                ));
        try {
            bookDamage.setDeleted(true);
            bookDamageRepository.flush();
            return true;
        } catch (Exception e) {
            throw new ServiceException(String.format("BookDamage was not softly deleted. id = %d", bookDamageId), e);
        }
    }
}