package by.library.yavlash.service.impl;

import by.library.yavlash.dto.BookSaveDto;
import by.library.yavlash.entity.Book;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.mapper.BookMapper;
import by.library.yavlash.repository.BookRepository;
import by.library.yavlash.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    @Transactional
    public boolean add(BookSaveDto bookSaveDto) throws ServiceException {
        try {
            Book book = bookMapper.fromSaveDto(bookSaveDto);
            bookRepository.save(book);
            return true;
        } catch (Exception e) {
            throw new ServiceException(String.format("Book was not saved. %s", bookSaveDto), e);
        }
    }

    @Override
    @Transactional
    public boolean softDelete(Long bookId) throws ServiceException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ServiceException(
                        String.format("Book was not softly deleted. Book was not found. id = %d", bookId)
                ));
        try {
            book.setDeleted(true);
            bookRepository.flush();
            return true;
        } catch (Exception e) {
            throw new ServiceException(String.format("Book was not softly deleted. id = %d", bookId), e);
        }
    }
}