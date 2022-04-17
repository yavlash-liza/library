package by.library.yavlash.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@Configuration
@PersistenceContext
@ComponentScan("by.library.yavlash")
@PropertySource("classpath:db/migration")
public class ApplicationConfiguration {
    @Value("jdbc:h2:mem:library;DB_CLOSE_DELAY=-1")
    private String url;
    @Value("sa")
    private String user;
    @Value("")
    private String password;
    @Value("db/migration")
    private String migrationLocation;
    @Value("org.h2.Driver")
    private String driverClassName;

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        return Flyway.configure()
                .dataSource(url, user, password)
                .locations(migrationLocation)
                .baselineOnMigrate(true)
                .load();
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("by.library.yavlash.entity");
        return sessionFactory;
    }

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driverClassName);
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(user);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }
}