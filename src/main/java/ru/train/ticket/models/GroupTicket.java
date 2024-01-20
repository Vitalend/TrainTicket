package ru.train.ticket.models;

public class GroupTicket {
    private int ticketQuantity;
    private int trainNumber;
    private int wagonNumber;

    public GroupTicket(){}

    public GroupTicket(int ticketQuantity, int trainNumber, int wagonNumber) {
        this.ticketQuantity = ticketQuantity;
        this.trainNumber = trainNumber;
        this.wagonNumber = wagonNumber;
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
