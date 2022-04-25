package by.library.yavlash.service.impl;

import by.library.yavlash.converter.GenreConverter;
import by.library.yavlash.dto.GenreDto;
import by.library.yavlash.entity.Genre;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.repository.GenreRepository;
import by.library.yavlash.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public List<GenreDto> findAll() throws ServiceException {
        try {
            List<Genre> genres = genreRepository.findAll();
            return GenreConverter.toListDto(genres);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " were not found "));
        }
    }

    @Override
    public boolean add(GenreDto genreDto) throws ServiceException {
        try {
            Genre genre = GenreConverter.fromSaveDto(genreDto);
            genreRepository.save(genre);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " was not added "));
        }
    }

    @Override
    public boolean delete(Long genreId) throws ServiceException {
        Optional<Genre> optional = genreRepository.findById(genreId);
        if (optional.isPresent()) {
            Genre genre = optional.get();
            genre.setDeleted(true);
            genreRepository.save(genre);
            return true;
        } else {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " was not deleted "));
        }
    }
}