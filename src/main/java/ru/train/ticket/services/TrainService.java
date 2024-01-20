package ru.train.ticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.train.ticket.models.Train;
import ru.train.ticket.util.ConnectionToDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class TrainService {
    ConnectionToDB connectionToDB;

    @Autowired
    public TrainService(ConnectionToDB connectionToDB) {
        this.connectionToDB = connectionToDB;
    }

    public List<Train> allTrains() throws SQLException {
        List<Train> trains = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connectionToDB.getConnection().prepareStatement
                    ("SELECT * FROM Trains");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Train train = new Train();

                train.setTrainNumber(resultSet.getInt("train_number"));
                train.setTrainRoute(resultSet.getString("train_rout"));
                train.setTrainDeparture(resultSet.getTimestamp("train_departure"));

                trains.add(train);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return trains;
    }
}
