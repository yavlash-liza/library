package by.library.yavlash;

import by.library.yavlash.service.UserService;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Configuration
@ComponentScan("by.library.yavlash")
@PropertySource("classpath:db/migration")
public class Runner {
    @Value("jdbc:h2:mem:library;DB_CLOSE_DELAY=-1")
    private String url;
    @Value("sa")
    private String user;
    @Value("")
    private String password;
    @Value("db/migration")
    private String migrationLocation;

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        return Flyway.configure()
                .dataSource(url, user, password)
                .locations(migrationLocation)
                .baselineOnMigrate(true)
                .load();
    }

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        UserService userService = context.getBean(UserService.class);
        System.out.println("All USERS" + userService.findAllUsers());
    }
}