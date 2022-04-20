package by.library.yavlash;

import by.library.yavlash.config.ApplicationConfiguration;
import by.library.yavlash.dto.BookCopyListDto;
import by.library.yavlash.dto.UserListDto;
import by.library.yavlash.service.BookCopyService;
import by.library.yavlash.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class Runner {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        UserService userService = context.getBean(UserService.class);
        List<UserListDto> allUsers = userService.findAllUsers();
        System.out.println("All USERS: " + allUsers);

        BookCopyService bookCopyService = context.getBean(BookCopyService.class);
        List<BookCopyListDto> bookCopies = bookCopyService.findAllBookCopies();
        System.out.println("All BOOKS: " + bookCopies);
    }
}