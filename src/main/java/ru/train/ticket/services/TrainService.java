package ru.train.ticket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;
import ru.train.ticket.DTO.TrainDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class TrainService {

    private final JdbcClient jdbcClient;

    @Autowired
    public TrainService(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<TrainDTO> allTrains(){

        List<TrainDTO> trainDTOList = new ArrayList<>();

        TrainDTO train = new TrainDTO();

        List<Map<String, Object>> trains =
                jdbcClient.sql("SELECT * FROM Trains JOIN departures ON " +
                        "Trains.train_id = departures.train_id")
                        .query()
                        .listOfRows();
        for (int i = 0; i < trains.size(); i++) {
            train.setTrainNumber((Integer)trains.get(i).get("train_number"));
            train.setTrainRoute(trains.get(i).get("train_route").toString());
            train.setDepartureTime((Timestamp) trains.get(i).get("departure_time"));

            trainDTOList.add(train);
        }
        return trainDTOList;
    }
}

