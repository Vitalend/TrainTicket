package ru.train.ticket.models;

import java.sql.Timestamp;

public class Train {
    private int trainNumber;
    private String trainRoute;
    private Timestamp trainDeparture;

    public Train(){}

    public Train(int trainNumber, String trainRoute, Timestamp trainDeparture) {
        this.trainNumber = trainNumber;
        this.trainRoute = trainRoute;
        this.trainDeparture = trainDeparture;
    }

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
