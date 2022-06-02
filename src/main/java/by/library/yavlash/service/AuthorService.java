package by.library.yavlash.service;

import by.library.yavlash.dto.AuthorDto;
import by.library.yavlash.dto.AuthorListDto;
import by.library.yavlash.dto.AuthorSaveDto;
import by.library.yavlash.exception.ServiceException;

import java.util.List;

public interface AuthorService {
    AuthorDto findById(Long authorId) throws ServiceException;
    List<AuthorListDto> findAll() throws ServiceException;
    List<AuthorListDto> findListAuthors(int page, int size, boolean deleted) throws ServiceException;
    List<AuthorListDto> findListAuthorsBySearch(int page, int size, boolean deleted, String search) throws ServiceException;
    boolean add(AuthorSaveDto authorSaveDto) throws ServiceException;
    boolean softDelete(Long authorId) throws ServiceException;
}