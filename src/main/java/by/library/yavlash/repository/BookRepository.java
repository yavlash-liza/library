package by.library.yavlash.repository;

import by.library.yavlash.entity.Author;
import by.library.yavlash.entity.Book;
import by.library.yavlash.entity.Genre;

import java.util.Set;

public interface BookRepository extends BaseRepository<Book> {
    Set<Author> findAuthorsByAuthorsId(Set<Long> authorsId);
    Set<Genre> findGenresByGenresId(Set<Long> genresId);
}