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

        Connection con = connectionTB.connect();

        List<Train> trains = new ArrayList<>();

        PreparedStatement preparedStatement = con.prepareStatement
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
        con.close();

        return trains;
    }
}
