package ru.train.ticket.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.Objects;

@Component
public class ConnectionToDB {

    Environment environment;

    @Autowired
    public ConnectionToDB(Environment environment) {
        this.environment = environment;
    }

    public ResultSet connect(String query) {
        ResultSet resultSet;
        try {
            Connection connection = DriverManager.getConnection
                    (Objects.requireNonNull(environment.getProperty("spring.datasource.url")),
                            environment.getProperty("spring.datasource.username"),
                            environment.getProperty("spring.datasource.password"));
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery
                    (query);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public void update(String query) {
        try {
            Connection connection = DriverManager.getConnection
                    (Objects.requireNonNull(environment.getProperty("spring.datasource.url")),
                            environment.getProperty("spring.datasource.username"),
                            environment.getProperty("spring.datasource.password"));
            Statement statement1 = connection.createStatement();
          statement1.executeUpdate(query);
          connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

