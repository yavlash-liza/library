package by.library.yavlash;

import by.library.yavlash.entity.Author;
import by.library.yavlash.service.FlywayService;
import by.library.yavlash.util.HibernateUtil;
import org.hibernate.Session;

import static by.library.yavlash.service.Property.*;

public class Runner {
    public static void main(String[] args) {
        FlywayService flywayService = new FlywayService(H2_URL, H2_USER, H2_PASSWORD, MIGRATION_LOCATION);
        flywayService.migrate();

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Author author = session.get(Author.class, 2L);
            System.out.println(author);
        }
    }
}