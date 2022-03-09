package by.library.yavlash.repository;

import by.library.yavlash.exception.RepositoryException;

import java.util.List;

public interface BaseRepository<E> {
    E findById(Long id) throws RepositoryException;
    List<E> findAll() throws RepositoryException;
    boolean add(E element) throws RepositoryException;
    boolean update(E element) throws RepositoryException;
    boolean delete(Long id) throws RepositoryException;
}