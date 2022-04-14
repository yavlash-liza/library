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

import java.util.List;

@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public AuthorDto findAuthorById(Long authorId) throws ServiceException {
        try {
            Author author = authorRepository.findById(authorId);
            return AuthorConverter.toDto(author);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not found: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    @Override
    public List<AuthorListDto> findAllAuthors() throws ServiceException {
        try {
            List<Author> authors = authorRepository.findAll();
            return AuthorConverter.toAuthorListDtos(authors);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s were not found: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    @Override
    public boolean addAuthor(AuthorSaveDto authorSaveDto) throws ServiceException {
        try {
            Author author = AuthorConverter.fromSaveDto(authorSaveDto);
            authorRepository.add(author);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not added: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    @Override
    public boolean deleteAuthor(Long authorId) throws ServiceException {
        try {
            authorRepository.delete(authorId);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not deleted: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }
}