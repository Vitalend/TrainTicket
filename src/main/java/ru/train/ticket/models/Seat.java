package ru.train.ticket.models;

public class Seat {

    private int seatId;
    private int seatNumber;
    private boolean seatBuying;
    private double seatCoast;
    private int wagonId;

    public Seat(){};

    public Seat(int seatId, int seatNumber, boolean seatBuying, double seatCoast, int wagonId) {
        this.seatId = seatId;
        this.seatNumber = seatNumber;
        this.seatBuying = seatBuying;
        this.seatCoast = seatCoast;
        this.wagonId = wagonId;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
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

    public int getWagonId() {
        return wagonId;
    }

    public void setWagonId(int wagonId) {
        this.wagonId = wagonId;
    }
}
