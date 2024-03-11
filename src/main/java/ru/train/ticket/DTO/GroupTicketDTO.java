package ru.train.ticket.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;

public class GroupTicketDTO {

    @Min(value = 1, message = "Wrong ticket quantity")
    @Max(value = 4, message = "Wrong ticket quantity")
    private int ticketQuantity;

    @Min(value = 1, message = "Wrong wagon number")
    @Max(value = 2, message = "Wrong wagon number")
    private int wagonNumber;

    @Min(value = 111, message = "Wrong train number")
    @Max(value = 222, message = "Wrong train number")
    private int trainNumber;

    @Size(min = (3), max = (30), message = "Enter the right name")
    private String passengerName;

    @Min(value = 100000, message = "Wrong passport number")
    @Max(value = 999999, message = "Wrong passport number")
    private int passportNumber;

    @Future(message = "Wrong date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp departureTime;

    public GroupTicketDTO() {
    }

    public GroupTicketDTO(int ticketQuantity, int wagonNumber, int trainNumber, String passengerName,
                          int passportNumber, Timestamp departureTime) {
        this.ticketQuantity = ticketQuantity;
        this.wagonNumber = wagonNumber;
        this.trainNumber = trainNumber;
        this.passengerName = passengerName;
        this.passportNumber = passportNumber;
        this.departureTime = departureTime;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public int getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(int passportNumber) {
        this.passportNumber = passportNumber;
    }

    public Timestamp getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Timestamp departureTime) {
        this.departureTime = departureTime;
    }

    public int getWagonNumber() {
        return wagonNumber;
    }

    public void setWagonNumber(int wagonNumber) {
        this.wagonNumber = wagonNumber;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    public int getTicketQuantity() {
        return ticketQuantity;
    }

    public void setTicketQuantity(int ticketQuantity) {
        this.ticketQuantity = ticketQuantity;
    }
}
