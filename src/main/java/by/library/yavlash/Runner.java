package by.library.yavlash;

import by.library.yavlash.config.ApplicationConfiguration;
import by.library.yavlash.dto.BookDamageDto;
import by.library.yavlash.dto.UserListDto;
import by.library.yavlash.entity.Author;
import by.library.yavlash.entity.Book;
import by.library.yavlash.entity.Genre;
import by.library.yavlash.entity.Order;
import by.library.yavlash.entity.Role;
import by.library.yavlash.entity.User;
import by.library.yavlash.repository.AuthorRepository;
import by.library.yavlash.repository.BookRepository;
import by.library.yavlash.repository.GenreRepository;
import by.library.yavlash.repository.OrderRepository;
import by.library.yavlash.repository.RoleRepository;
import by.library.yavlash.repository.UserRepository;
import by.library.yavlash.service.BookDamageService;
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
        List<UserListDto> allUsers = userService.findAllUsers();
        System.out.println("---------------------------------------------");
        System.out.println("All USERS: " + allUsers);
        System.out.println("---------------------------------------------");

        BookRepository bookRepository = context.getBean(BookRepository.class);
        Optional<Book> byId = bookRepository.findById(1L);
        System.out.println("---------------------------------------------");
        System.out.println("BOOKS: " + byId);
        System.out.println("---------------------------------------------");

        BookDamageService bookDamageService = context.getBean(BookDamageService.class);
        BookDamageDto bookDamageById = bookDamageService.findBookDamageById(2L);
        System.out.println("---------------------------------------------");
        System.out.println("BOOKS DAMAGES: " + bookDamageById);
        System.out.println("---------------------------------------------");

        GenreRepository genreRepository = context.getBean(GenreRepository.class);
        Optional<Genre> byIdGenre = genreRepository.findById(1L);
        System.out.println("--------genreRepository-------------------------------------");
        System.out.println("GENRES: " + byIdGenre);
        System.out.println("---------------------------------------------");

        OrderRepository orderRepository = context.getBean(OrderRepository.class);
        Optional<Order> byIdOrder = orderRepository.findById(2L);
        System.out.println("---------------------------------------------");
        System.out.println("ORDERS: " + byIdOrder);
        System.out.println("---------------------------------------------");

        RoleRepository roleRepository = context.getBean(RoleRepository.class);
        Optional<Role> byIdRole = roleRepository.findById(2L);
        System.out.println("---------------------------------------------");
        System.out.println("ROLES: " + byIdRole);
        System.out.println("---------------------------------------------");

        UserRepository userRepository = context.getBean(UserRepository.class);
        Optional<User> byIdUser = userRepository.findById(1L);
        System.out.println("---------------------------------------------");
        System.out.println("USERS: " + byIdUser);
        System.out.println("---------------------------------------------");
    }
}