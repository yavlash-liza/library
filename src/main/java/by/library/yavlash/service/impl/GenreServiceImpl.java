package by.library.yavlash.service.impl;

import by.library.yavlash.dto.GenreDto;
import by.library.yavlash.entity.Genre;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.mapper.GenreMapper;
import by.library.yavlash.repository.GenreRepository;
import by.library.yavlash.service.GenreService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Override
    public List<GenreDto> findAllGenres() throws ServiceException {
        try {
            List<Genre> genres = genreRepository.findAll();
            return genreMapper.toListDto(genres);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s were not found: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    @Override
    public boolean addGenre(GenreDto genreDto) throws ServiceException {
        try {
            Genre genre = genreMapper.fromSaveDto(genreDto);
            genreRepository.add(genre);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not added: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    @Override
    public boolean deleteGenre(Long genreId) throws ServiceException {
        try {
            genreRepository.delete(genreId);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not deleted: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }
}