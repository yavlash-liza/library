package by.library.yavlash.service.impl;

import by.library.yavlash.dto.AuthorDto;
import by.library.yavlash.dto.AuthorListDto;
import by.library.yavlash.dto.AuthorSaveDto;
import by.library.yavlash.entity.Author;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.mapper.AuthorMapper;
import by.library.yavlash.repository.AuthorRepository;
import by.library.yavlash.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final static String AUTHOR_CACHE = "authors";
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    @Cacheable(value = AUTHOR_CACHE, key = "#authorId")
    @Transactional
    public AuthorDto findById(Long authorId) {
        return authorRepository.findById(authorId).map(authorMapper::toDto)
                .orElseThrow(() -> new ServiceException(String.format("Author was not found. id = %d", authorId)));
    }

    @Override
    @Cacheable(value = AUTHOR_CACHE)
    @Transactional
    public List<AuthorListDto> findAll() {
        try {
            List<Author> authors = authorRepository.findAll();
            return authorMapper.toListDto(authors);
        } catch (Exception e) {
            throw new ServiceException("Authors were not found.", e);
        }
    }

    @Override
    @CacheEvict(value = AUTHOR_CACHE, key = "#authorSaveDto.id")
    @Transactional
    public boolean add(AuthorSaveDto authorSaveDto) {
        try {
            Author author = authorMapper.fromSaveDto(authorSaveDto);
            authorRepository.save(author);
            return true;
        } catch (Exception e) {
            throw new ServiceException(String.format("Author was not saved. %s", authorSaveDto), e);
        }
    }

    @Override
    @CacheEvict(value = AUTHOR_CACHE, key = "#authorId")
    @Transactional
    public boolean softDelete(Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ServiceException(
                        String.format("Author was not softly deleted. Author was not found. id = %d", authorId)
                ));
        try {
            author.setDeleted(true);
            authorRepository.flush();
            return true;
        } catch (Exception e) {
            throw new ServiceException(String.format("Author was not softly deleted. id = %d", authorId), e);
        }
    }
}