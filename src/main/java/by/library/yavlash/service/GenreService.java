package by.library.yavlash.service;

import by.library.yavlash.dto.GenreDto;
import by.library.yavlash.exception.ServiceException;

import java.util.List;

public interface GenreService {
    void addGenre(GenreDto genreDto) throws ServiceException;
    List<GenreDto> findAllGenres() throws ServiceException;
    void deleteGenre(Long genreId) throws ServiceException;
}