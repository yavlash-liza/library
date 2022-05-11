package by.library.yavlash.controller;

import by.library.yavlash.repository.AuthorRepository;
import by.library.yavlash.repository.BookCopyRepository;
import by.library.yavlash.repository.BookDamageRepository;
import by.library.yavlash.repository.BookRepository;
import by.library.yavlash.repository.GenreRepository;
import by.library.yavlash.repository.OrderRepository;
import by.library.yavlash.repository.RoleRepository;
import by.library.yavlash.repository.UserRepository;
import by.library.yavlash.service.AuthorService;
import by.library.yavlash.service.BookCopyService;
import by.library.yavlash.service.BookDamageService;
import by.library.yavlash.service.BookService;
import by.library.yavlash.service.GenreService;
import by.library.yavlash.service.OrderService;
import by.library.yavlash.service.RoleService;
import by.library.yavlash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@MockBean(classes = {
        UserRepository.class,
        BookRepository.class,
        AuthorRepository.class,
        BookCopyRepository.class,
        BookDamageRepository.class,
        RoleRepository.class,
        OrderRepository.class,
        GenreRepository.class
} )
@AutoConfigureMockMvc
public class BaseControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected AuthorService authorService;

    @MockBean
    protected BookCopyService bookCopyService;

    @MockBean
    protected BookService bookService;

    @MockBean
    protected BookDamageService bookDamageService;

    @MockBean
    protected GenreService genreService;

    @MockBean
    protected OrderService orderService;

    @MockBean
    protected RoleService roleService;

    @MockBean
    protected UserService userService;
}