package ru.train.ticket.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import ru.train.ticket.models.Train;
import ru.train.ticket.util.ConnectionToDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class TrainDAO {
    Environment environment;

    ConnectionToDB connectionToDB;

    @Autowired
    public TrainDAO(Environment environment, ConnectionToDB connectionToDB) {
        this.environment = environment;
        this.connectionToDB = connectionToDB;
    }

    public List<Train> allTrains() {
        List<Train> trains = new ArrayList<>();

        try {

            ResultSet resultSet = connectionToDB.connect("SELECT * FROM Trains");
            while (resultSet.next()) {
                Train train = new Train();

                train.setTrainNumber(resultSet.getInt("train_number"));
                train.setTrainRoute(resultSet.getString("train_rout"));
                train.setTrainDeparture(resultSet.getTimestamp("train_departure"));

                trains.add(train);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return trains;
    }
}
