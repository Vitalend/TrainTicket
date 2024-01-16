package ru.train.ticket.models;

public class Wagon {

    private int wagonId;
    private int wagonNumber;
    private String wagonType;
    private int trainId;

    public Wagon(){};

    public Wagon(int wagonId, int wagonNumber, String wagonType, int trainId) {
        this.wagonId = wagonId;
        this.wagonNumber = wagonNumber;
        this.wagonType = wagonType;
        this.trainId = trainId;
    }

    public int getWagonId() {
        return wagonId;
    }

    public void setWagonId(int wagonId) {
        this.wagonId = wagonId;
    }

    public int getWagonNumber() {
        return wagonNumber;
    }

    public void setWagonNumber(int wagonNumber) {
        this.wagonNumber = wagonNumber;
    }

    public String getWagonType() {
        return wagonType;
    }

    public void setWagonType(String wagonType) {
        this.wagonType = wagonType;
    }

    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }
}
