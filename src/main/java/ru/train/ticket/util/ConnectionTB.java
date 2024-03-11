package ru.train.ticket.util;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

@Component
public class ConnectionTB {

    Environment environment;


    public ConnectionTB(Environment environment) {
        this.environment = environment;
    }

    public Connection connect() throws SQLException {
        return DriverManager.getConnection
                (Objects.requireNonNull(environment.getProperty("spring.datasource.url")),
                        environment.getProperty("spring.datasource.username"),
                        environment.getProperty("spring.datasource.password"));

    }
}
