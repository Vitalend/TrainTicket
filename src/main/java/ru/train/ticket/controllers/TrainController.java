package ru.train.ticket.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.train.ticket.services.TrainService;
import ru.train.ticket.models.Train;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/train")
public class TrainController {

    private final TrainService trainService;

    @Autowired
    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    @GetMapping("/all")
    public List<Train> getAllTrains() throws SQLException {

        return trainService.allTrains();
    }
}
