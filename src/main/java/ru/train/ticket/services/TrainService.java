package ru.train.ticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.train.ticket.models.Train;
import ru.train.ticket.util.ConnectionTB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class TrainService {
    ConnectionTB connectionTB;

    @Autowired
    public TrainService(ConnectionTB connectionTB) {
        this.connectionTB = connectionTB;
    }



    public List<Train> allTrains() throws SQLException {
        List<Train> trains = new ArrayList<>();

        System.setProperty("user.timezone", "Europe/Moscow");

        try {
            PreparedStatement preparedStatement =  connectionTB.connect().prepareStatement
                    ("SELECT * FROM Trains");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Train train = new Train();

                train.setTrainNumber(resultSet.getInt("train_number"));
                train.setTrainRoute(resultSet.getString("train_route"));
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
