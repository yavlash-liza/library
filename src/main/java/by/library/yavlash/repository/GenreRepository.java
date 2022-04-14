package by.library.yavlash.repository;

import by.library.yavlash.entity.Book;
import by.library.yavlash.entity.Genre;
import by.library.yavlash.exception.RepositoryException;

import java.util.Set;

public interface GenreRepository extends BaseRepository<Genre> {
    Set<Book> findBooksByGenreId(Long genreId) throws RepositoryException;
}