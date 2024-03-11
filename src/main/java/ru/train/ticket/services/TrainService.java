package ru.train.ticket.services;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.train.ticket.DTO.TrainDTO;
import ru.train.ticket.util.ConnectionTB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class TrainService {

    private final ConnectionTB connectionTB;

    @Autowired
    public TrainService(ConnectionTB connectionTB) {
        this.connectionTB = connectionTB;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    public List<TrainDTO> allTrains() throws SQLException {

        Connection con = connectionTB.connect();

        List<TrainDTO> trains = new ArrayList<>();

        PreparedStatement preparedStatement = con.prepareStatement
                ("SELECT * FROM Trains JOIN departures ON Trains.train_id = departures.train_id");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            TrainDTO train = new TrainDTO();

            train.setTrainNumber(resultSet.getInt("train_number"));
            train.setTrainRoute(resultSet.getString("train_route"));
            train.setDepartureTime(resultSet.getTimestamp("departure_time"));

            trains.add(train);
        }
        resultSet.close();
        con.close();

        return trains;
    }
}

