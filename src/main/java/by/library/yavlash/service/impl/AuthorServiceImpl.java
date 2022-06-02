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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    @Transactional
    public AuthorDto findById(Long authorId) throws ServiceException {
        return authorRepository.findById(authorId).map(authorMapper::toDto)
                .orElseThrow(() -> new ServiceException(String.format("Author was not found. id = %d", authorId)));
    }

    @Override
    @Transactional
    public List<AuthorListDto> findAll() throws ServiceException {
        try {
            List<Author> authors = authorRepository.findAll();
            return authorMapper.toListDto(authors);
        } catch (Exception e) {
            throw new ServiceException("Authors were not found.", e);
        }
    }

    @Override
    @Transactional
    public List<AuthorListDto> findListAuthors(int page, int size, boolean deleted) throws ServiceException {
        try {
            PageRequest pageReq = PageRequest.of(page, size);
            Page<AuthorListDto> authors = authorRepository.findAllByDeleted(deleted, pageReq)
                    .map(authorMapper::toListDto);
            return authors.getContent();
        } catch (Exception e) {
            throw new ServiceException("Authors were not found.", e);
        }
    }

    @Override
    @Transactional
    public List<AuthorListDto> findListAuthorsBySearch(int page, int size, boolean deleted, String search) throws ServiceException {
        try {
            PageRequest pageReq = PageRequest.of(page, size);
            Page<AuthorListDto> authors = authorRepository.findAllByDeletedAndLastName(deleted, search, pageReq)
                    .map(authorMapper::toListDto);
            return authors.getContent();
        } catch (Exception e) {
            throw new ServiceException("Authors were not found.", e);
        }
    }

    @Override
    @Transactional
    public boolean add(AuthorSaveDto authorSaveDto) throws ServiceException {
        try {
            Author author = authorMapper.fromSaveDto(authorSaveDto);
            authorRepository.save(author);
            return true;
        } catch (Exception e) {
            throw new ServiceException(String.format("Author was not saved. %s", authorSaveDto), e);
        }
    }

    @Override
    @Transactional
    public boolean softDelete(Long authorId) throws ServiceException {
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