package by.library.yavlash.service;

import by.library.yavlash.dto.AuthorDto;
import by.library.yavlash.dto.AuthorListDto;
import by.library.yavlash.dto.AuthorSaveDto;

import java.util.List;

public interface AuthorService {
    AuthorDto findById(Long authorId);
    List<AuthorListDto> findAll();
    List<AuthorListDto> findListAuthors(int page, int size, boolean deleted);
    List<AuthorListDto> findListAuthorsBySearch(int page, int size, boolean deleted, String search);
    boolean add(AuthorSaveDto authorSaveDto);
    boolean softDelete(Long authorId);
}