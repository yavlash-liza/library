package by.library.yavlash.service.impl;

import by.library.yavlash.dto.AuthorListDto;
import by.library.yavlash.dto.AuthorSaveDto;
import by.library.yavlash.entity.Author;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.mapper.AuthorMapper;
import by.library.yavlash.repository.AuthorRepository;
import by.library.yavlash.service.AuthorService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    public AuthorRepository authorRepository;
    public AuthorMapper authorMapper;

    @Override
    public void addAuthor(AuthorSaveDto authorSaveDto) throws ServiceException {
        try {
            Author author = authorMapper.fromSaveDto(authorSaveDto);
            authorRepository.add(author);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not added: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    @Override
    public List<AuthorListDto> findAllAuthors() throws ServiceException {
        try {
            List<Author> authors = authorRepository.findAll();
            return authorMapper.toAuthorListDtos(authors);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s were not found: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    @Override
    public void deleteAuthor(Long authorId) throws ServiceException {
        try {
            authorRepository.delete(authorId);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not deleted: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }
}