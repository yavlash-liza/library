package by.library.yavlash;

import by.library.yavlash.entity.Genre;
import by.library.yavlash.repository.impl.*;
import by.library.yavlash.service.FlywayService;
import org.h2.jdbcx.JdbcConnectionPool;

import javax.sql.DataSource;

import static by.library.yavlash.service.Property.*;

public class Runner {
    public static void main(String[] args) throws Exception {
        DataSource dataSource = JdbcConnectionPool.create(H2_URL, H2_USER, H2_PASSWORD);
        FlywayService flywayService = new FlywayService(H2_URL, H2_USER, H2_PASSWORD, MIGRATION_LOCATION);
        flywayService.migrate();



//        BookRepository bookRepository = new BookRepositoryImpl(dataSource);
        GenreRepositoryImpl genreRepository = new GenreRepositoryImpl(dataSource);
//        UserRepository userRepository = new UserRepositoryImpl(dataSource);
//        AuthorRepositoryImpl authorRepository = new AuthorRepositoryImpl(dataSource);
//        OrderRepository orderRepository = new OrderRepositoryImpl(dataSource);
//        BookCopyRepository bookCopyRepository = new BookCopyRepositoryImpl(dataSource);
//        BookDamageRepository bookDamageRepository = new BookDamageRepositoryImpl(dataSource);
//        RoleRepository roleRepository = new RoleRepositoryImpl(dataSource);
//
//        System.out.println(bookDamageRepository.findAll());
//        bookRepository.delete(2L);
//        System.out.println(bookDamageRepository.findAll());
//        System.out.println(bookCopyRepository.findAll());
        System.out.println(genreRepository.findAll());
        genreRepository.add(Genre.builder().genreName("tale").build());
        System.out.println(genreRepository.findAll());
    }
}