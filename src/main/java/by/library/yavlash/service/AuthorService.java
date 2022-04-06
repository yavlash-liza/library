package by.library.yavlash.service;

import by.library.yavlash.dto.AuthorListDto;
import by.library.yavlash.dto.AuthorSaveDto;
import by.library.yavlash.exception.ServiceException;

import java.util.List;

public interface AuthorService {
    void addAuthor(AuthorSaveDto authorSaveDto) throws ServiceException;
    List<AuthorListDto> findAllAuthors() throws ServiceException;
    void deleteAuthor(Long authorId) throws ServiceException;
}