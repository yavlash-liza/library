package by.library.yavlash.controller;

import by.library.yavlash.dto.GenreDto;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    public List<GenreDto> findAll() throws ServiceException {
        return genreService.findAll();
    }

    @PostMapping
    public boolean add(@RequestBody GenreDto genreDto) throws ServiceException {
        return genreService.add(genreDto);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) throws ServiceException {
        return genreService.delete(id);
    }
}