package by.library.yavlash.service.impl;

import by.library.yavlash.dto.GenreDto;
import by.library.yavlash.entity.Genre;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.mapper.GenreMapper;
import by.library.yavlash.repository.GenreRepository;
import by.library.yavlash.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Override
    @Transactional
    public List<GenreDto> findAll() throws ServiceException {
        try {
            List<Genre> genres = genreRepository.findAll();
            return genreMapper.toDto(genres);
        } catch (Exception e) {
            throw new ServiceException("Genres were not found.", e);
        }
    }

    @Override
    @Transactional
    public boolean add(GenreDto genreDto) throws ServiceException {
        try {
            Genre genre = genreMapper.fromSaveDto(genreDto);
            genreRepository.save(genre);
            return true;
        } catch (Exception e) {
            throw new ServiceException(String.format("Genre was not saved. %s", genreDto), e);
        }
    }

    @Override
    @Transactional
    public boolean softDelete(Long genreId) throws ServiceException {
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new ServiceException(
                        String.format("Genre was not softly deleted. Genre was not found. id = %d", genreId)
                ));
        try {
            genre.setDeleted(true);
            genreRepository.flush();
            return true;
        } catch (Exception e) {
            throw new ServiceException(String.format("Genre was not softly deleted. id = %d", genreId), e);
        }
    }
}