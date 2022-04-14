package by.library.yavlash.service;

import by.library.yavlash.dto.AuthorDto;
import by.library.yavlash.dto.AuthorListDto;
import by.library.yavlash.dto.AuthorSaveDto;
import by.library.yavlash.exception.ServiceException;

import java.util.List;

public interface AuthorService {
    AuthorDto findAuthorById(Long authorId) throws ServiceException;
    List<AuthorListDto> findAllAuthors() throws ServiceException;
    boolean addAuthor(AuthorSaveDto authorSaveDto) throws ServiceException;
    boolean deleteAuthor(Long authorId) throws ServiceException;
}