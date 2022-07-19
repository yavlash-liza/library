package by.library.yavlash.service;

import by.library.yavlash.dto.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();
    boolean add(GenreDto genreDto);
    boolean softDelete(Long genreId);
}