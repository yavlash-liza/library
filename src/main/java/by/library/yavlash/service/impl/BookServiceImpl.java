package by.library.yavlash.service.impl;

import by.library.yavlash.dto.BookSaveDto;
import by.library.yavlash.entity.Author;
import by.library.yavlash.entity.Book;
import by.library.yavlash.entity.Genre;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.mapper.BookMapper;
import by.library.yavlash.repository.BookRepository;
import by.library.yavlash.service.BookService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public boolean addBook(BookSaveDto bookSaveDto) throws ServiceException {
        try {
            Book book = bookMapper.fromSaveDto(bookSaveDto);
            book.setGenres(findGenresByGenresId(bookSaveDto.getGenresId()));
            book.setAuthors(findAuthorsByAuthorsId(bookSaveDto.getAuthorsId()));
            bookRepository.add(book);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not added: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }

    private Set<Author> findAuthorsByAuthorsId(List<Long> authorsId) {
        return authorsId.stream()
                .map(authorId -> Author.builder().id(authorId).build())
                .collect(Collectors.toSet());
    }

    private Set<Genre> findGenresByGenresId(List<Long> genresId) {
        return genresId.stream()
                .map(genreId -> Genre.builder().id(genreId).build())
                .collect(Collectors.toSet());
    }

    @Override
    public boolean deleteBook(Long bookId) throws ServiceException {
        try {
            bookRepository.delete(bookId);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s was not deleted: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }
}