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
@PropertySource("classpath:/application.properties")
public class ApplicationConfiguration {
    @Value("${datasource.url}")
    private String url;
    @Value("${datasource.username}")
    private String user;
    @Value("${datasource.password}")
    private String password;
    @Value("${flyway.locations}")
    private String migrationLocation;
    @Value("${datasource.driver-class-name}")
    private String driverClassName;
    @Value("${entity.path}")
    private String entityPath;

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
        sessionFactory.setPackagesToScan(entityPath);
        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driverClassName);
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(user);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }
}