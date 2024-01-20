package ru.train.ticket.util;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.Objects;

@Component
public class ConnectionToDB {
    Environment environment;

    private final Connection connection;

    public ConnectionToDB(Environment environment) throws SQLException {
        this.environment = environment;
        connection = DriverManager.getConnection
                (Objects.requireNonNull(environment.getProperty("spring.datasource.url")),
                        environment.getProperty("spring.datasource.username"),
                        environment.getProperty("spring.datasource.password"));
    }

    public Connection getConnection() {
        return connection;
    }
}

