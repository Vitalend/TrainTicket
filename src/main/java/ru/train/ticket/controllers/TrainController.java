package ru.train.ticket.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.train.ticket.DAO.TrainDAO;
import ru.train.ticket.models.Train;

import java.util.List;

@RestController
@RequestMapping("/train")
public class TrainController {

    private final TrainDAO trainDAO;

    @Autowired
    public TrainController(TrainDAO trainDAO) {
        this.trainDAO = trainDAO;
    }

    @GetMapping("/all")
    public List<Train> getAllTrains(){

        return trainDAO.allTrains();
    }
}
