package ru.train.ticket.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import javax.annotation.processing.Generated;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Train {
   // private int trainId;
    private int trainNumber;
    private String trainRoute;
    private Timestamp trainDeparture;

    public Train(){};

    public Train(int trainNumber, String trainRoute, Timestamp trainDeparture) {
      //  this.trainId = trainId;
        this.trainNumber = trainNumber;
        this.trainRoute = trainRoute;
        this.trainDeparture = trainDeparture;
    }

//    public int getTrainId() {
//        return trainId;
//    }

//  }

    public int getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getTrainRoute() {
        return trainRoute;
    }

    public void setTrainRoute(String trainRoute) {
        this.trainRoute = trainRoute;
    }

    public Timestamp getTrainDeparture() {
        return trainDeparture;
    }

    public void setTrainDeparture(Timestamp trainDeparture) {
        this.trainDeparture = trainDeparture;
    }
}
