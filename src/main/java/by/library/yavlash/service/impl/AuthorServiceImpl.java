package by.library.yavlash.service.impl;

import by.library.yavlash.converter.AuthorConverter;
import by.library.yavlash.dto.AuthorDto;
import by.library.yavlash.dto.AuthorListDto;
import by.library.yavlash.dto.AuthorSaveDto;
import by.library.yavlash.entity.Author;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.repository.AuthorRepository;
import by.library.yavlash.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public AuthorDto findAuthorById(Long authorId) throws ServiceException {
        Optional<Author> author = authorRepository.findById(authorId);
        return author.map(AuthorConverter::toDto)
                .orElseThrow(() -> new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not found")));
    }

    @Override
    public List<AuthorListDto> findAllAuthors() throws ServiceException {
        try {
            List<Author> authors = authorRepository.findAll();
            return AuthorConverter.toAuthorListDtos(authors);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " were not found "));
        }
    }

    @Override
    public boolean addAuthor(AuthorSaveDto authorSaveDto) throws ServiceException {
        try {
            Author author = AuthorConverter.fromSaveDto(authorSaveDto);
            authorRepository.save(author);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " was not added "));
        }
    }

    @Override
    public boolean deleteAuthor(Long authorId) throws ServiceException {
        try {
            Optional<Author> author = authorRepository.findById(authorId);
            author.ifPresent(value -> value.setDeleted(true));
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " was not deleted "));
        }
    }
}