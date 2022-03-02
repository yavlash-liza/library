package by.library.yavlash.repository;

import by.library.yavlash.entity.*;
import by.library.yavlash.service.FlywayService;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import javax.sql.DataSource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static by.library.yavlash.property.Property.*;

public abstract class BaseRepositoryTest {
    private final DataSource dataSource;
    private final FlywayService flywayService;

    private List<User> users;
    private List<Role> roles;
    private List<Order> orders;
    private List<Genre> genres;
    private List<Book> books;
    private List<BookDamage> bookDamage;
    private List<BookCopy> bookCopies;
    private List<Author> authors;

    public BaseRepositoryTest() {
        dataSource = JdbcConnectionPool.create(H2_URL, H2_USER, H2_PASSWORD);
        flywayService = new FlywayService(H2_URL, H2_USER, H2_PASSWORD, MIGRATION_LOCATION);
        fillUsers();
        fillRoles();
        fillOrders();
        fillGenres();
        fillBooks();
        fillBookDamage();
        fillBookCopies();
        fillAuthors();
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    @BeforeEach
    public void migrate() {
        flywayService.migrate();
    }

    @AfterEach
    public void clean() {
        flywayService.clean();
    }

    private void fillUsers() {
        users = new ArrayList<>() {{
            add(User.builder().id(1L).firstName("vlad").lastName("kaliaha").passportNumber("1111").email("email1").address("address1").birthDate(LocalDate.of(2005, 6, 6)).build());
            add(User.builder().id(2L).firstName("andrei").lastName("yurueu").passportNumber("2222").email("email2").address("address2").birthDate(LocalDate.of(2001, 6, 6)).build());
            add(User.builder().id(3L).firstName("yaroslav").lastName("vasilevski").passportNumber("3333").email("email3").address("address3").birthDate(LocalDate.of(1998, 6, 6)).build());
            add(User.builder().id(4L).firstName("anastasiya").lastName("yurkova").passportNumber("4444").email("email4").address("address4").birthDate(LocalDate.of(1999, 6, 6)).build());
            add(User.builder().id(5L).firstName("alexander").lastName("kupriyanenko").passportNumber("5555").email("email5").address("address5").birthDate(LocalDate.of(1996, 6, 6)).build());
        }};
    }

    public User findUserForFindById() {
        return users.get(0);
    }

    public List<User> findUsersForFindAll() {
        return users;
    }

    private void fillRoles() {
        roles = new ArrayList<>() {{
            add(Role.builder().id(1L).roleName("admin").build());
            add(Role.builder().id(2L).roleName("user").build());
        }};
    }

    public Role findRoleForFindById() {
        return roles.get(0);
    }

    public List<Role> findRolesForFindAll() {
        return roles;
    }

    private void fillOrders() {
        orders = new ArrayList<>() {{
            add(Order.builder().id(1L).orderStatus("NEW").startDate(LocalDate.of(1998, 6, 6)).endDate(LocalDate.of(1998, 6, 6)).price(243).userId(1L).build());
            add(Order.builder().id(2L).orderStatus("NEW").startDate(LocalDate.of(1998, 6, 6)).endDate(LocalDate.of(1998, 6, 6)).price(21).userId(1L).build());
            add(Order.builder().id(3L).orderStatus("NEW").startDate(LocalDate.of(1998, 6, 6)).endDate(LocalDate.of(1998, 6, 6)).price(253).userId(1L).build());
            add(Order.builder().id(4L).orderStatus("NEW").startDate(LocalDate.of(1998, 6, 6)).endDate(LocalDate.of(1998, 6, 6)).price(273).userId(3L).build());
            add(Order.builder().id(5L).orderStatus("NEW").startDate(LocalDate.of(1998, 6, 6)).endDate(LocalDate.of(1998, 6, 6)).price(238).userId(4L).build());
        }};
    }

    public Order findOrderForFindById() {
        return orders.get(0);
    }

    public List<Order> findOrdersForFindAll() {
        return orders;
    }

    private void fillGenres() {
        genres = new ArrayList<>() {{
            add(Genre.builder().id(1L).genreName("NOVEL").build());
            add(Genre.builder().id(2L).genreName("ADVENTURE").build());
            add(Genre.builder().id(3L).genreName("COMEDY").build());
            add(Genre.builder().id(4L).genreName("CRIME").build());
            add(Genre.builder().id(5L).genreName("HORROR").build());
            add(Genre.builder().id(6L).genreName("SCIENCE FICTION").build());
            add(Genre.builder().id(7L).genreName("ROMANCE").build());
        }};
    }

    public Genre findGenreForFindById() {
        return genres.get(0);
    }

    public List<Genre> findGenresForFindAll() {
        return genres;
    }

    private void fillBooks() {
        books = new ArrayList<>() {{
            add(Book.builder().id(1L).title("War and peace").pagesNumber(1365).imagePath("image path").build());
            add(Book.builder().id(2L).title("The Master and Margarita").pagesNumber(638).imagePath("image path").build());
            add(Book.builder().id(3L).title("Idiot").pagesNumber(496).imagePath("image path").build());
            add(Book.builder().id(4L).title("The old man and the sea").pagesNumber(153).imagePath("image path").build());
            add(Book.builder().id(5L).title("Eugene Onegin").pagesNumber(462).imagePath("image path").build());
        }};
    }

    public Book findBookForFindById() {
        return books.get(0);
    }

    public List<Book> findBooksForFindAll() {
        return books;
    }

    private void fillBookDamage() {
        bookDamage = new ArrayList<>() {{
            add(BookDamage.builder().id(1L).imagePath("image path").damageDescription("damage1").userId(1L).orderId(1L).bookCopyId(3L).build());
            add(BookDamage.builder().id(2L).imagePath("image path").damageDescription("damage2").userId(1L).orderId(1L).bookCopyId(2L).build());
            add(BookDamage.builder().id(3L).imagePath("image path").damageDescription("damage3").userId(3L).orderId(4L).bookCopyId(1L).build());
            add(BookDamage.builder().id(4L).imagePath("image path").damageDescription("damage4").userId(4L).orderId(5L).bookCopyId(5L).build());
        }};
    }

    public BookDamage findBookDamageForFindById() {
        return bookDamage.get(0);
    }

    public List<BookDamage> findBookDamageForFindAll() {
        return bookDamage;
    }

    private void fillBookCopies() {
        bookCopies = new ArrayList<>() {{
            add(BookCopy.builder().id(1L).status("AVAILABLE").registrationDate(LocalDate.of(2019, 3, 1)).imagePath("image path").pricePerDay(150).bookId(1L).build());
            add(BookCopy.builder().id(2L).status("AVAILABLE").registrationDate(LocalDate.of(2020, 6, 1)).imagePath("image path").pricePerDay(210).bookId(2L).build());
            add(BookCopy.builder().id(3L).status("AVAILABLE").registrationDate(LocalDate.of(2021, 8, 4)).imagePath("image path").pricePerDay(225).bookId(2L).build());
            add(BookCopy.builder().id(4L).status("AVAILABLE").registrationDate(LocalDate.of(2017, 10, 10)).imagePath("image path").pricePerDay(128).bookId(5L).build());
            add(BookCopy.builder().id(5L).status("AVAILABLE").registrationDate(LocalDate.of(2020, 6, 2)).imagePath("image path").pricePerDay(311).bookId(3L).build());
        }};
    }

    public BookCopy findBookCopyForFindById() {
        return bookCopies.get(0);
    }

    public List<BookCopy> findBookCopiesForFindAll() {
        return bookCopies;
    }

    private void fillAuthors() {
        authors = new ArrayList<>() {{
            add(Author.builder().id(1L).firstName("Lev").lastName("Tolstoy").birthDate(LocalDate.of(1879, 4, 4)).imagePath("image path").build());
            add(Author.builder().id(2L).firstName("Ernest").lastName("Hemingway").birthDate(LocalDate.of(1903, 7, 7)).imagePath("image path").build());
            add(Author.builder().id(3L).firstName("Mikhail").lastName("Bulgakov").birthDate(LocalDate.of(1885, 10, 10)).imagePath("image path").build());
            add(Author.builder().id(4L).firstName("Alexander").lastName("Pushkin").birthDate(LocalDate.of(1852, 2, 2)).imagePath("image path").build());
            add(Author.builder().id(5L).firstName("Fedor").lastName("Dostoevsky").birthDate(LocalDate.of(1845, 1, 1)).imagePath("image path").build());
        }};
    }

    public Author findAuthorForFindById() {
        return authors.get(0);
    }

    public List<Author> findAuthorsForFindAll() {
        return authors;
    }

}