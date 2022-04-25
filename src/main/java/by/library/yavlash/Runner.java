package by.library.yavlash;

import by.library.yavlash.config.ApplicationConfiguration;
import by.library.yavlash.dto.UserListDto;
import by.library.yavlash.entity.Author;
import by.library.yavlash.entity.Book;
import by.library.yavlash.repository.AuthorRepository;
import by.library.yavlash.repository.BookRepository;
import by.library.yavlash.service.AuthorService;
import by.library.yavlash.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Optional;

public class Runner {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        AuthorRepository repository = context.getBean(AuthorRepository.class);
        Iterable<Author> authors = repository.findAll();
        System.out.println("---------------------------------------------");
        System.out.println("All AUTHORS: " + authors);
        System.out.println("---------------------------------------------");

        UserService userService = context.getBean(UserService.class);
        List<UserListDto> allUsers = userService.findAll();
        System.out.println("---------------------------------------------");
        System.out.println("All USERS: " + allUsers);
        System.out.println("---------------------------------------------");

        BookRepository bookRepository = context.getBean(BookRepository.class);
        Optional<Book> byId = bookRepository.findById(1L);
        System.out.println("---------------------------------------------");
        System.out.println("BOOKS: " + byId);
        System.out.println("---------------------------------------------");

        AuthorService authorService = context.getBean(AuthorService.class);
        AuthorRepository authorRepository = context.getBean(AuthorRepository.class);
        Optional<Author> author = authorRepository.findById(2L);
        Author author1 = null;
        if(author.isPresent()){
            author1 = author.get();
        }
        assert author1 != null;
        boolean delete = authorService.delete(author1.getId());
        Optional<Author> newAuthor = authorRepository.findById(2L);
        System.out.println("---------------------------------------------");
        System.out.println("AUTHOR 1: " + author);
        System.out.println("---------------------------------------------");
        System.out.println("1 - : " + author1);
        System.out.println("---------------------------------------------");
        System.out.println("delete - : " + delete);
        System.out.println("---------------------------------------------");
        System.out.println("new author - : " + newAuthor);
    }
}