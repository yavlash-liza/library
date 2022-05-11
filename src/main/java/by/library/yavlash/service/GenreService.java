package by.library.yavlash.service;

import by.library.yavlash.dto.GenreDto;
import by.library.yavlash.exception.ServiceException;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll() throws ServiceException;
    boolean add(GenreDto genreDto) throws ServiceException;
    boolean softDelete(Long genreId) throws ServiceException;
}