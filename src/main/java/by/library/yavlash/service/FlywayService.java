package by.library.yavlash.service;

import org.flywaydb.core.Flyway;

public class FlywayService {
    private Flyway flyway;

    public FlywayService(String url, String user, String password, String migrationLocation) {
        init(url, user, password, migrationLocation);
    }

    public void migrate() {
        flyway.migrate();
    }

    public void clean() {
        flyway.clean();
    }

    public void init(String url, String user, String password, String migrationLocation) {
        flyway = Flyway.configure().dataSource(url, user, password).locations(migrationLocation).load();
    }
}