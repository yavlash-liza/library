package by.library.yavlash.service;

import by.library.yavlash.dto.AuthorDto;
import by.library.yavlash.dto.AuthorListDto;
import by.library.yavlash.dto.AuthorSaveDto;

import java.util.List;

public interface AuthorService {
    AuthorDto findById(Long authorId);
    List<AuthorListDto> findAll();
    boolean add(AuthorSaveDto authorSaveDto);
    boolean softDelete(Long authorId);
}