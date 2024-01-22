package ru.train.ticket.models;

import org.springframework.stereotype.Component;

@Component
public class OneTicket {

    private int seatNumber;
    private int wagonNumber;
    private int trainNumber;

    public OneTicket() {
    }

    public OneTicket(int seatNumber, int wagonNumber, int trainNumber) {
        this.seatNumber = seatNumber;
        this.wagonNumber = wagonNumber;
        this.trainNumber = trainNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
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
}
