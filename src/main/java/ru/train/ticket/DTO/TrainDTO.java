package ru.train.ticket.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;


public class TrainDTO {
    private int trainNumber;
    private String trainRoute;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp departureTime;

    public TrainDTO() {
    }

    public TrainDTO(int trainNumber, String trainRoute, Timestamp departureTime) {
        this.trainNumber = trainNumber;
        this.trainRoute = trainRoute;
        this.departureTime = departureTime;
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

    public Timestamp getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Timestamp departureTime) {
        this.departureTime = departureTime;
    }

}
