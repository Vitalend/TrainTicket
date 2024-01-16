package ru.train.ticket.models;

public class SeatTicket {


    private int seatNumber;
    private boolean seatBuying;
    private double seatCoast;
    private int wagonNumber;
    private int trainNumber;

    private int ticketQuantity;

    public int getTicketQuantity() {
        return ticketQuantity;
    }

    public void setTicketQuantity(int ticketQuantity) {
        this.ticketQuantity = ticketQuantity;
    }

    public SeatTicket(){};

    public SeatTicket(int seatNumber, boolean seatBuying, double seatCoast, int wagonNumber, int trainNumber, int ticketQuantity) {
        this.seatNumber = seatNumber;
        this.seatBuying = seatBuying;
        this.seatCoast = seatCoast;
        this.wagonNumber = wagonNumber;
        this.trainNumber = trainNumber;
        this.ticketQuantity = ticketQuantity;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public boolean isSeatBuying() {
        return seatBuying;
    }

    public void setSeatBuying(boolean seatBuying) {
        this.seatBuying = seatBuying;
    }

    public double getSeatCoast() {
        return seatCoast;
    }

    public void setSeatCoast(double seatCoast) {
        this.seatCoast = seatCoast;
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
