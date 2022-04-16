package by.library.yavlash.service.impl;

import by.library.yavlash.converter.GenreConverter;
import by.library.yavlash.dto.GenreDto;
import by.library.yavlash.entity.Genre;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.repository.GenreRepository;
import by.library.yavlash.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<GenreDto> findAllGenres() throws ServiceException {
        try {
            List<Genre> genres = genreRepository.findAll();
            return GenreConverter.toListDto(genres);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s were not found: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    @Override
    public boolean addGenre(GenreDto genreDto) throws ServiceException {
        try {
            Genre genre = GenreConverter.fromSaveDto(genreDto);
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