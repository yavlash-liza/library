package by.library.yavlash;

import by.library.yavlash.config.ApplicationConfiguration;
import by.library.yavlash.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Runner {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        UserService userService = context.getBean(UserService.class);
        System.out.println("All USERS: " + userService.findAllUsers());

//        BookCopyService bookCopyService = context.getBean(BookCopyService.class);
//        System.out.println("All BOOKS: " + bookCopyService.findAllBookCopies());
    }
}