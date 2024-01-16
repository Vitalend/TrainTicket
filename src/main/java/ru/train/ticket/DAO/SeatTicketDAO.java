package ru.train.ticket.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.train.ticket.models.SeatTicket;
import ru.train.ticket.util.ConnectionToDB;

import java.sql.*;


@Component
public class SeatTicketDAO {
    ConnectionToDB connectionToDB;

    @Autowired
    public SeatTicketDAO(ConnectionToDB connectionToDB) {
        this.connectionToDB = connectionToDB;
    }

    private String response;

    public Boolean buyOneTicket(SeatTicket seatTicket) {
        try {
            ResultSet resultSet = connectionToDB.connect("SELECT * FROM Trains JOIN Wagons " +
                    "ON Trains.train_id = Wagons.train_id JOIN Seats "
                    + "ON Wagons.wagon_id = Seats.wagon_id WHERE train_number = " + seatTicket.getTrainNumber()
                    + " AND wagon_number = " + seatTicket.getWagonNumber() + " AND seat_number = "
                    + seatTicket.getSeatNumber() + " AND seat_buying = true");
            if (resultSet.next()) {
                connectionToDB.update("UPDATE seats SET seat_buying = false WHERE seat_id = "
                        + resultSet.getInt("seat_id"));

                response = "Билет на поезд " + seatTicket.getTrainNumber() + " в вагон "
                        + seatTicket.getWagonNumber() + " на место " + seatTicket.getSeatNumber() +
                        " куплен, стоимость " + resultSet.getDouble("seat_cost") +
                        " тип вагона " + resultSet.getString("wagon_type");
                return true;
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return false;
    }

    public Boolean groupTicketBuy(SeatTicket seatTicket) {
        int counter = 0;
        try {
            ResultSet resultSet = connectionToDB.connect(
                    "SELECT * FROM Trains JOIN Wagons ON Trains.train_id = Wagons.train_id " +
                            "JOIN Seats ON Wagons.wagon_id = Seats.wagon_id WHERE seat_buying = true " +
                            "AND train_number = " + seatTicket.getTrainNumber());
            while (resultSet.next()) {
                counter++;
            }
            int quantity = seatTicket.getTicketQuantity();
            if (counter >= quantity) {

                ResultSet resultSet2 = connectionToDB.connect(
                        "SELECT * FROM Trains JOIN Wagons ON Trains.train_id = Wagons.train_id " +
                                "JOIN Seats ON Wagons.wagon_id = Seats.wagon_id WHERE seat_buying = true " +
                                "AND train_number = " + seatTicket.getTrainNumber());
                while (resultSet2.next()) {
                    if (quantity > 0) {
                        connectionToDB.update("UPDATE seats SET seat_buying = false WHERE seat_id = "
                                + resultSet2.getInt("seat_id"));
                        quantity--;
                    }
                }
                response = "Билеты на поезд " + seatTicket.getTrainNumber() +
                        " куплены в количестве " + seatTicket.getTicketQuantity();
                return true;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return false;
    }

    public Boolean refundTicket(SeatTicket seatTicket) {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Timestamp twoHours = new Timestamp(7200000);

        try {
            ResultSet resultSet = connectionToDB.connect("SELECT * FROM Trains JOIN Wagons ON Trains.train_id = Wagons.train_id JOIN Seats "
                    + "ON Wagons.wagon_id = Seats.wagon_id WHERE train_number = " + seatTicket.getTrainNumber()
                    + " AND wagon_number = " + seatTicket.getWagonNumber() + " AND seat_number = "
                    + seatTicket.getSeatNumber());
            while (resultSet.next()) {
                Timestamp timestampDB = (resultSet.getTimestamp("train_departure"));
                if (!resultSet.getBoolean("seat_buying")
                        && (timestampDB.getTime() - timestamp.getTime()) > twoHours.getTime()) {

                    connectionToDB.update("UPDATE seats SET seat_buying = true WHERE seat_id = "
                            + resultSet.getInt("seat_id"));
                    response = "Успешный возврат билета на поезд " + seatTicket.getTrainNumber() + " вагон "
                            + seatTicket.getWagonNumber() + " место " + seatTicket.getSeatNumber()
                            + " средства вернутся в размере " + resultSet.getInt("seat_cost");
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
