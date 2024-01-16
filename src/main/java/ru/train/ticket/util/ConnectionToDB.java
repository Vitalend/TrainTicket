package ru.train.ticket.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import ru.train.ticket.models.SeatTicket;

import java.sql.*;
import java.util.Objects;

@Component
public class ConnectionToDB {

    Environment environment;

    @Autowired
    public ConnectionToDB(Environment environment) {
        this.environment = environment;
    }

    public PreparedStatement connect(String string) {
        PreparedStatement preparedStatement;
        try {
            Connection connection = DriverManager.getConnection
                    (Objects.requireNonNull(environment.getProperty("spring.datasource.url")),
                            environment.getProperty("spring.datasource.username"),
                            environment.getProperty("spring.datasource.password"));
            preparedStatement = connection.prepareStatement(string);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return preparedStatement;
    }

    public void update(String string) {

        try {
            Connection connection = DriverManager.getConnection
                    (Objects.requireNonNull(environment.getProperty("spring.datasource.url")),
                            environment.getProperty("spring.datasource.username"),
                            environment.getProperty("spring.datasource.password"));
            PreparedStatement preparedStatement = connection.prepareStatement(string);
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

